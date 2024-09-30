package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.application.console.io.output.abstraction.IFileSaver;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

public class RetrieveFileHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final IStringReader stringReader;
    private final IFileSaver fileSaver;
    private final IFileService fileService;

    @Inject
    public RetrieveFileHandler(IErrorHandler errorHandler,
                               IStringReader stringReader,
                               IFileSaver fileSaver,
                               IFileService fileService) {
        this.errorHandler = errorHandler;
        this.stringReader = stringReader;
        this.fileSaver = fileSaver;
        this.fileService = fileService;
    }

    @Override
    public String getTitle() {
        return "Retrieve File";
    }

    @Override
    public void handle(Runnable stop) {
        var name = stringReader.readString("Enter file name: ");
        var result = fileService.retrieveFile(name);

        result.match(
                fileSaver::saveFile,
                errorHandler::handleError
        );
    }
}
