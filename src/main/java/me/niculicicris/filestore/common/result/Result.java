package me.niculicicris.filestore.common.result;

import me.niculicicris.filestore.common.error.Error;

import java.util.function.Function;

public class Result<T> {
    private final T value;
    private final Error error;
    private final boolean success;

    private Result(T value, Error error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> failure(Error error) {
        return new Result<>(null, error, false);
    }

    public <R> R match(Function<T, R> onSuccess, Function<Error, R> onFailure) {
        if (success) return onSuccess.apply(value);
        else return onFailure.apply(error);
    }

    public T getValue() {
        return value;
    }

    public Error getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }
}
