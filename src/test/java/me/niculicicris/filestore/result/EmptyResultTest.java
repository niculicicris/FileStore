package me.niculicicris.filestore.result;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyResultTest {

    @Test
    public void success_shouldReturnSuccess() {
        var result = EmptyResult.success();
        assertTrue(result.isSuccess());
    }

    @Test
    public void failure_shouldReturnFailure() {
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = EmptyResult.failure(error);

        assertTrue(result.isFailure());
        assertEquals(error, result.getError());
    }

    @Test
    public void match_onSuccess_shouldExecuteOnSuccess() {
        var result = EmptyResult.success();

        result.match(
                () -> {
                },
                _ -> Assertions.fail()
        );
    }

    @Test
    public void match_onFailure_shouldExecuteOnFailure() {
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = EmptyResult.failure(error);

        result.match(
                Assertions::fail,
                _ -> {
                }
        );
    }
}
