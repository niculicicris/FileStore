package me.niculicicris.filestore.common.injection.extension;

import me.niculicicris.filestore.application.infrastructure.builder.ApplicationBuilder;
import me.niculicicris.filestore.data.database.Database;
import me.niculicicris.filestore.data.database.IDatabase;
import me.niculicicris.filestore.repository.AuthenticationRepository;
import me.niculicicris.filestore.repository.FileRepository;
import me.niculicicris.filestore.repository.UserRepository;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;

public class RepositoryInjection {

    private RepositoryInjection() {
    }

    public static void addRepositories(ApplicationBuilder builder) {
        builder.addComponent(IDatabase.class, Database.class);
        builder.addComponent(IUserRepository.class, UserRepository.class);
        builder.addComponent(IAuthenticationRepository.class, AuthenticationRepository.class);
        builder.addComponent(IFileRepository.class, FileRepository.class);
    }
}
