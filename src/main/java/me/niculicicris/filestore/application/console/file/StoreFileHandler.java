package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.IFileReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

public class StoreFileHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final IFileReader fileReader;
    private final IFileService fileService;

    @Inject
    public StoreFileHandler(IErrorHandler errorHandler, IFileReader fileReader, IFileService fileService) {
        this.errorHandler = errorHandler;
        this.fileReader = fileReader;
        this.fileService = fileService;
    }

    @Override
    public String getTitle() {
        return "Store File";
    }

    @Override
    public void handle(Runnable stop) {
        var optionalFile = fileReader.readFile();

        if (optionalFile.isEmpty()) {
            errorHandler.handleError("File");
            return;
        }
        var file = optionalFile.get();
        var result = fileService.storeFile(file);

        result.match(
                () -> System.out.println("File stored successfully!"),
                errorHandler::handleError
        );
    }
}
