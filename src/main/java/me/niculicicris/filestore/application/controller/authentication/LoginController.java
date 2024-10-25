package me.niculicicris.filestore.application.controller.authentication;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.niculicicris.filestore.application.infrastructure.error.IErrorHandler;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.service.authentication.abstraction.IAuthenticationService;

public class LoginController {
    private final IAuthenticationService authenticationService;
    private final INavigator navigator;
    private final IErrorHandler errorHandler;
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");

    @FXML
    protected TextField usernameField;

    @FXML
    protected PasswordField passwordField;

    @Inject
    public LoginController(IAuthenticationService authenticationService, INavigator navigator, IErrorHandler errorHandler) {
        this.authenticationService = authenticationService;
        this.navigator = navigator;
        this.errorHandler = errorHandler;
    }

    @FXML
    protected void initialize() {
        username.bindBidirectional(usernameField.textProperty());
        password.bindBidirectional(passwordField.textProperty());
    }

    public void onLoginButtonClick() {
        var credentials = new UserCredentialsDto(username.getValue(), password.getValue());
        var result = authenticationService.loginUser(credentials);

        result.match(() -> navigator.navigate("file"), errorHandler::handleError);
    }

    public void onRegisterButtonClick() {
        navigator.navigate("register");
    }
}
