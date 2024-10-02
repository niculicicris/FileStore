package me.niculicicris.filestore.service;

import me.niculicicris.filestore.service.authentication.HashService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashServiceTest {
    private final HashService hashService = new HashService();

    @Test
    public void matches_wrongValue_returnsFalse() {
        var correctPassword = "testPassword";
        var wrongPassword = "testWrongPassword";
        var hashedPassword = hashService.hash(correctPassword);

        var matches = hashService.matches(hashedPassword, wrongPassword);
        assertFalse(matches);
    }

    @Test
    public void matches_correctValue_returnsTrue() {
        var password = "testPassword";
        var hashedPassword = hashService.hash(password);

        var matches = hashService.matches(hashedPassword, password);
        assertTrue(matches);
    }
}
