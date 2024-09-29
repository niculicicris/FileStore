package me.niculicicris.filestore.common.error;

public class UserError {

    private UserError() {
    }

    public static Error invalidUsername(String username) {
        return new Error("User.Validation.Username", ErrorType.VALIDATION, username);
    }

    public static Error invalidPassword(String password) {
        return new Error("User.Validation.Password", ErrorType.VALIDATION, password);
    }

    public static Error alreadyRegistered(String username) {
        return new Error("User.Duplicate", ErrorType.CONFLICT, username);
    }

    public static Error notFound(String username) {
        return new Error("User.NotFound", ErrorType.NOT_FOUND, username);
    }

    public static Error failedAuthentication(String username) {
        return new Error("User.Authentication", ErrorType.AUTHENTICATION, username);
    }
}
