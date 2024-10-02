package me.niculicicris.filestore.application.console.abstraction;

import me.niculicicris.filestore.common.error.Error;

public interface IErrorHandler {
    void handleError(Error error);

    void handleError(String code);

    void handleError(String code, String target);
}
