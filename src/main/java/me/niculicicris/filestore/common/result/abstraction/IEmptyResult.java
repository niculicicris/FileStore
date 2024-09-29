package me.niculicicris.filestore.common.result.abstraction;

import me.niculicicris.filestore.common.error.Error;

import java.util.function.Consumer;

public interface IEmptyResult {
    void match(Runnable onSuccess, Consumer<Error> onFailure);

    Error getError();

    boolean isSuccess();

    boolean isFailure();
}
