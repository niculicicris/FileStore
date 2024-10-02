package me.niculicicris.filestore.repository.abstraction;

import me.niculicicris.filestore.data.model.StoredFile;

import java.util.List;
import java.util.Optional;

public interface IFileRepository {
    void saveFile(StoredFile file);

    boolean fileExists(String owner, String name);

    List<StoredFile> getFiles(String owner);

    Optional<StoredFile> getFile(String owner, String name);

    void deleteFile(String owner, String name);
}
