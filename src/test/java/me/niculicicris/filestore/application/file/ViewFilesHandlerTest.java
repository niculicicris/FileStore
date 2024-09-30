package me.niculicicris.filestore.application.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.file.ViewFilesHandler;
import me.niculicicris.filestore.common.error.Error;
import me.niculicicris.filestore.common.error.ErrorType;
import me.niculicicris.filestore.common.result.Result;
import me.niculicicris.filestore.data.dto.FileDetailDto;
import me.niculicicris.filestore.service.file.abstraction.IFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ViewFilesHandlerTest {
    @Mock
    private IErrorHandler errorHandlerMock;

    @Mock
    private IFileService fileServiceMock;

    @InjectMocks
    private ViewFilesHandler viewFilesHandler;

    @Test
    public void handle_onFailure_shouldHandleError() {
        var error = new Error("Test", ErrorType.AUTHORIZATION, "TestTarget");

        when(fileServiceMock.getFilesDetails()).thenReturn(Result.failure(error));
        viewFilesHandler.handle(() -> {
        });

        verify(fileServiceMock, times(1)).getFilesDetails();
        verify(errorHandlerMock, times(1)).handleError(error);
    }

    @Test
    public void handle_onSuccess_shouldHandleError() {
        var details = new ArrayList<FileDetailDto>();

        when(fileServiceMock.getFilesDetails()).thenReturn(Result.success(details));
        viewFilesHandler.handle(() -> {
        });

        verify(fileServiceMock, times(1)).getFilesDetails();
        verify(errorHandlerMock, never()).handleError(any(Error.class));
    }
}
