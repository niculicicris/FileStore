package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.data.model.StoredFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapFileRepositoryTest {
    private final MapFileRepository repository = new MapFileRepository();

    @Test
    public void fileExists_noFile_shouldReturnFalse() {
        var fileExists = repository.fileExists("TestOwner", "Test.txt");
        assertFalse(fileExists);
    }

    @Test
    public void fileExists_shouldReturnTrue() {
        var owner = "TestOwner";
        var name = "Test.txt";
        var file = new StoredFile(owner, name, new byte[0]);

        repository.saveFile(file);
        var fileExists = repository.fileExists(owner, name);

        assertTrue(fileExists);
    }

    @Test
    public void getFiles_noFile_shouldReturnEmptyList() {
        var files = repository.getFiles("TestOwner");
        assertTrue(files.isEmpty());
    }

    @Test
    public void getFiles_shouldReturnFiles() {
        var owner = "TestOwner";
        var file1 = new StoredFile(owner, "Test1.txt", new byte[0]);
        var file2 = new StoredFile(owner, "Test2.txt", new byte[0]);

        repository.saveFile(file1);
        repository.saveFile(file2);

        var files = repository.getFiles("TestOwner");

        assertEquals(2, files.size());
        assertEquals(files.getFirst().owner(), file1.owner());
        assertEquals(files.getFirst().name(), file1.name());
        assertEquals(files.get(1).owner(), file2.owner());
        assertEquals(files.get(1).name(), file2.name());
    }

    @Test
    public void getFile_noFile_shouldReturnEmpty() {
        var file = repository.getFile("TestOwner", "Test.txt");
        assertTrue(file.isEmpty());
    }

    @Test
    public void getFile_shouldReturnFile() {
        var owner = "TestOwner";
        var name = "Test.txt";
        var file = new StoredFile(owner, name, new byte[0]);

        repository.saveFile(file);
        var optionalFile = repository.getFile(owner, name);

        assertTrue(optionalFile.isPresent());
        assertEquals(owner, optionalFile.get().owner());
        assertEquals(name, optionalFile.get().name());
    }
}
