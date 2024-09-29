package me.niculicicris.filestore.repository.abstraction;

import java.util.Optional;

public interface IAuthenticationRepository {
    void saveAuthentication(String username);

    boolean isAuthenticated();

    Optional<String> getAuthentication();
}
