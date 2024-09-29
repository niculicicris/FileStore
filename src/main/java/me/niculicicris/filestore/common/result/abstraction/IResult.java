package me.niculicicris.filestore.common.result.abstraction;

import me.niculicicris.filestore.common.error.Error;

import java.util.function.Consumer;

public interface IResult<T> {
    void match(Consumer<T> onSuccess, Consumer<Error> onFailure);

    T getValue();

    Error getError();

    boolean isSuccess();

    boolean isFailure();
}
