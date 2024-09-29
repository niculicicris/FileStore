package me.niculicicris.filestore.service.abstraction;

import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;

public interface IUserService {
    IEmptyResult registerUser(UserCredentialsDto credentials);
}
