package me.niculicicris.filestore.common.injection.abstraction;

public interface IComponentCollection extends IComponentInjector {
    <A, I extends A> void addComponent(Class<A> abstraction, I implementation);
}
