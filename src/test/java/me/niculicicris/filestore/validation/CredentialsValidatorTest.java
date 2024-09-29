package me.niculicicris.filestore.validation;

import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CredentialsValidatorTest {
    private final CredentialsValidator validator = new CredentialsValidator();

    @Test
    public void validate_validCredentials_shouldReturnSuccess() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");
        var result = validator.validate(credentials);

        assertTrue(result.isSuccess());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "t", "te"})
    public void validate_invalidUsername_shouldReturnValidationError(String username) {
        var credentials = new UserCredentialsDto(username, "testPassword");
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().getType());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "12345"})
    public void validate_invalidPassword_shouldReturnValidationError(String password) {
        var credentials = new UserCredentialsDto("testUsername", password);
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().getType());
    }

    @Test
    public void validate_invalidCredentials_shouldReturnValidationError() {
        var credentials = new UserCredentialsDto("", "");
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().getType());
    }
}
