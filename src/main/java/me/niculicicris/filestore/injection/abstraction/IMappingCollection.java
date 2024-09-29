package me.niculicicris.filestore.injection.abstraction;

public interface IMappingCollection {
    <A, I extends A> void addMapping(Class<A> abstraction, Class<I> implementation);

    <A, I extends A> void addMapping(String key, Class<A> abstraction, Class<I> implementation);
    
    <A, I extends A> Class<I> getMapping(String key, Class<A> abstraction);
}
