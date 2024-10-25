package me.niculicicris.filestore.data.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabase {
    void executeAction(PreparedStatement statement) throws SQLException;

    ResultSet executeQuery(PreparedStatement statement) throws SQLException;

    PreparedStatement createPreparedStatement(String sql);
}
