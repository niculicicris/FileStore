package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.data.model.StoredFile;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;

import java.util.*;

public class MapFileRepository implements IFileRepository {
    private final Map<String, Map<String, StoredFile>> storedFiles = new HashMap<>();

    @Override
    public void saveFile(StoredFile file) {
        if (!storedFiles.containsKey(file.owner())) {
            storedFiles.put(file.owner(), new HashMap<>());
        }

        storedFiles.get(file.owner()).put(file.name(), file);
    }

    @Override
    public boolean fileExists(String owner, String name) {
        return storedFiles.containsKey(owner) && storedFiles.get(owner).containsKey(name);
    }

    @Override
    public List<StoredFile> getFiles(String owner) {
        if (!storedFiles.containsKey(owner)) return Collections.emptyList();
        return new ArrayList<>(storedFiles.get(owner).values());
    }

    @Override
    public Optional<StoredFile> getFile(String owner, String name) {
        if (!storedFiles.containsKey(owner)) return Optional.empty();
        return Optional.ofNullable(storedFiles.get(owner).get(name));
    }

    @Override
    public void deleteFile(String owner, String name) {
        if (!storedFiles.containsKey(owner)) return;
        storedFiles.get(owner).remove(name);

        if (storedFiles.get(owner).isEmpty()) {
            storedFiles.remove(owner);
        }
    }
}
