package me.niculicicris.filestore.injection.abstraction;

public interface IComponentCollection {
    <A, I extends A> void addComponent(Class<A> abstraction, I implementation);

    <A> A getComponent(Class<A> abstraction);

    <A> A getComponent(String key, Class<A> abstraction);
}
