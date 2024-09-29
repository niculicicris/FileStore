package me.niculicicris.filestore.application;

import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.application.console.abstraction.IConsolePanel;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.annotation.Named;

public class ConsoleApplication implements IApplication {
    private final IConsolePanel authenticationPanel;
    private final IConsolePanel filePanel;

    @Inject
    public ConsoleApplication(@Named("Authentication") IConsolePanel authenticationPanel,
                              @Named("File") IConsolePanel filePanel) {
        this.authenticationPanel = authenticationPanel;
        this.filePanel = filePanel;
    }

    @Override
    public void run() {
        authenticationPanel.run();
        filePanel.run();
    }
}