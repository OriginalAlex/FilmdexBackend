package io.github.originalalex.filmdex.database;

import java.sql.*;
import java.util.Calendar;

public class DatabaseModifier {

    private Connection databaseConnection;

    private int nextUserID; // the next available number to select as the user ID for the next created account
    private int nextPostID; // ^^ for posts

    public DatabaseModifier(String host, String port, String username, String password, String dbName) {
        System.out.println("jdbc:mysql://" + host + ":" + port + "");
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port,
                    username,
                    password);
            AddDefaults.addDefaults(connection, dbName);
            this.databaseConnection = connection;
            this.setNextIDs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setNextIDs() {
        try {
            Statement statement = databaseConnection.createStatement();
            ResultSet maxUserID = statement.executeQuery("SELECT MAX(id) FROM users;");
            if (maxUserID.next()) {
                this.nextUserID = maxUserID.getInt(1) + 1;
            }
            ResultSet maxPostID = statement.executeQuery("SELECT MAX(id) FROM posts;");
            if (maxPostID.next()) {
                this.nextPostID = maxPostID.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createUser(String username, String passwordHash, String email) {
        try {
            PreparedStatement createUser = databaseConnection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?);");
            createUser.setInt(1, nextUserID);
            createUser.setString(2, username);
            createUser.setString(3, passwordHash);
            createUser.setString(4, email);
            createUser.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            createUser.executeUpdate();
            createUser.close();
            this.nextUserID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPost(int posterID, int parentID, String location, String content) {
        try {
            PreparedStatement addPost = databaseConnection.prepareStatement("INSERT INTO posts VALUES(?,?,?,?,0,0,?,?);");
            addPost.setInt(1, nextPostID);
            addPost.setInt(2, posterID);
            addPost.setInt(3, parentID);
            addPost.setString(4, location);
            addPost.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            addPost.setString(6, content);
            addPost.executeUpdate();
            addPost.close();
            nextPostID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUsername(int id, String newUsername) {
        try {
            PreparedStatement changeUsername = databaseConnection.prepareStatement("UPDATE users " +
                    "SET Username=? " +
                    "WHERE id=?;");
            changeUsername.setString(1, newUsername);
            changeUsername.setInt(2, id);
            changeUsername.executeUpdate();
            changeUsername.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upvotePost(int id) {
        try {
             PreparedStatement upvotePost = databaseConnection.prepareStatement("UPDATE posts " +
                     "SET Upvotes=Upvotes+1 " +
                     "WHERE id=?;");
             upvotePost.setInt(1, id);
             upvotePost.executeUpdate();
             upvotePost.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downvotePost(int id) {
        try {
            PreparedStatement downvotePost = databaseConnection.prepareStatement("UPDATE posts " +
                    "SET Downvotes=Downvotes+1 " +
                    "WHERE id=?");
            downvotePost.setInt(1, id);
            downvotePost.executeUpdate();
            downvotePost.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
