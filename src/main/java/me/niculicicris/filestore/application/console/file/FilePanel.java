package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.ConsolePanel;
import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.input.abstraction.IOptionReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.annotation.Named;

public class FilePanel extends ConsolePanel {

    @Inject
    public FilePanel(IErrorHandler errorHandler,
                     IOptionReader optionReader,
                     @Named("Store") IOptionHandler storeHandler,
                     @Named("View") IOptionHandler viewHandler,
                     @Named("Retrieve") IOptionHandler retrieveHandler) {
        super("FileStore", errorHandler, optionReader);

        addHandler(storeHandler);
        addHandler(viewHandler);
        addHandler(retrieveHandler);
    }
}
