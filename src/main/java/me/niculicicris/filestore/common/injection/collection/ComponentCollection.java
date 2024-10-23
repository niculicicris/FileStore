package me.niculicicris.filestore.common.injection.collection;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.annotation.Named;
import me.niculicicris.filestore.common.exception.CircularDependencyException;
import me.niculicicris.filestore.common.exception.ComponentCreationException;
import me.niculicicris.filestore.common.exception.ImplementationConflictException;
import me.niculicicris.filestore.common.exception.NoInjectableConstructorException;
import me.niculicicris.filestore.common.injection.abstraction.IComponentCollection;
import me.niculicicris.filestore.common.injection.abstraction.IMappingCollection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComponentCollection implements IComponentCollection {
    private final IMappingCollection mappings;
    private final Map<Class<?>, Map<String, Object>> implementations = new HashMap<>();
    private final Set<Class<?>> creating = new HashSet<>();

    public ComponentCollection(IMappingCollection mappings) {
        this.mappings = mappings;
    }

    @Override
    public <A, I extends A> void addComponent(Class<A> abstraction, I implementation) {
        if (implementationExists("Default", abstraction)) {
            throw new ImplementationConflictException(abstraction);
        }

        if (!implementations.containsKey(abstraction)) {
            implementations.put(abstraction, new HashMap<>());
        }

        implementations.get(abstraction).put("Default", implementation);
    }

    @Override
    public <A> A getComponent(Class<A> abstraction) {
        return getComponent("Default", abstraction);
    }

    @Override
    public <A> A getComponent(String key, Class<A> abstraction) {
        if (implementationExists(key, abstraction)) {
            return getImplementation(key, abstraction);
        }

        return createImplementation(key, abstraction);
    }

    private boolean implementationExists(String key, Class<?> abstraction) {
        if (!implementations.containsKey(abstraction)) return false;
        return implementations.get(abstraction).containsKey(key);
    }

    private <A> A getImplementation(String key, Class<A> abstraction) {
        return (A) implementations.get(abstraction).get(key);
    }

    private <A> A createImplementation(String key, Class<A> abstraction) {
        var implementation = mappings.getMapping(key, abstraction);

        try {
            creating.add(abstraction);
            var instance = (A) createInstance(implementation);
            creating.remove(abstraction);

            if (!implementations.containsKey(abstraction)) {
                implementations.put(abstraction, new HashMap<>());
            }
            implementations.get(abstraction).put(key, instance);

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ComponentCreationException(abstraction);
        }
    }

    private <I> I createInstance(Class<I> implementation)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var constructor = getConstructor(implementation);
        var parameters = getParameters(implementation, constructor.getParameters());

        return constructor.newInstance(parameters);
    }

    private <I> Constructor<I> getConstructor(Class<I> implementation) {
        if (hasDefaultConstructor(implementation)) {
            return getDefaultConstructor(implementation);
        }

        return getInjectableConstructor(implementation);
    }

    private boolean hasDefaultConstructor(Class<?> implementation) {
        var constructors = implementation.getConstructors();

        if (constructors.length != 1) return false;
        return constructors[0].getParameterCount() == 0;
    }

    private <I> Constructor<I> getDefaultConstructor(Class<I> implementation) {
        return (Constructor<I>) implementation.getConstructors()[0];
    }

    private <I> Constructor<I> getInjectableConstructor(Class<I> implementation) {
        var constructors = implementation.getConstructors();

        for (var constructor : constructors) {
            if (!isInjectableConstructor(constructor)) continue;
            return (Constructor<I>) constructor;
        }

        throw new NoInjectableConstructorException(implementation);
    }

    private boolean isInjectableConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class);
    }

    private Object[] getParameters(Class<?> implementation, Parameter[] parameters) {
        var implementations = new Object[parameters.length];

        for (int index = 0; index < parameters.length; index++) {
            var parameterType = parameters[index];
            implementations[index] = getParameter(implementation, parameterType);
        }

        return implementations;
    }

    private Object getParameter(Class<?> implementation, Parameter parameter) {
        if (isCreating(parameter.getType())) {
            throw new CircularDependencyException(implementation, parameter.getType());
        }

        var key = "Default";
        if (parameter.isAnnotationPresent(Named.class)) {
            key = parameter.getAnnotation(Named.class).value();
        }

        return getComponent(key, parameter.getType());
    }

    private boolean isCreating(Class<?> implementation) {
        return creating.contains(implementation);
    }
}
