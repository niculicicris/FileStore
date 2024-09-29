package me.niculicicris.filestore.common.result;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;

import java.util.function.Consumer;

public class EmptyResult implements IEmptyResult {
    private final Error error;
    private final boolean success;

    private EmptyResult(Error error, boolean success) {
        this.error = error;
        this.success = success;
    }

    public static IEmptyResult success() {
        return new EmptyResult(null, true);
    }

    public static IEmptyResult failure(Error error) {
        return new EmptyResult(error, false);
    }

    @Override
    public void match(Runnable onSuccess, Consumer<Error> onFailure) {
        if (success) onSuccess.run();
        else onFailure.accept(error);
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
