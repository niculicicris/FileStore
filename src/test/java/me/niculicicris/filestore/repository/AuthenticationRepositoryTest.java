package me.niculicicris.filestore.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationRepositoryTest {
    private final AuthenticationRepository authenticationRepository = new AuthenticationRepository();

    @Test
    public void isAuthenticated_noAuthentication_shouldReturnFalse() {
        var isAuthenticated = authenticationRepository.isAuthenticated();
        assertFalse(isAuthenticated);
    }

    @Test
    public void isAuthenticated_savedAuthentication_shouldReturnTrue() {
        authenticationRepository.saveAuthentication("testUsername");

        var isAuthenticated = authenticationRepository.isAuthenticated();
        assertTrue(isAuthenticated);
    }

    @Test
    public void getAuthentication_noAuthentication_shouldReturnEmpty() {
        var authentication = authenticationRepository.getAuthentication();
        assertTrue(authentication.isEmpty());
    }

    @Test
    public void getAuthentication_savedAuthentication_shouldReturnAuthentication() {
        var username = "testUsername";

        authenticationRepository.saveAuthentication(username);
        var authentication = authenticationRepository.getAuthentication();

        assertTrue(authentication.isPresent());
        assertEquals(username, authentication.get());
    }
}
