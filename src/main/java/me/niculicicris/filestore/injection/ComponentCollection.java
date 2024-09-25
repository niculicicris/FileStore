package me.niculicicris.filestore.injection;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.exception.CircularDependencyException;
import me.niculicicris.filestore.common.exception.NoConstructorException;
import me.niculicicris.filestore.common.exception.UnresolvedComponentException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComponentCollection {
    private final Map<Class<?>, Class<?>> mappings = new HashMap<>();
    private final Map<Class<?>, Object> implementations = new HashMap<>();
    private final Set<Class<?>> resolvingComponents = new HashSet<>();

    public <A, I extends A> void addMapping(Class<A> abstraction, Class<I> implementation) {
        mappings.put(abstraction, implementation);
    }

    public <A> A resolveComponent(Class<A> abstraction) {
        if (implementations.containsKey(abstraction)) {
            return (A) implementations.get(abstraction);
        }

        return createComponent(abstraction);
    }

    private <A> A createComponent(Class<A> abstraction) {
        if (!mappings.containsKey(abstraction)) {
            throw new UnresolvedComponentException(abstraction);
        }
        try {
            return createInstance(abstraction);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new UnresolvedComponentException(abstraction);
        }
    }

    private <A> A createInstance(Class<A> abstraction)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var constructor = getConstructor(abstraction);
        var parameterTypes = constructor.getParameterTypes();

        if (parameterTypes.length == 0) return (A) constructor.newInstance();
        resolvingComponents.add(abstraction);

        var parameters = getParameters(abstraction, parameterTypes);
        var instance = constructor.newInstance(parameters);

        implementations.put(abstraction, instance);
        resolvingComponents.remove(abstraction);

        return (A) instance;
    }

    private <A> Constructor<?> getConstructor(Class<A> abstraction) {
        var implementation = mappings.get(abstraction);

        if (hasDefaultConstructor(implementation)) {
            return implementation.getConstructors()[0];
        }

        for (var constructor : implementation.getConstructors()) {
            if (!isInjectionConstructor(constructor)) continue;
            return constructor;
        }

        throw new NoConstructorException(abstraction);
    }

    private boolean hasDefaultConstructor(Class<?> implementation) {
        if (implementation.getConstructors().length != 1) return false;
        var constructor = implementation.getConstructors()[0];

        if (!Modifier.isPublic(constructor.getModifiers())) return false;

        return constructor.getParameterCount() == 0;
    }

    private boolean isInjectionConstructor(Constructor<?> constructor) {
        if (!Modifier.isPublic(constructor.getModifiers())) return false;
        return constructor.isAnnotationPresent(Inject.class);
    }

    private <A> Object[] getParameters(Class<A> abstraction, Class<?>[] parameterTypes) {
        var parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (resolvingComponents.contains(parameterTypes[i])) {
                throw new CircularDependencyException(parameterTypes[i], abstraction);
            }

            parameters[i] = resolveComponent(parameterTypes[i]);
        }

        return parameters;
    }
}
