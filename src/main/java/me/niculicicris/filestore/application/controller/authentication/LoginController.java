package me.niculicicris.filestore.application.controller.authentication;

import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;

public class LoginController {
    private final INavigator navigator;

    @Inject
    public LoginController(INavigator navigator) {
        this.navigator = navigator;
    }


    public void onLoginButtonClick() {

    }

    public void onRegisterButtonClick() {
        navigator.navigate("register");
    }
}
