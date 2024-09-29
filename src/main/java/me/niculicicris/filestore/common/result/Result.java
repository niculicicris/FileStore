package me.niculicicris.filestore.common.result;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.result.abstraction.IResult;

import java.util.function.Consumer;

public class Result<T> implements IResult<T> {
    private final T value;
    private final Error error;
    private final boolean success;

    private Result(T value, Error error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    public static <T> IResult<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> IResult<T> failure(Error error) {
        return new Result<>(null, error, false);
    }

    @Override
    public void match(Consumer<T> onSuccess, Consumer<Error> onFailure) {
        if (success) onSuccess.accept(value);
        else onFailure.accept(error);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Error getError() {
        return error;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean isFailure() {
        return !success;
    }
}
