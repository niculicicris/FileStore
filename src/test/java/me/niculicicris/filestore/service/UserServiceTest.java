package me.niculicicris.filestore.service;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;
import me.niculicicris.filestore.service.authentication.UserService;
import me.niculicicris.filestore.service.authentication.abstraction.IHashService;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IHashService hashServiceMock;

    @Mock
    private ICredentialsValidator validatorMock;

    @Mock
    private IUserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void registerUser_invalidCredentials_shouldReturnValidationError() {
        var credentials = new UserCredentialsDto("testUsername", "testPassword");
        var validationError = new Error("Test", ErrorType.VALIDATION, "TestTarget");
        var validationResult = EmptyResult.failure(validationError);

        when(validatorMock.validate(credentials)).thenReturn(validationResult);
        var result = userService.registerUser(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().getType());
    }

    @Test
    public void registerUser_userAlreadyExists_shouldReturnConflictError() {
        var username = "testUsername";
        var credentials = new UserCredentialsDto(username, "testPassword");

        when(validatorMock.validate(credentials)).thenReturn(EmptyResult.success());
        when(userRepositoryMock.userExists(username)).thenReturn(true);

        var result = userService.registerUser(credentials);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.CONFLICT, result.getError().getType());
    }

    @Test
    public void registerUser_shouldCreateUser() {
        var username = "testUsername";
        var password = "testPassword";
        var hashedPassword = "testHashedPassword";
        var credentials = new UserCredentialsDto(username, password);

        when(hashServiceMock.hash(password)).thenReturn(hashedPassword);
        when(validatorMock.validate(credentials)).thenReturn(EmptyResult.success());
        when(userRepositoryMock.userExists(username)).thenReturn(false);

        var result = userService.registerUser(credentials);

        assertTrue(result.isSuccess());
        verify(hashServiceMock, times(1)).hash(password);
        verify(userRepositoryMock, times(1)).saveUser(argThat(user -> {
            return user.username().equals(username) && user.password().equals(hashedPassword);
        }));
    }
}
