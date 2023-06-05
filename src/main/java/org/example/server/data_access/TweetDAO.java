package org.example.server.data_access;

import org.example.server.models.Tweet;

import java.sql.*;

public class TweetDAO {

    private final Connection connection;
    public TweetDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createTweetTable();
    }

    public void createTweetTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tweets (id SERIAL PRIMARY KEY, writer_id VARCHAR(36) NOT NULL, owner_id VARCHAR(36) NOT NULL, text VARCHAR(280) NOT NULL, quote_tweet_id VARCHAR(36), media_path TEXT[] , likes INTEGER NOT NULL, retweets INTEGER NOT NULL, replies INTEGER NOT NULL, create_at DATE NOT NULL)");
        statement.executeUpdate();
    }
    
    public void saveTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tweets (writer_id, owner_id, text, quote_tweet_id, media_path, likes, retweets, replies, create_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, tweet.getWriterId());
        statement.setString(2, tweet.getOwnerId());
        statement.setString(3, tweet.getText());
        statement.setString(4, tweet.getQuoteTweetId());
        statement.setArray(5, connection.createArrayOf("text", tweet.getMediaPaths().toArray()));
        statement.setInt(6, tweet.getLikes());
        statement.setInt(7, tweet.getRetweets());
        statement.setInt(8, tweet.getReplies());
        statement.setDate(9, new java.sql.Date(tweet.getCreatedAt().getTime()));
        statement.executeUpdate();
    }

    public void updateTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE tweets SET writer_id = ?, owner_id = ?, text = ?, quote_tweet_id = ?, media_path = ?, likes = ?, retweets = ?, replies = ?, create_at = ? WHERE id = ?");
        statement.setString(1, tweet.getWriterId());
        statement.setString(2, tweet.getOwnerId());
        statement.setString(3, tweet.getText());
        statement.setString(4, tweet.getQuoteTweetId());
        statement.setArray(5, connection.createArrayOf("text", tweet.getMediaPaths().toArray()));
        statement.setInt(6, tweet.getLikes());
        statement.setInt(7, tweet.getRetweets());
        statement.setInt(8, tweet.getReplies());
        statement.setDate(9, new java.sql.Date(tweet.getCreatedAt().getTime()));
        statement.setString(10, tweet.getId());
        statement.executeUpdate();
    }

    public void deleteTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE id = ?");
        statement.setString(1, tweet.getId());
        statement.executeUpdate();
    }

    public void deleteTweet(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
    }

    public Tweet getTweet(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array x = resultSet.getArray("media_path");
            String[] mediapath = (String[]) x.getArray();
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }

    public Tweet getTweet(Tweet tweet1) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE id = ?");
        statement.setString(1, tweet1.getId());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array x = resultSet.getArray("media_path");
            String[] mediapath = (String[]) x.getArray();
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }

    public Tweet getTweetByWriterId(String writerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE writer_id = ?");
        statement.setString(1, writerId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array x = resultSet.getArray("media_path");
            String[] mediapath = (String[]) x.getArray();
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }

    public Tweet getTweetByOwnerId(String ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ?");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array x = resultSet.getArray("media_path");
            String[] mediapath = (String[]) x.getArray();
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }

    public Tweet getTweetByQuoteTweetId(String quoteTweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE quote_tweet_id = ?");
        statement.setString(1, quoteTweetId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array x = resultSet.getArray("media_path");
            String[] mediapath = (String[]) x.getArray();
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }
}
