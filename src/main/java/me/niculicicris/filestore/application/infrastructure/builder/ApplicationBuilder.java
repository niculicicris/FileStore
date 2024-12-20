package me.niculicicris.filestore.application.infrastructure.builder;

import me.niculicicris.filestore.application.Application;
import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.common.injection.abstraction.IComponentCollection;
import me.niculicicris.filestore.common.injection.abstraction.IMappingCollection;
import me.niculicicris.filestore.common.injection.collection.ComponentCollection;
import me.niculicicris.filestore.common.injection.collection.MappingCollection;

public class ApplicationBuilder {
    private final IMappingCollection mappings;
    private final IComponentCollection components;

    private <T extends IApplication> ApplicationBuilder(Class<T> applicationType) {
        this.mappings = new MappingCollection();
        this.components = new ComponentCollection(mappings);

        this.mappings.addMapping(IApplication.class, applicationType);
        this.components.addComponent(IComponentCollection.class, components);
    }

    public static ApplicationBuilder createApplication() {
        return new ApplicationBuilder(Application.class);
    }

    public <A, I extends A> void addComponent(Class<A> abstraction, Class<I> implementation) {
        mappings.addMapping(abstraction, implementation);
    }

    public <A, I extends A> void addComponent(String key, Class<A> abstraction, Class<I> implementation) {
        mappings.addMapping(key, abstraction, implementation);
    }

    public <A, I extends A> void addComponent(Class<A> abstraction, I implementation) {
        components.addComponent(abstraction, implementation);
    }

    public IApplication build() {
        return components.getComponent(IApplication.class);
    }
}
