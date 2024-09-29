package me.niculicicris.filestore.application.console.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.OptionHandler;
import me.niculicicris.filestore.application.console.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.abstraction.IAuthenticationService;

public class LoginHandler extends OptionHandler {
    private final IErrorHandler errorHandler;
    private final ICredentialsReader credentialsReader;
    private final IAuthenticationService authenticationService;

    @Inject
    public LoginHandler(IErrorHandler errorHandler,
                        ICredentialsReader credentialsReader, IAuthenticationService authenticationService) {
        super("Login");
        this.errorHandler = errorHandler;
        this.credentialsReader = credentialsReader;
        this.authenticationService = authenticationService;
    }

    @Override
    public void handle(Runnable stop) {
        var credentials = credentialsReader.readCredentials();
        var result = authenticationService.loginUser(credentials);

        result.match(
                () -> {
                    System.out.println("Logged in successfully!");
                    stop.run();
                },
                errorHandler::handleError
        );
    }
}
