package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.data.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapUserRepositoryTest {
    private final MapUserRepository repository = new MapUserRepository();

    @Test
    public void userExists_noUser_shouldReturnFalse() {
        var userExists = repository.userExists("TestUsername");
        assertFalse(userExists);
    }

    @Test
    public void userExists_shouldReturnTrue() {
        var user = new User("TestUsername", "TestPassword");
        repository.saveUser(user);

        var userExists = repository.userExists("TestUsername");
        assertTrue(userExists);
    }

    @Test
    public void getUser_noUser_shouldReturnEmpty() {
        var user = repository.getUser("TestUsername");
        assertTrue(user.isEmpty());
    }

    @Test
    public void getUser_shouldReturnUser() {
        var user = new User("TestUsername", "TestPassword");
        repository.saveUser(user);

        var foundUser = repository.getUser("TestUsername");

        assertTrue(foundUser.isPresent());
        assertEquals(user.username(), foundUser.get().username());
        assertEquals(user.password(), foundUser.get().password());
    }
}
