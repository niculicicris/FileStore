package me.niculicicris.filestore.injection.extension;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.validation.CredentialsValidator;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;

public class ValidationInjection {

    private ValidationInjection() {
    }

    public static void addValidation(ApplicationBuilder builder) {
        builder.addComponent(ICredentialsValidator.class, CredentialsValidator.class);
    }
}
