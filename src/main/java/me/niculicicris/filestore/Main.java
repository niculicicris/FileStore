package me.niculicicris.filestore;

import javafx.application.Application;
import javafx.stage.Stage;
import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.common.injection.extension.ApplicationInjection;
import me.niculicicris.filestore.common.injection.extension.RepositoryInjection;
import me.niculicicris.filestore.common.injection.extension.ServiceInjection;
import me.niculicicris.filestore.common.injection.extension.ValidationInjection;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var builder = ApplicationBuilder.createApplication();
        
        ValidationInjection.addValidation(builder);
        RepositoryInjection.addRepositories(builder);
        ServiceInjection.addServices(builder);
        ApplicationInjection.addApplication(builder, stage);

        builder.build().run();
    }
}