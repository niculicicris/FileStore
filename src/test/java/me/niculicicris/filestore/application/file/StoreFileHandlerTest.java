package me.niculicicris.filestore.application.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.file.StoreFileHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.IFileReader;
import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.service.file.abstraction.IFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreFileHandlerTest {
    @Mock
    private IErrorHandler errorHandlerMock;

    @Mock
    private IFileReader fileReaderMock;

    @Mock
    private IFileService fileServiceMock;

    @InjectMocks
    private StoreFileHandler storeFileHandler;

    @Test
    public void handle_invalidFile_shouldHandleError() {
        when(fileReaderMock.readFile()).thenReturn(Optional.empty());

        storeFileHandler.handle(() -> {
        });

        verify(fileReaderMock, times(1)).readFile();
        verify(errorHandlerMock, times(1)).handleError("File.System.NotFound");
    }

    @Test
    public void handle_onFailure_shouldHandleError() {
        var file = new FileDto("Test.txt", new byte[0]);
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");
        var result = EmptyResult.failure(error);

        when(fileReaderMock.readFile()).thenReturn(Optional.of(file));
        when(fileServiceMock.storeFile(file)).thenReturn(result);

        storeFileHandler.handle(() -> {
        });

        verify(fileReaderMock, times(1)).readFile();
        verify(fileServiceMock, times(1)).storeFile(file);
        verify(errorHandlerMock, times(1)).handleError(error);
    }

    @Test
    public void handle_onSuccess_shouldContinue() {
        var file = new FileDto("Test.txt", new byte[0]);

        when(fileReaderMock.readFile()).thenReturn(Optional.of(file));
        when(fileServiceMock.storeFile(file)).thenReturn(EmptyResult.success());

        storeFileHandler.handle(() -> {
        });

        verify(fileReaderMock, times(1)).readFile();
        verify(fileServiceMock, times(1)).storeFile(file);
        verify(errorHandlerMock, never()).handleError(any(Error.class));
    }
}
