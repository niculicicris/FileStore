package me.niculicicris.filestore.validation;

import me.niculicicris.filestore.common.error.ErrorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileNameValidatorTest {
    private final FileNameValidator validator = new FileNameValidator();

    @Test
    public void validate_nullName_shouldReturnValidationError() {
        var result = validator.validate(null);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }


    @ParameterizedTest
    @ValueSource(strings = {"Test.txt", "Test.java", "Test"})
    public void validate_validName_shouldReturnSuccess(String name) {
        var result = validator.validate(name);
        assertTrue(result.isSuccess());
    }
}
