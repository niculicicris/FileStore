package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

public class DeleteFileHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final IStringReader stringReader;
    private final IFileService fileService;

    @Inject
    public DeleteFileHandler(IErrorHandler errorHandler,
                             IStringReader stringReader,
                             IFileService fileService) {
        this.errorHandler = errorHandler;
        this.stringReader = stringReader;
        this.fileService = fileService;
    }

    @Override
    public String getTitle() {
        return "Delete File";
    }

    @Override
    public void handle(Runnable stop) {
        var name = stringReader.readString("Enter file name: ");
        var result = fileService.deleteFile(name);

        result.match(
                () -> System.out.println("File deleted successfully!"),
                errorHandler::handleError
        );
    }
}
