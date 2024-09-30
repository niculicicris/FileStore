package me.niculicicris.filestore.application.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.file.RetrieveFileHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.application.console.io.output.abstraction.IFileSaver;
import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.Result;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.service.file.abstraction.IFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveFileHandlerTest {
    @Mock
    private IErrorHandler errorHandlerMock;

    @Mock
    private IStringReader stringReaderMock;

    @Mock
    private IFileSaver fileSaverMock;

    @Mock
    private IFileService fileServiceMock;

    @InjectMocks
    private RetrieveFileHandler retrieveFileHandler;

    @Test
    public void handle_onFailure_ShouldHandleError() {
        var fileName = "Test.txt";
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");

        when(stringReaderMock.readString(any())).thenReturn(fileName);
        when(fileServiceMock.retrieveFile(fileName)).thenReturn(Result.failure(error));

        retrieveFileHandler.handle(() -> {
        });

        verify(stringReaderMock, times(1)).readString(any());
        verify(fileServiceMock, times(1)).retrieveFile(fileName);
        verify(errorHandlerMock, times(1)).handleError(error);
    }

    @Test
    public void handle_onSuccess_ShouldContinue() {
        var fileName = "Test.txt";
        var file = new FileDto(fileName, new byte[0]);

        when(stringReaderMock.readString(any())).thenReturn(fileName);
        when(fileServiceMock.retrieveFile(fileName)).thenReturn(Result.success(file));

        retrieveFileHandler.handle(() -> {
        });

        verify(stringReaderMock, times(1)).readString(any());
        verify(fileServiceMock, times(1)).retrieveFile(fileName);
        verify(fileSaverMock, times(1)).saveFile(file);
    }
}
