package me.niculicicris.filestore.repository;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.database.IDatabase;
import me.niculicicris.filestore.data.model.User;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;

import java.sql.SQLException;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    private final IDatabase database;

    @Inject
    public UserRepository(IDatabase database) {
        this.database = database;
    }

    @Override
    public void saveUser(User user) {
        var statement = database.createPreparedStatement("INSERT INTO user VALUES (?, ?)");

        try {
            statement.setString(1, user.username());
            statement.setString(2, user.password());

            database.executeAction(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userExists(String username) {
        var statement = database.createPreparedStatement("SELECT 1 FROM user WHERE username = ?");

        try {
            statement.setString(1, username);
            var resultSet = database.executeQuery(statement);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUser(String username) {
        var statement = database.createPreparedStatement("SELECT * FROM user WHERE username = ?");

        try {
            statement.setString(1, username);

            var resultSet = database.executeQuery(statement);

            if (resultSet.next()) {
                var password = resultSet.getString("password");
                return Optional.of(new User(username, password));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
