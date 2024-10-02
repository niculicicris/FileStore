package me.niculicicris.filestore.service;

import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.data.model.StoredFile;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;
import me.niculicicris.filestore.service.file.FileService;
import me.niculicicris.filestore.validation.abstraction.IFileNameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private IFileNameValidator validatorMock;

    @Mock
    private IAuthenticationRepository authenticationRepositoryMock;

    @Mock
    private IFileRepository fileRepositoryMock;

    @InjectMocks
    private FileService fileService;

    @Test
    public void storeFile_notAuthenticated_shouldReturnAuthorizationError() {
        var file = new FileDto("Test.txt", new byte[0]);

        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.empty());
        var result = fileService.storeFile(file);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.AUTHORIZATION, result.getError().type());
    }

    @Test
    public void storeFile_fileAlreadyExists_shouldReturnConflictError() {
        var file = new FileDto("Test.txt", new byte[0]);
        var owner = "TestOwner";

        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.fileExists(owner, file.name())).thenReturn(true);
        var result = fileService.storeFile(file);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.CONFLICT, result.getError().type());
    }

    @Test
    public void storeFile_shouldStoreFile() {
        var file = new FileDto("Test.txt", new byte[0]);
        var owner = "TestOwner";

        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.fileExists(owner, file.name())).thenReturn(false);
        var result = fileService.storeFile(file);

        assertTrue(result.isSuccess());
        verify(fileRepositoryMock, times(1)).saveFile(any());
    }

    @Test
    public void getFilesDetails_notAuthenticated_shouldReturnAuthorizationError() {
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.empty());
        var result = fileService.getFilesDetails();

        assertTrue(result.isFailure());
        assertEquals(ErrorType.AUTHORIZATION, result.getError().type());
    }

    @Test
    public void getFilesDetails_shouldReturnFilesDetails() {
        var owner = "TestOwner";
        var files = new ArrayList<StoredFile>();

        files.add(new StoredFile(owner, "Test1.txt", new byte[0]));
        files.add(new StoredFile(owner, "Test2.txt", new byte[0]));

        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.getFiles(owner)).thenReturn(files);

        var result = fileService.getFilesDetails();
        var value = result.getValue();

        assertTrue(result.isSuccess());
        verify(fileRepositoryMock, times(1)).getFiles(owner);
        for (int index = 0; index < value.size(); ++index) {
            assertEquals(files.get(index).name(), value.get(index).name());
        }
    }

    @Test
    public void retrieveFile_invalidFileName_shouldReturnValidationError() {
        var validationError = new Error("Test", ErrorType.VALIDATION, "TestTarget");
        var name = "Test*";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.failure(validationError));
        var result = fileService.retrieveFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @Test
    public void retrieveFile_notAuthenticated_shouldReturnAuthorizationError() {
        var name = "Test.txt";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.empty());

        var result = fileService.retrieveFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.AUTHORIZATION, result.getError().type());
    }

    @Test
    public void retrieveFile_fileNotFound_shouldReturnNotFoundError() {
        var name = "Test.txt";
        var owner = "TestOwner";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.getFile(owner, name)).thenReturn(Optional.empty());

        var result = fileService.retrieveFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.NOT_FOUND, result.getError().type());
        verify(fileRepositoryMock, times(1)).getFile(owner, name);
    }

    @Test
    public void retrieveFile_shouldReturnRetrievedFile() {
        var name = "Test.txt";
        var owner = "TestOwner";
        var file = new StoredFile(owner, name, new byte[0]);

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.getFile(owner, name)).thenReturn(Optional.of(file));

        var result = fileService.retrieveFile(name);

        assertTrue(result.isSuccess());
        assertEquals(name, result.getValue().name());
    }

    @Test
    public void deleteFile_invalidFileName_shouldReturnValidationError() {
        var validationError = new Error("Test", ErrorType.VALIDATION, "TestTarget");
        var name = "Test*";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.failure(validationError));
        var result = fileService.deleteFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.VALIDATION, result.getError().type());
    }

    @Test
    public void deleteFile_notAuthenticated_shouldReturnAuthorizationError() {
        var name = "Test.txt";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.empty());

        var result = fileService.deleteFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.AUTHORIZATION, result.getError().type());
    }

    @Test
    public void deleteFile_fileNotFound_shouldReturnNotFoundError() {
        var name = "Test.txt";
        var owner = "TestOwner";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.fileExists(owner, name)).thenReturn(false);

        var result = fileService.deleteFile(name);

        assertTrue(result.isFailure());
        assertEquals(ErrorType.NOT_FOUND, result.getError().type());
    }

    @Test
    public void deleteFile__shouldDeleteFile() {
        var name = "Test.txt";
        var owner = "TestOwner";

        when(validatorMock.validate(name)).thenReturn(EmptyResult.success());
        when(authenticationRepositoryMock.getAuthentication()).thenReturn(Optional.of(owner));
        when(fileRepositoryMock.fileExists(owner, name)).thenReturn(true);

        var result = fileService.deleteFile(name);

        assertTrue(result.isSuccess());
        verify(fileRepositoryMock, times(1)).deleteFile(owner, name);
    }
}
