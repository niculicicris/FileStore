package me.niculicicris.filestore.application.console.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.authentication.abstraction.IUserService;

public class RegisterHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final ICredentialsReader credentialsReader;
    private final IUserService userService;

    @Inject
    public RegisterHandler(IErrorHandler errorHandler,
                           ICredentialsReader credentialsReader, IUserService userService) {
        this.errorHandler = errorHandler;
        this.credentialsReader = credentialsReader;
        this.userService = userService;
    }

    @Override
    public String getTitle() {
        return "Register";
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
