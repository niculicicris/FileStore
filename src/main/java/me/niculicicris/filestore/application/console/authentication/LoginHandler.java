package me.niculicicris.filestore.application.console.authentication;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.io.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.service.authentication.abstraction.IAuthenticationService;

public class LoginHandler implements IOptionHandler {
    private final IErrorHandler errorHandler;
    private final ICredentialsReader credentialsReader;
    private final IAuthenticationService authenticationService;

    @Inject
    public LoginHandler(IErrorHandler errorHandler,
                        ICredentialsReader credentialsReader,
                        IAuthenticationService authenticationService) {
        this.errorHandler = errorHandler;
        this.credentialsReader = credentialsReader;
        this.authenticationService = authenticationService;
    }

    @Override
    public String getTitle() {
        return "Login";
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
