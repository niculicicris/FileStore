package me.niculicicris.filestore.common.injection.collection;

import me.niculicicris.filestore.common.exception.NoDefaultMappingException;
import me.niculicicris.filestore.common.exception.NoMappingException;
import me.niculicicris.filestore.common.injection.abstraction.IMappingCollection;

import java.util.HashMap;
import java.util.Map;

public class MappingCollection implements IMappingCollection {
    private final Map<Class<?>, Map<String, Class<?>>> mappings = new HashMap<>();

    @Override
    public <A, I extends A> void addMapping(Class<A> abstraction, Class<I> implementation) {
        addMapping("Default", abstraction, implementation);
    }

    @Override
    public <A, I extends A> void addMapping(String key, Class<A> abstraction, Class<I> implementation) {
        if (!mappings.containsKey(abstraction)) {
            mappings.put(abstraction, new HashMap<>());
        }

        mappings.get(abstraction).put(key, implementation);
    }

    @Override
    public <A, I extends A> Class<I> getMapping(String key, Class<A> abstraction) {
        if (!hasImplementation(key, abstraction)) {
            if (key.equals("Default")) throw new NoDefaultMappingException(abstraction);
            else throw new NoMappingException(key, abstraction);
        }

        return (Class<I>) mappings.get(abstraction).get(key);
    }

    private boolean hasImplementation(String key, Class<?> abstraction) {
        if (!mappings.containsKey(abstraction)) return false;
        return mappings.get(abstraction).containsKey(key);
    }
}
