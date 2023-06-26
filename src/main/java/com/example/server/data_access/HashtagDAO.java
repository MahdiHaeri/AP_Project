package com.example.server.data_access;

import com.example.server.models.Hashtag;

import java.sql.*;

public class HashtagDAO {
    private final Connection connection;
    public HashtagDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createHashtagTable();
    }
    public void createHashtagTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS hashtags (id SERIAL PRIMARY KEY, tweets_id TEXT[])");
        statement.executeUpdate();
    }

    public void saveHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO hashtags (id, tweets_id) VALUES (?, ?)");
        statement.setString(1, hashtag.getHashtagId());
        statement.setArray(2, connection.createArrayOf("text", hashtag.getTweetsId().toArray()));
        statement.executeUpdate();
    }
    public void updateHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE hashtags SET tweets_id = ? WHERE id = ?");
        statement.setArray(1, connection.createArrayOf("text", hashtag.getTweetsId().toArray()));
        statement.setString(2, hashtag.getHashtagId());
        statement.executeUpdate();
    }
    public void deleteHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM hashtags WHERE id = ?");
        statement.setString(1, hashtag.getHashtagId());
        statement.executeUpdate();
    }

    public void deleteHashtag(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM hashtags WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
    }

    public Hashtag getHashtag(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hashtags WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Hashtag hashtag = new Hashtag();
            hashtag.setHashtagId(resultSet.getString("id"));
            Array x = resultSet.getArray("tweets_id");
            String[] tweets_id = (String[]) x.getArray();
            hashtag.setHashtagTweetsId(tweets_id);
            return hashtag;
        }
        return null;
    }

}
