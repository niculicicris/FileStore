package me.niculicicris.filestore.service;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.data.model.User;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;
import me.niculicicris.filestore.service.abstraction.IHashService;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private IHashService hashServiceMock;

    @Mock
    private ICredentialsValidator validatorMock;

    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private IAuthenticationRepository authenticationRepositoryMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void loginUser_invalidCredentials_shouldReturnValidationError() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");
        var validationError = new Error("Test", ErrorType.VALIDATION, "TestTarget");
        var validationResult = EmptyResult.failure(validationError);

        when(validatorMock.validate(credentials)).thenReturn(validationResult);
        var result = authenticationService.loginUser(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().getType());
    }

    @Test
    public void loginUser_invalidUser_shouldReturnNotFoundError() {
        var username = "testUsername";
        var credentials = new UserCredentialsDto(username, "testPassword");

        when(validatorMock.validate(credentials)).thenReturn(EmptyResult.success());
        when(userRepositoryMock.getUser(username)).thenReturn(Optional.empty());

        var result = authenticationService.loginUser(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.NOT_FOUND, result.getError().getType());
        assertEquals(username, result.getError().getTarget());
    }

    @Test
    public void loginUser_invalidPassword_shouldReturnAuthenticationError() {
        var username = "testUsername";
        var password = "testPassword";
        var passwordHash = "testPasswordHash";

        var credentials = new UserCredentialsDto(username, password);
        var user = new User(username, passwordHash);

        when(validatorMock.validate(credentials)).thenReturn(EmptyResult.success());
        when(userRepositoryMock.getUser(username)).thenReturn(Optional.of(user));
        when(hashServiceMock.matches(passwordHash, password)).thenReturn(false);

        var result = authenticationService.loginUser(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.AUTHENTICATION, result.getError().getType());
    }

    @Test
    public void loginUser_shouldReturnSuccess() {
        var username = "testUsername";
        var password = "testPassword";
        var passwordHash = "testPasswordHash";

        var credentials = new UserCredentialsDto(username, password);
        var user = new User(username, passwordHash);

        when(validatorMock.validate(credentials)).thenReturn(EmptyResult.success());
        when(userRepositoryMock.getUser(username)).thenReturn(Optional.of(user));
        when(hashServiceMock.matches(passwordHash, password)).thenReturn(true);

        var result = authenticationService.loginUser(credentials);

        assertTrue(result.isSuccess());
        verify(authenticationRepositoryMock, times(1)).saveAuthentication(username);
    }
}
