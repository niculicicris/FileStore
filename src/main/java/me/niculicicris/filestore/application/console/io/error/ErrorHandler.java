package me.niculicicris.filestore.application.console.io.error;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
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
        errors.put("File.System.NotFound", "The file was not found or can't be read.");
        errors.put("File.System.Duplicate", "There is already a file saved with this name.");
    }

    private void addUserErrors() {
        errors.put("User.Validation.Username", "'{target}' is not a valid username.");
        errors.put("User.Validation.Password", "'{target}' is not a valid password.");
        errors.put("User.Duplicate", "There is already a user with the name '{target}'.");
        errors.put("User.NotFound", "There is no user with the name '{target}'.");
        errors.put("User.Authentication", "The credentials provided are incorrect.");
        errors.put("User.Authorization", "You are not authorized to do this.");
    }

    private void addFileErrors() {
        errors.put("File.Validation", "This is not a valid file name.");
        errors.put("File.Duplicate", "There is already a file with the name '{target}' stored.");
        errors.put("File.NotFound", "There is no file with the name '{target}' stored.");
    }

    @Override
    public void handleError(Error error) {
        var message = getErrorMessage(error);
        System.out.println(message);
    }

    private String getErrorMessage(Error error) {
        var message = errors.get(error.code());
        return message.replace("{target}", error.target());
    }

    @Override
    public void handleError(String code) {
        var message = errors.get(code);
        System.out.println(message);
    }

    @Override
    public void handleError(String code, String target) {
        var message = errors.get(code).replace("{target}", target);
        System.out.println(message);
    }
}
