package me.niculicicris.filestore.application.input;

import me.niculicicris.filestore.application.console.input.CredentialsReader;
import me.niculicicris.filestore.application.console.input.abstraction.IStringReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CredentialsReaderTest {
    @Mock
    private IStringReader stringReaderMock;

    @InjectMocks
    private CredentialsReader credentialsReader;

    @Test
    public void readCredentials_shouldReturnCredentials() {
        var username = "testUsername";
        var password = "testPassword";

        when(stringReaderMock.readString(any()))
                .thenReturn(username).thenReturn(password);
        var credentials = credentialsReader.readCredentials();

        assertEquals(username, credentials.username());
        assertEquals(password, credentials.password());
    }
}
