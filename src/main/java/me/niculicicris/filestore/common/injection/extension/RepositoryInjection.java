package me.niculicicris.filestore.common.injection.extension;

import me.niculicicris.filestore.application.builder.ApplicationBuilder;
import me.niculicicris.filestore.repository.AuthenticationRepository;
import me.niculicicris.filestore.repository.MapFileRepository;
import me.niculicicris.filestore.repository.MapUserRepository;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;

public class RepositoryInjection {

    private RepositoryInjection() {
    }

    public static void addRepositories(ApplicationBuilder builder) {
        builder.addComponent(IUserRepository.class, MapUserRepository.class);
        builder.addComponent(IAuthenticationRepository.class, AuthenticationRepository.class);
        builder.addComponent(IFileRepository.class, MapFileRepository.class);
    }
}
