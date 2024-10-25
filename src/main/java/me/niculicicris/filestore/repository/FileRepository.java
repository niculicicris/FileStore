package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.database.IDatabase;
import me.niculicicris.filestore.data.model.FileDescriptor;
import me.niculicicris.filestore.data.model.StoredFile;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileRepository implements IFileRepository {
    private final IDatabase database;

    @Inject
    public FileRepository(IDatabase database) {
        this.database = database;
    }

    @Override
    public void saveFile(StoredFile file) {
        var statement = database.createPreparedStatement("INSERT INTO file VALUES (?, ?, ?)");

        try {
            statement.setString(1, file.owner());
            statement.setString(2, file.name());
            statement.setBlob(3, new ByteArrayInputStream(file.content()));

            database.executeAction(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean fileExists(String owner, String name) {
        var statement = database.createPreparedStatement("SELECT 1 FROM file WHERE owner = ? AND name = ?");

        try {
            statement.setString(1, owner);
            statement.setString(2, name);

            var resultSet = database.executeQuery(statement);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileDescriptor> getFileDescriptors(String owner) {
        var statement = database.createPreparedStatement("SELECT name, OCTET_LENGTH(content) AS size FROM file WHERE owner = ?");

        try {
            var fileDescriptors = new ArrayList<FileDescriptor>();

            statement.setString(1, owner);
            var resultSet = database.executeQuery(statement);

            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var size = resultSet.getInt("size");

                fileDescriptors.add(new FileDescriptor(name, size));
            }

            return fileDescriptors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<StoredFile> getFile(String owner, String name) {
        var statement = database.createPreparedStatement("SELECT content FROM file WHERE owner = ? AND name = ?");

        try {
            statement.setString(1, owner);
            statement.setString(2, name);

            var resultSet = database.executeQuery(statement);

            if (resultSet.next()) {
                var content = resultSet.getBytes("content");
                return Optional.of(new StoredFile(owner, name, content));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String owner, String name) {
        var statement = database.createPreparedStatement("DELETE FROM file WHERE owner = ? AND name = ?");

        try {
            statement.setString(1, owner);
            statement.setString(2, name);

            database.executeAction(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
