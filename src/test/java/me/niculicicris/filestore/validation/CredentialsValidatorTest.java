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

    @Test
    public void validate_nullUsername_shouldReturnValidationError() {
        var credentials = new UserCredentialsDto(null, "testPassword");
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "tes"})
    public void validate_tooShortUsername_shouldReturnValidationError(String username) {
        var credentials = new UserCredentialsDto(username, "testPassword");
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @Test
    public void validate_tooLongUsername_shouldReturnValidationError() {
        var username = "t".repeat(25);
        var credentials = new UserCredentialsDto(username, "testPassword");
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @Test
    public void validate_nullPassword_shouldReturnValidationError() {
        var credentials = new UserCredentialsDto("testUsername", null);
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "testP"})
    public void validate_tooShortPassword_shouldReturnValidationError(String password) {
        var credentials = new UserCredentialsDto("testUsername", password);
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @Test
    public void validate_tooLongPassword_shouldReturnValidationError() {
        var password = "t".repeat(25);
        var credentials = new UserCredentialsDto("testUsername", password);
        var result = validator.validate(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }
}
