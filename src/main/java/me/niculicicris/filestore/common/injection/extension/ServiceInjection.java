package me.niculicicris.filestore.common.injection.extension;

import me.niculicicris.filestore.application.infrastructure.builder.ApplicationBuilder;
import me.niculicicris.filestore.service.authentication.AuthenticationService;
import me.niculicicris.filestore.service.authentication.HashService;
import me.niculicicris.filestore.service.authentication.UserService;
import me.niculicicris.filestore.service.authentication.abstraction.IAuthenticationService;
import me.niculicicris.filestore.service.authentication.abstraction.IHashService;
import me.niculicicris.filestore.service.authentication.abstraction.IUserService;
import me.niculicicris.filestore.service.file.FileService;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

public class ServiceInjection {

    private ServiceInjection() {
    }

    public static void addServices(ApplicationBuilder builder) {
        builder.addComponent(IUserService.class, UserService.class);
        builder.addComponent(IAuthenticationService.class, AuthenticationService.class);
        builder.addComponent(IHashService.class, HashService.class);
        builder.addComponent(IFileService.class, FileService.class);
    }
}
