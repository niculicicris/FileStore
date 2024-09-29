package me.niculicicris.filestore.validation.abstraction;

import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;

public interface ICredentialsValidator {
    IEmptyResult validate(UserCredentialsDto credentials);
}
