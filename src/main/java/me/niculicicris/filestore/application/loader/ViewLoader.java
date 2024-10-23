package me.niculicicris.filestore.application.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.niculicicris.filestore.Main;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.injection.abstraction.IComponentCollection;

import java.io.IOException;

public class ViewLoader implements IViewLoader {
    private final IComponentCollection componentCollection;

    @Inject
    public ViewLoader(IComponentCollection componentCollection) {
        this.componentCollection = componentCollection;
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

    @Override
    public void unload(String resource) {

        try {
            var loader = createLoader(resource);

            loader.load();
            componentCollection.removeComponent(loader.getController().getClass());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private FXMLLoader createLoader(String resource) {
        var loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource(resource));
        loader.setControllerFactory(componentCollection::getComponent);

        return loader;
    }
}
