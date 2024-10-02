package me.niculicicris.filestore.injection.extension;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.application.console.abstraction.IConsolePanel;
import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.abstraction.IOptionHandler;
import me.niculicicris.filestore.application.console.authentication.AuthenticationPanel;
import me.niculicicris.filestore.application.console.authentication.LoginHandler;
import me.niculicicris.filestore.application.console.authentication.RegisterHandler;
import me.niculicicris.filestore.application.console.file.FilePanel;
import me.niculicicris.filestore.application.console.file.RetrieveFileHandler;
import me.niculicicris.filestore.application.console.file.StoreFileHandler;
import me.niculicicris.filestore.application.console.file.ViewFilesHandler;
import me.niculicicris.filestore.application.console.io.error.ErrorHandler;
import me.niculicicris.filestore.application.console.io.input.CredentialsReader;
import me.niculicicris.filestore.application.console.io.input.FileReader;
import me.niculicicris.filestore.application.console.io.input.OptionReader;
import me.niculicicris.filestore.application.console.io.input.StringReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.ICredentialsReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.IFileReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.IOptionReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.application.console.io.output.FileSaver;
import me.niculicicris.filestore.application.console.io.output.abstraction.IFileSaver;

import java.util.Scanner;

public class ApplicationInjection {

    private ApplicationInjection() {
    }

    public static void addApplication(ApplicationBuilder builder) {
        addError(builder);
        addAuthentication(builder);
        addFile(builder);
        addInput(builder);
        addOutput(builder);
    }

    private static void addError(ApplicationBuilder builder) {
        builder.addComponent(IErrorHandler.class, ErrorHandler.class);
    }

    private static void addAuthentication(ApplicationBuilder builder) {
        builder.addComponent("Authentication", IConsolePanel.class, AuthenticationPanel.class);
        builder.addComponent("Login", IOptionHandler.class, LoginHandler.class);
        builder.addComponent("Register", IOptionHandler.class, RegisterHandler.class);
    }

    private static void addFile(ApplicationBuilder builder) {
        builder.addComponent("File", IConsolePanel.class, FilePanel.class);
        builder.addComponent("Store", IOptionHandler.class, StoreFileHandler.class);
        builder.addComponent("View", IOptionHandler.class, ViewFilesHandler.class);
        builder.addComponent("Retrieve", IOptionHandler.class, RetrieveFileHandler.class);
    }

    private static void addInput(ApplicationBuilder builder) {
        builder.addComponent(Scanner.class, new Scanner(System.in));
        builder.addComponent(IOptionReader.class, OptionReader.class);
        builder.addComponent(IStringReader.class, StringReader.class);
        builder.addComponent(ICredentialsReader.class, CredentialsReader.class);
        builder.addComponent(IFileReader.class, FileReader.class);
    }

    private static void addOutput(ApplicationBuilder builder) {
        builder.addComponent(IFileSaver.class, FileSaver.class);
    }
}
