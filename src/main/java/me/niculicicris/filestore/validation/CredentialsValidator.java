package me.niculicicris.filestore.validation;

import me.niculicicris.filestore.common.error.UserError;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;

public class CredentialsValidator implements ICredentialsValidator {

    @Override
    public IEmptyResult validate(UserCredentialsDto credentials) {
        var username = credentials.username();
        var password = credentials.password();

        if (!isUsernameValid(username)) {
            var error = UserError.invalidUsername(username);
            return EmptyResult.failure(error);
        }

        if (!isPasswordValid(password)) {
            var error = UserError.invalidPassword(password);
            return EmptyResult.failure(error);
        }

        return EmptyResult.success();
    }

    private boolean isUsernameValid(String username) {
        if (username == null) return false;
        if (username.isEmpty()) return false;
        if (username.length() < 3) return false;


        return username.length() <= 32;
    }

    private boolean isPasswordValid(String password) {
        if (password == null) return false;
        if (password.isEmpty()) return false;

        return password.length() >= 6;
    }
}
