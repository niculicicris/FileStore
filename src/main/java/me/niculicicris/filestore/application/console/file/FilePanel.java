package me.niculicicris.filestore.application.console.file;

import me.niculicicris.filestore.application.console.abstraction.ConsolePanel;
import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.input.abstraction.IOptionReader;
import me.niculicicris.filestore.common.annotation.Inject;

public class FilePanel extends ConsolePanel {

    @Inject
    public FilePanel(IErrorHandler errorHandler, IOptionReader optionReader) {
        super("FileStore", errorHandler, optionReader);
    }
}
