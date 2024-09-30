package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.FileDetailDto;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

import java.util.List;

public class ViewFilesHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final IFileService fileService;

    @Inject
    public ViewFilesHandler(IErrorHandler errorHandler, IFileService fileService) {
        this.errorHandler = errorHandler;
        this.fileService = fileService;
    }

    @Override
    public String getTitle() {
        return "View Files";
    }

    @Override
    public void handle(Runnable stop) {
        var result = fileService.getFilesDetails();

        result.match(
                this::displayFilesDetails,
                errorHandler::handleError
        );
    }

    private void displayFilesDetails(List<FileDetailDto> details) {
        System.out.println("Files: ");
        details.forEach(this::displayFileDetails);
    }

    private void displayFileDetails(FileDetailDto detail) {
        System.out.println(" - " + detail.name() + " (" + detail.size() + " bytes)");
    }
}
