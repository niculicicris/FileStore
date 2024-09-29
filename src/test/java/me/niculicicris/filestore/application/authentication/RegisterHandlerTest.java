package me.niculicicris.filestore.application.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.authentication.RegisterHandler;
import me.niculicicris.filestore.application.console.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.service.abstraction.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterHandlerTest {
    @Mock
    private IErrorHandler errorHandlerMock;

    @Mock
    private ICredentialsReader credentialsReaderMock;

    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private RegisterHandler registerHandler;

    @Test
    public void handle_wrongCredentials_shouldHandleError() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = EmptyResult.failure(error);

        when(credentialsReaderMock.readCredentials()).thenReturn(credentials);
        when(userServiceMock.registerUser(credentials)).thenReturn(result);

        registerHandler.handle(() -> {
        });

        verify(credentialsReaderMock, times(1)).readCredentials();
        verify(userServiceMock, times(1)).registerUser(credentials);
        verify(errorHandlerMock, times(1)).handleError(error);
    }

    @Test
    public void handle_correctCredentials_shouldContinue() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");

        when(credentialsReaderMock.readCredentials()).thenReturn(credentials);
        when(userServiceMock.registerUser(credentials)).thenReturn(EmptyResult.success());

        registerHandler.handle(() -> {
        });

        verify(credentialsReaderMock, times(1)).readCredentials();
        verify(userServiceMock, times(1)).registerUser(credentials);
        verify(errorHandlerMock, never()).handleError(any(Error.class));
    }
}
