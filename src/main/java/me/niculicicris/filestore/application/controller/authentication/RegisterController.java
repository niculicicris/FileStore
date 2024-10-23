package me.niculicicris.filestore.application.controller.authentication;

import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;

public class RegisterController {
    private final INavigator navigator;

    @Inject
    public RegisterController(INavigator navigator) {
        this.navigator = navigator;
    }

    public void switchLogin() {
        navigator.navigate("login");
    }
}
