package me.niculicicris.filestore.application.navigation;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import me.niculicicris.filestore.application.loader.IViewLoader;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;

import java.util.HashMap;
import java.util.Map;

public abstract class Navigator implements INavigator {
    private final IViewLoader viewLoader;
    private final Map<String, String> routes = new HashMap<>();
    private String currentRoute;
    private Pane root;

    @Inject
    public Navigator(IViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    @Override
    public void initialize(Pane root) {
        this.root = root;
    }

    public void navigate(String route) {
        if (currentRoute != null) {
            var currentResource = routes.get(currentRoute);
            viewLoader.unload(currentResource);
        }
        var view = getView(route);

        currentRoute = route;
        root.getChildren().clear();
        root.getChildren().add(view);
    }


    private Parent getView(String route) {
        if (!routes.containsKey(route)) {
            throw new RuntimeException("There is no navigation route with the name '" + route + "'.");
        }
        var resource = routes.get(route);

        return viewLoader.load(resource);
    }

    protected void addRoute(String name, String resource) {
        routes.put(name, resource);
    }
}
