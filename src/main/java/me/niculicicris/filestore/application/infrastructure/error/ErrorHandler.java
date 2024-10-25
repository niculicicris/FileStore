package me.niculicicris.filestore.application.infrastructure.error;

import javafx.scene.control.Alert;
import me.niculicicris.filestore.common.error.Error;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler implements IErrorHandler {
    private final Map<String, String> errors = new HashMap<>();

    public ErrorHandler() {
        addCommonErrors();
        addUserErrors();
        addFileErrors();
    }

    private void addCommonErrors() {
        errors.put("Server", "An internal server error has occurred.");
    }

    private void addUserErrors() {
        errors.put("User.Validation", "The provided username or password is invalid.");
        errors.put("User.Password.Validation", "The provided passwords do not match.");
        errors.put("User.Duplicate", "There is already a user with the name '{target}'.");
        errors.put("User.Authentication", "The provided credentials are wrong.");
    }

    private void addFileErrors() {
        errors.put("File.Validation", "This is not a valid file name.");
        errors.put("File.Duplicate", "There is already a file with the name '{target}' stored.");
        errors.put("File.NotFound", "There is no file with the name '{target}' stored.");
    }


    @Override
    public void handleError(Error error) {
        var message = getErrorMessage(error);
        var alert = new Alert(Alert.AlertType.ERROR, message);

        alert.show();
    }

    @Override
    public void handleError(String code) {
        var message = errors.get(code);
        var alert = new Alert(Alert.AlertType.ERROR, message);

        alert.show();
    }

    private String getErrorMessage(Error error) {
        var message = errors.get(error.code());
        return message.replace("{target}", error.target());
    }
}