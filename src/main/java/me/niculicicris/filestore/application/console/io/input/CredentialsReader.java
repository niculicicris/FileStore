package me.niculicicris.filestore.application.console.io.input;

import me.niculicicris.filestore.application.console.io.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;

public class CredentialsReader implements ICredentialsReader {
    private final IStringReader stringReader;

    @Inject
    public CredentialsReader(IStringReader stringReader) {
        this.stringReader = stringReader;
    }

    @Override
    public UserCredentialsDto readCredentials() {
        var username = stringReader.readString("Enter username: ");
        var password = stringReader.readString("Enter password: ");

        return new UserCredentialsDto(username, password);
    }
}
