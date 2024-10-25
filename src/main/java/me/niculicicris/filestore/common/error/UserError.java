package me.niculicicris.filestore.common.error;

public class UserError {

    private UserError() {
    }

    public static Error invalidCredentials() {
        return new Error("User.Validation", ErrorType.VALIDATION, "");
    }

    public static Error alreadyRegistered(String username) {
        return new Error("User.Duplicate", ErrorType.CONFLICT, username);
    }

    public static Error failedAuthentication(String username) {
        return new Error("User.Authentication", ErrorType.AUTHENTICATION, username);
    }

    public static Error failedAuthorization() {
        return new Error("User.Authorization", ErrorType.AUTHORIZATION, "");
    }
}
