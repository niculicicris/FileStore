package me.niculicicris.filestore.common.injection.extension;

import javafx.stage.Stage;
import me.niculicicris.filestore.application.controller.ApplicationController;
import me.niculicicris.filestore.application.controller.authentication.LoginController;
import me.niculicicris.filestore.application.controller.authentication.RegisterController;
import me.niculicicris.filestore.application.controller.file.FileExplorerController;
import me.niculicicris.filestore.application.infrastructure.builder.ApplicationBuilder;
import me.niculicicris.filestore.application.infrastructure.error.ErrorHandler;
import me.niculicicris.filestore.application.infrastructure.error.IErrorHandler;
import me.niculicicris.filestore.application.infrastructure.loader.IViewLoader;
import me.niculicicris.filestore.application.infrastructure.loader.ViewLoader;
import me.niculicicris.filestore.application.io.reader.FileReader;
import me.niculicicris.filestore.application.io.reader.IFileReader;
import me.niculicicris.filestore.application.io.writer.FileWriter;
import me.niculicicris.filestore.application.io.writer.IFileWriter;
import me.niculicicris.filestore.application.navigation.MainNavigator;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;

public class ApplicationInjection {

    private ApplicationInjection() {
    }

    public static void addApplication(ApplicationBuilder builder, Stage stage) {
        addFX(builder, stage);
        addIO(builder);
        addNavigation(builder);
        addControllers(builder);
        addErrorHandling(builder);
    }

    private static void addFX(ApplicationBuilder builder, Stage stage) {
        builder.addComponent(Stage.class, stage);
        builder.addComponent(IViewLoader.class, ViewLoader.class);
    }

    private static void addIO(ApplicationBuilder builder) {
        builder.addComponent(IFileReader.class, FileReader.class);
        builder.addComponent(IFileWriter.class, FileWriter.class);
    }

    private static void addNavigation(ApplicationBuilder builder) {
        builder.addComponent(INavigator.class, MainNavigator.class);
    }

    private static void addControllers(ApplicationBuilder builder) {
        builder.addComponent(ApplicationController.class, ApplicationController.class);
        builder.addComponent(LoginController.class, LoginController.class);
        builder.addComponent(RegisterController.class, RegisterController.class);
        builder.addComponent(FileExplorerController.class, FileExplorerController.class);
    }

    private static void addErrorHandling(ApplicationBuilder builder) {
        builder.addComponent(IErrorHandler.class, ErrorHandler.class);
    }
}
