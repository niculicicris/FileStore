package me.niculicicris.filestore.repository.abstraction;

import me.niculicicris.filestore.data.model.User;

import java.util.Optional;

public interface IUserRepository {
    void saveUser(User user);

    boolean userExists(String username);

    Optional<User> getUser(String username);
}
