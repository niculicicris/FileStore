package me.niculicicris.filestore;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.injection.ApplicationInjection;
import me.niculicicris.filestore.injection.RepositoryInjection;
import me.niculicicris.filestore.injection.ServiceInjection;
import me.niculicicris.filestore.injection.ValidationInjection;

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