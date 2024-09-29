package me.niculicicris.filestore.injection.extension;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.service.AuthenticationService;
import me.niculicicris.filestore.service.NoneHashService;
import me.niculicicris.filestore.service.UserService;
import me.niculicicris.filestore.service.abstraction.IAuthenticationService;
import me.niculicicris.filestore.service.abstraction.IHashService;
import me.niculicicris.filestore.service.abstraction.IUserService;

public class ServiceInjection {

    private ServiceInjection() {
    }

    public static void addServices(ApplicationBuilder builder) {
        builder.addComponent(IUserService.class, UserService.class);
        builder.addComponent(IAuthenticationService.class, AuthenticationService.class);
        builder.addComponent(IHashService.class, NoneHashService.class);
    }
}
