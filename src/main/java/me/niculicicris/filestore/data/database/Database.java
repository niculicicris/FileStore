package me.niculicicris.filestore.data.database;

import java.sql.*;

public class Database implements IDatabase {
    private Connection connection;

    @Override
    public void executeAction(PreparedStatement statement) throws SQLException {
        statement.execute();
    }

    @Override
    public ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    @Override
    public PreparedStatement createPreparedStatement(String sql) {
        try {
            openConnection();
            return connection.prepareStatement(sql);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void openConnection() throws ClassNotFoundException, SQLException {
        var url = "jdbc:mysql://localhost:3306/filestore";
        var username = "root";
        var password = "password";

        connection = DriverManager.getConnection(url, username, password);
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }
}
