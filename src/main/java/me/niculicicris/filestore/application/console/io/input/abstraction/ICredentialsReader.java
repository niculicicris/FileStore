package me.niculicicris.filestore.application.console.io.input.abstraction;

import me.niculicicris.filestore.data.dto.UserCredentialsDto;

public interface ICredentialsReader {
    UserCredentialsDto readCredentials();
}
