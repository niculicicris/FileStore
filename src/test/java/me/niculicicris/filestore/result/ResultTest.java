package me.niculicicris.filestore.result;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultTest {

    @Test
    public void success_shouldReturnSuccess() {
        var value = "TestValue";
        var result = Result.success(value);

        assertTrue(result.isSuccess());
        assertEquals(value, result.getValue());
    }

    @Test
    public void failure_shouldReturnFailure() {
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = Result.failure(error);

        assertTrue(result.isFailure());
        assertEquals(error, result.getError());
    }

    @Test
    public void match_onSuccess_shouldExecuteOnSuccess() {
        var result = Result.success(null);

        result.match(
                _ -> {
                },
                _ -> Assertions.fail()
        );
    }

    @Test
    public void match_onFailure_shouldExecuteOnFailure() {
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = Result.failure(error);

        result.match(
                _ -> Assertions.fail(),
                _ -> {
                }
        );
    }
}
