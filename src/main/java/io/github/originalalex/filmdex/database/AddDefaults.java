package io.github.originalalex.filmdex.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddDefaults {

    public static void addDefaults(Connection connection, String dbName) throws SQLException {
        PreparedStatement createDb = connection.prepareStatement("CREATE DATABASE ? IF NOT EXISTS");
        Statement createDefaults = connection.createStatement();
        createDefaults.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName + ";");
        createDefaults.close();
        connection.setCatalog(dbName);
        createDefaults = connection.createStatement();
        createDefaults.executeUpdate(
        "CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT NOT NULL PRIMARY KEY," +
                "Username VARCHAR(16)  NOT NULL," +
                "PasswordHash VARCHAR(64) NOT NULL," +
                "Email VARCHAR(255) NOT NULL," +
                "JoinDate DATETIME" +
            ");"
        );
        createDefaults.execute(
        "CREATE TABLE IF NOT EXISTS posts(" +
                "id BIGINT NOT NULL PRIMARY KEY," +
                "PosterID BIGINT NOT NULL," +
                "ParentID BIGINT NOT NULL," +
                "Location VARCHAR(512) NOT NULL," +
                "Upvotes INT," +
                "Downvotes INT," +
                "TimePosted DATETIME," +
                "Content VARCHAR(2000)," +
                "FOREIGN KEY (PosterID) REFERENCES users(id)," +
                "FOREIGN KEY (ParentID) REFERENCES posts(id)" +
            ");"
        );
        createDefaults.close();
    }

}
