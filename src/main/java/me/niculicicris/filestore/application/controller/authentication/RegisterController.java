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
import me.niculicicris.filestore.service.authentication.abstraction.IUserService;

public class RegisterController {
    private final IUserService userService;
    private final INavigator navigator;
    private final IErrorHandler errorHandler;
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty confirmedPassword = new SimpleStringProperty("");

    @FXML
    protected TextField usernameField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected PasswordField confirmedPasswordField;


    @Inject
    public RegisterController(IUserService userService, INavigator navigator, IErrorHandler errorHandler) {
        this.userService = userService;
        this.navigator = navigator;
        this.errorHandler = errorHandler;
    }

    @FXML
    protected void initialize() {
        username.bindBidirectional(usernameField.textProperty());
        password.bindBidirectional(passwordField.textProperty());
        confirmedPassword.bindBidirectional(confirmedPasswordField.textProperty());
    }

    public void onRegisterButtonClick() {
        if (!passwordsMatch()) {
            errorHandler.handleError("User.Password.Validation");
            return;
        }
        var credentials = new UserCredentialsDto(username.get(), password.get());
        var result = userService.registerUser(credentials);

        result.match(() -> navigator.navigate("login"), errorHandler::handleError);
    }

    private boolean passwordsMatch() {
        return password.get().equals(confirmedPassword.get());
    }

    public void onLoginButtonClick() {
        navigator.navigate("login");
    }
}
