package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;

import java.util.Optional;

public class AuthenticationRepository implements IAuthenticationRepository {
    private String username = "";

    @Override
    public void saveAuthentication(String username) {
        this.username = username;
    }

    @Override
    public boolean isAuthenticated() {
        return !username.isEmpty();
    }

    @Override
    public Optional<String> getAuthentication() {
        return username.isEmpty() ? Optional.empty() : Optional.of(username);
    }
}
