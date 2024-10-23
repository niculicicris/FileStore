package me.niculicicris.filestore.application.navigation;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import me.niculicicris.filestore.application.loader.IFxmlLoader;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;

import java.util.HashMap;
import java.util.Map;

public abstract class Navigator implements INavigator {
    private final IFxmlLoader fxmlLoader;
    private final Map<String, String> routes = new HashMap<>();
    private Pane root;

    @Inject
    public Navigator(IFxmlLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    @Override
    public void initialize(Pane root) {
        this.root = root;
    }

    public void navigate(String route) {
        var view = getView(route);

        root.getChildren().clear();
        root.getChildren().add(view);
    }

    private Parent getView(String route) {
        if (!routes.containsKey(route)) {
            throw new RuntimeException("There is no navigation route with the name '" + route + "'.");
        }
        var resource = routes.get(route);

        return fxmlLoader.load(resource);
    }

    protected void addRoute(String name, String resource) {
        routes.put(name, resource);
    }
}
