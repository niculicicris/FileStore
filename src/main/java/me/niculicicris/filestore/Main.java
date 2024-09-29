package me.niculicicris.filestore;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.injection.extension.ApplicationInjection;
import me.niculicicris.filestore.injection.extension.RepositoryInjection;
import me.niculicicris.filestore.injection.extension.ServiceInjection;
import me.niculicicris.filestore.injection.extension.ValidationInjection;

public class Main {

    public static void main(String[] args) {
        var builder = ApplicationBuilder.createConsoleApplication();

        ValidationInjection.addValidation(builder);
        RepositoryInjection.addRepositories(builder);
        ServiceInjection.addServices(builder);
        ApplicationInjection.addApplication(builder);

        builder.build().run();
    }
}