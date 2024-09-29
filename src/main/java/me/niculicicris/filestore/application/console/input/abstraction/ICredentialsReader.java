package me.niculicicris.filestore.application.console.input.abstraction;

import me.niculicicris.filestore.data.dto.UserCredentialsDto;

public interface ICredentialsReader {
    UserCredentialsDto readCredentials();
}
