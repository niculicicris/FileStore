package me.niculicicris.filestore.common.injection.extension;

import javafx.stage.Stage;
import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.application.controller.ApplicationController;
import me.niculicicris.filestore.application.controller.authentication.LoginController;
import me.niculicicris.filestore.application.controller.authentication.RegisterController;
import me.niculicicris.filestore.application.loader.IFxmlLoader;
import me.niculicicris.filestore.application.loader.InjectionFXMLLoader;
import me.niculicicris.filestore.application.navigation.MainNavigator;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;

public class ApplicationInjection {

    private ApplicationInjection() {
    }

    public static void addApplication(ApplicationBuilder builder, Stage stage) {
        addFX(builder, stage);
        addNavigation(builder);
        addControllers(builder);
    }

    private static void addFX(ApplicationBuilder builder, Stage stage) {
        builder.addComponent(Stage.class, stage);
        builder.addComponent(IFxmlLoader.class, InjectionFXMLLoader.class);
    }

    private static void addNavigation(ApplicationBuilder builder) {
        builder.addComponent(INavigator.class, MainNavigator.class);
    }

    private static void addControllers(ApplicationBuilder builder) {
        builder.addComponent(ApplicationController.class, ApplicationController.class);
        builder.addComponent(LoginController.class, LoginController.class);
        builder.addComponent(RegisterController.class, RegisterController.class);
    }
}
