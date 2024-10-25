package me.niculicicris.filestore.application.infrastructure.error;

import me.niculicicris.filestore.common.error.Error;

public interface IErrorHandler {
    void handleError(Error error);

    void handleError(String code);
}