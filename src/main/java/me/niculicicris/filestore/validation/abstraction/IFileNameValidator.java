package me.niculicicris.filestore.validation.abstraction;

import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;

public interface IFileNameValidator {
    IEmptyResult validate(String name);
}
