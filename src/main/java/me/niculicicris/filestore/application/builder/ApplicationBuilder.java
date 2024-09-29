package me.niculicicris.filestore.application.builder;

import me.niculicicris.filestore.application.ConsoleApplication;
import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.injection.abstraction.IComponentCollection;
import me.niculicicris.filestore.injection.abstraction.IMappingCollection;
import me.niculicicris.filestore.injection.collection.ComponentCollection;
import me.niculicicris.filestore.injection.collection.MappingCollection;

public class ApplicationBuilder {
    private final IMappingCollection mappings;
    private final IComponentCollection components;

    private <T extends IApplication> ApplicationBuilder(Class<T> applicationType) {
        this.mappings = new MappingCollection();
        this.components = new ComponentCollection(mappings);

        this.mappings.addMapping(IApplication.class, applicationType);
    }

    public static ApplicationBuilder createConsoleApplication() {
        return new ApplicationBuilder(ConsoleApplication.class);
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
