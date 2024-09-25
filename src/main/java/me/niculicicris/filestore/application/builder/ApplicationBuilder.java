package me.niculicicris.filestore.application.builder;

import me.niculicicris.filestore.application.ConsoleApplication;
import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.injection.ComponentCollection;

public class ApplicationBuilder {
    private final ComponentCollection components;
    private final Class<? extends IApplication> applicationType;

    private <T extends IApplication> ApplicationBuilder(Class<T> applicationType) {
        this.components = new ComponentCollection();
        this.applicationType = applicationType;
    }

    public static ApplicationBuilder createConsoleApplication() {
        return new ApplicationBuilder(ConsoleApplication.class);
    }

    public <A, I extends A> void addComponent(Class<A> abstraction, Class<I> implementation) {
        components.addMapping(abstraction, implementation);
    }

    public IApplication build() {
        components.addMapping(IApplication.class, applicationType);
        return components.resolveComponent(IApplication.class);
    }
}
