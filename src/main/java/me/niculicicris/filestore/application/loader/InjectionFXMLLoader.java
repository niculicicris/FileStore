package me.niculicicris.filestore.application.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.niculicicris.filestore.Main;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.injection.abstraction.IComponentInjector;

import java.io.IOException;

public class InjectionFXMLLoader implements IFxmlLoader {
    private final IComponentInjector componentInjector;

    @Inject
    public InjectionFXMLLoader(IComponentInjector componentInjector) {
        this.componentInjector = componentInjector;
    }

    @Override
    public Parent load(String resource) {
        try {
            var loader = createLoader(resource);
            return loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private FXMLLoader createLoader(String resource) {
        var loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource(resource));
        loader.setControllerFactory(componentInjector::getComponent);

        return loader;
    }
}
