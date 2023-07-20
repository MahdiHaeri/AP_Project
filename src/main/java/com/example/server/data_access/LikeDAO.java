package com.example.server.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LikeDAO {
    private final Connection connection;

    public LikeDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createLikeTable();
    }

    private void createLikeTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS likes (like_id SERIAL PRIMARY KEY, user_id VARCHAR(36), tweet_id VARCHAR(36), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        statement.executeUpdate();
    }

    public void saveLike(String userId, String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO likes (user_id, tweet_id) VALUES (?, ?)");
        statement.setString(1, userId);
        statement.setString(2, tweetId);

        statement.executeUpdate();
    }

    public void deleteLike(String userId, String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM likes WHERE user_id = ? AND tweet_id = ?");
        statement.setString(1, userId);
        statement.setString(2, tweetId);

        statement.executeUpdate();
    }

    public void deleteLikes() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM likes");
        statement.executeUpdate();
    }

    public void deleteLikesByTweetId(String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM likes WHERE tweet_id = ?");
        statement.setString(1, tweetId);
        statement.executeUpdate();
    }

    public void deleteLikesByUserId(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM likes WHERE user_id = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteLikesByUserIdAndTweetId(String userId, String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM likes WHERE user_id = ? AND tweet_id = ?");
        statement.setString(1, userId);
        statement.setString(2, tweetId);
        statement.executeUpdate();
    }
}
