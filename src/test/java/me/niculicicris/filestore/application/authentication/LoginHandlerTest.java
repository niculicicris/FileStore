package me.niculicicris.filestore.application.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.authentication.LoginHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.service.authentication.abstraction.IAuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginHandlerTest {
    @Mock
    private IErrorHandler errorHandlerMock;

    @Mock
    private ICredentialsReader credentialsReaderMock;

    @Mock
    private IAuthenticationService authenticationServiceMock;

    @InjectMocks
    private LoginHandler loginHandler;

    @Test
    public void handle_onFailure_shouldHandleError() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = EmptyResult.failure(error);

        when(credentialsReaderMock.readCredentials()).thenReturn(credentials);
        when(authenticationServiceMock.loginUser(credentials)).thenReturn(result);

        loginHandler.handle(() -> {
        });

        verify(credentialsReaderMock, times(1)).readCredentials();
        verify(authenticationServiceMock, times(1)).loginUser(credentials);
        verify(errorHandlerMock, times(1)).handleError(error);
    }

    @Test
    public void handle_onSuccess_shouldStop() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");

        when(credentialsReaderMock.readCredentials()).thenReturn(credentials);
        when(authenticationServiceMock.loginUser(credentials)).thenReturn(EmptyResult.success());

        loginHandler.handle(() -> {
        });

        verify(credentialsReaderMock, times(1)).readCredentials();
        verify(authenticationServiceMock, times(1)).loginUser(credentials);
        verify(errorHandlerMock, never()).handleError(any(Error.class));
    }
}
