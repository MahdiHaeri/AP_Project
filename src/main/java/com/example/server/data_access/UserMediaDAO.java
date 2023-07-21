package com.example.server.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMediaDAO {

    private final Connection connection;

    public UserMediaDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createMediaTable();
    }

    public void createMediaTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user_media (id SERIAL PRIMARY KEY, user_id VARCHAR(36) NOT NULL, media_type VARCHAR(36) NOT NULL, media_path VARCHAR(255) NOT NULL, uploaded_at TIMESTAMP NOT NULL DEFAULT NOW())");
        statement.executeUpdate();
    }

    public void saveMedia(String userId, String mediaType, String mediaPath) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO user_media (user_id, media_type, media_path) VALUES (?, ?, ?)");
        statement.setString(1, userId);
        statement.setString(2, mediaType);
        statement.setString(3, mediaPath);

        statement.executeUpdate();
    }

    public void deleteMedia(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_media WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void deleteMediaByUserId(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_media WHERE user_id = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteMedia() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_media");
        statement.executeUpdate();
    }

    public ArrayList<String> getMediaByUserIdAndType(String userId, String mediaType) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT media_path FROM user_media WHERE user_id = ? AND media_type = ?");
        statement.setString(1, userId);
        statement.setString(2, mediaType);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<String> mediaPaths = new ArrayList<>();
        while (resultSet.next()) {
            mediaPaths.add(resultSet.getString("media_path"));
        }
        return mediaPaths;
    }

    public ArrayList<String> getMediaByUserId(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT media_path FROM user_media WHERE user_id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<String> mediaPaths = new ArrayList<>();
        while (resultSet.next()) {
            mediaPaths.add(resultSet.getString("media_path"));
        }
        return mediaPaths;
    }
}