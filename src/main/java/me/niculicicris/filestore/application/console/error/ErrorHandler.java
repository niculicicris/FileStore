package me.niculicicris.filestore.application.console.error;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.common.error.Error;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler implements IErrorHandler {
    private final Map<String, String> errors = new HashMap<>();

    public ErrorHandler() {
        addCommonErrors();
        addUserErrors();
    }

    private void addCommonErrors() {
        errors.put("Server", "An internal server error has occurred.");
    }

    private void addUserErrors() {
        errors.put("User.Validation.Username", "'{target}' is not a valid username.");
        errors.put("User.Validation.Password", "'{target}' is not a valid password.");
        errors.put("User.Duplicate", "There is already a user with the name '{target}'.");
        errors.put("User.NotFound", "There is no user with the name '{target}'.");
        errors.put("User.Authentication", "The credentials provided are incorrect.");
    }

    @Override
    public void handleError(Error error) {
        var message = getErrorMessage(error);
        System.out.println(message);
    }

    private String getErrorMessage(Error error) {
        var message = errors.get(error.getCode());
        return message.replace("{target}", error.getTarget());
    }

    @Override
    public void handleError(String code) {
        var message = errors.get(code);
        System.out.println(message);
    }
}
