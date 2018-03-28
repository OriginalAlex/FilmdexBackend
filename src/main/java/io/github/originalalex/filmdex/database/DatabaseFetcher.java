package io.github.originalalex.filmdex.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseFetcher {

    private Connection connection;

    public DatabaseFetcher(Connection connection) {
        this.connection = connection;
    }

    public String getPasswordHash(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT PasswordHash FROM users WHERE Username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUsernameEmail(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Email FROM users WHERE Username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getID(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE Username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
