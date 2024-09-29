package me.niculicicris.filestore.application.console.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.OptionHandler;
import me.niculicicris.filestore.application.console.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.abstraction.IUserService;

public class RegisterHandler extends OptionHandler {
    private final IErrorHandler errorHandler;
    private final ICredentialsReader credentialsReader;
    private final IUserService userService;

    @Inject
    public RegisterHandler(IErrorHandler errorHandler,
                           ICredentialsReader credentialsReader, IUserService userService) {
        super("Register");
        this.errorHandler = errorHandler;
        this.credentialsReader = credentialsReader;
        this.userService = userService;
    }

    @Override
    public void handle(Runnable stop) {
        var credentials = credentialsReader.readCredentials();
        var result = userService.registerUser(credentials);

        result.match(
                () -> System.out.println("Registered successfully!"),
                errorHandler::handleError
        );
    }
}