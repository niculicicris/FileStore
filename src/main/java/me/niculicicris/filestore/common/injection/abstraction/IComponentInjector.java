package me.niculicicris.filestore.common.injection.abstraction;

public interface IComponentInjector {
    <A> A getComponent(Class<A> abstraction);

    <A> A getComponent(String key, Class<A> abstraction);
}
