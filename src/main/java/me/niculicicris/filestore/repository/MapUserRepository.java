package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.data.model.User;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapUserRepository implements IUserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void saveUser(User user) {
        users.put(user.username(), user);
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public Optional<User> getUser(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
