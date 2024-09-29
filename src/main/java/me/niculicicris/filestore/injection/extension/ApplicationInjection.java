package me.niculicicris.filestore.injection.extension;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.application.console.abstraction.IConsolePanel;
import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.OptionHandler;
import me.niculicicris.filestore.application.console.authentication.AuthenticationPanel;
import me.niculicicris.filestore.application.console.authentication.LoginHandler;
import me.niculicicris.filestore.application.console.authentication.RegisterHandler;
import me.niculicicris.filestore.application.console.error.ErrorHandler;
import me.niculicicris.filestore.application.console.file.FilePanel;
import me.niculicicris.filestore.application.console.input.CredentialsReader;
import me.niculicicris.filestore.application.console.input.OptionReader;
import me.niculicicris.filestore.application.console.input.StringReader;
import me.niculicicris.filestore.application.console.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.application.console.input.abstraction.IOptionReader;
import me.niculicicris.filestore.application.console.input.abstraction.IStringReader;

import java.util.Scanner;

public class ApplicationInjection {

    private ApplicationInjection() {
    }

    public static void addApplication(ApplicationBuilder builder) {
        addError(builder);
        addAuthentication(builder);
        addFile(builder);
        addInput(builder);
    }

    private static void addError(ApplicationBuilder builder) {
        builder.addComponent(IErrorHandler.class, ErrorHandler.class);
    }

    private static void addAuthentication(ApplicationBuilder builder) {
        builder.addComponent("Authentication", IConsolePanel.class, AuthenticationPanel.class);
        builder.addComponent("Login", OptionHandler.class, LoginHandler.class);
        builder.addComponent("Register", OptionHandler.class, RegisterHandler.class);
    }

    private static void addFile(ApplicationBuilder builder) {
        builder.addComponent("File", IConsolePanel.class, FilePanel.class);
    }

    private static void addInput(ApplicationBuilder builder) {
        builder.addComponent(Scanner.class, new Scanner(System.in));
        builder.addComponent(IOptionReader.class, OptionReader.class);
        builder.addComponent(IStringReader.class, StringReader.class);
        builder.addComponent(ICredentialsReader.class, CredentialsReader.class);
    }
}
