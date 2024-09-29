package me.niculicicris.filestore.application.console.authentication;

import me.niculicicris.filestore.application.console.abstraction.ConsolePanel;
import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.OptionHandler;
import me.niculicicris.filestore.application.console.input.abstraction.IOptionReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.annotation.Named;

public class AuthenticationPanel extends ConsolePanel {

    @Inject
    public AuthenticationPanel(IErrorHandler errorHandler,
                               IOptionReader optionReader,
                               @Named("Login") OptionHandler loginHandler,
                               @Named("Register") OptionHandler registerHandler) {
        super("Authentication", errorHandler, optionReader);

        addHandler(loginHandler);
        addHandler(registerHandler);
    }
}
