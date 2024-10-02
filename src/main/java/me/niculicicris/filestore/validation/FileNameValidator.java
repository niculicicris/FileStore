package me.niculicicris.filestore.validation;

import me.niculicicris.filestore.common.error.FileError;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.validation.abstraction.IFileNameValidator;

public class FileNameValidator implements IFileNameValidator {

    @Override
    public IEmptyResult validate(String name) {
        if (name == null) {
            var error = FileError.invalidFileName();
            return EmptyResult.failure(error);
        }

        var regex = "[^~)('!*<>:;,?\"|]+.[^~)('!*<>:;,?\"|]+";
        if (!name.matches(regex)) {
            var error = FileError.invalidFileName();
            return EmptyResult.failure(error);
        }

        return EmptyResult.success();
    }
}
