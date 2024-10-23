package me.niculicicris.filestore.application.navigation;

import me.niculicicris.filestore.application.loader.IViewLoader;
import me.niculicicris.filestore.common.annotation.Inject;

public class MainNavigator extends Navigator {

    @Inject
    public MainNavigator(IViewLoader viewLoader) {
        super(viewLoader);

        addRoute("login", "/application/authentication/login-view.fxml");
        addRoute("register", "/application/authentication/register-view.fxml");
    }
}
