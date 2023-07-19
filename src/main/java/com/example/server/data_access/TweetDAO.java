package com.example.server.data_access;

import com.example.server.models.Tweet;

import java.sql.*;
import java.util.ArrayList;

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
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tweets (writer_id, owner_id, text, quote_tweet_id, media_path, likes, retweets, replies, create_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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

    public void deleteTweets() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets");
        statement.executeUpdate();
    }

    public void deleteTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE id = ?");
        statement.setString(1, tweet.getId());
        statement.executeUpdate();
    }

    public void deleteTweet(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE id = ?");
        statement.setInt(1, Integer.parseInt(id));
        statement.executeUpdate();
    }

    public Tweet getTweet(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE id = ?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediaPath = toArrayList(elements);
            tweet.setMediaPaths(mediaPath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            return tweet;
        }
        return null;
    }

    public Tweet getTweet(Tweet tweet) throws SQLException {
        System.out.println("hello world");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE id = ?");
        statement.setString(1, tweet.getId());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Tweet resultTweet = new Tweet();
            resultTweet.setId(resultSet.getString("id"));
            resultTweet.setWriterId(resultSet.getString("writer_id"));
            resultTweet.setOwnerId(resultSet.getString("owner_id"));
            resultTweet.setText(resultSet.getString("text"));
            resultTweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediapath = toArrayList(elements);
            resultTweet.setMediaPaths(mediapath);
            resultTweet.setLikes(resultSet.getInt("likes"));
            resultTweet.setRetweets(resultSet.getInt("retweets"));
            resultTweet.setReplies(resultSet.getInt("replies"));
            return resultTweet;
        }
        return null;
    }

    public ArrayList<Tweet> getTweets() throws SQLException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediapath = toArrayList(elements);
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            tweets.add(tweet);
        }
        return tweets;
    }

    public ArrayList<Tweet> getTweetsByWriterId(String writerId) throws SQLException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE writer_id = ?");
        statement.setString(1, writerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediapath = toArrayList(elements);
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            tweets.add(tweet);
        }
        return tweets;
    }

    public ArrayList<Tweet> getTweetsByOwnerId(String ownerId) throws SQLException {
        ArrayList<Tweet> tweets = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ?");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediapath = toArrayList(elements);
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            tweets.add(tweet);
        }
        return tweets;
    }

    public ArrayList<Tweet> getTweetsByQuoteTweetId(String quoteTweetId) throws SQLException {
        ArrayList<Tweet> tweets = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE quote_tweet_id = ?");
        statement.setString(1, quoteTweetId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setId(resultSet.getString("id"));
            tweet.setWriterId(resultSet.getString("writer_id"));
            tweet.setOwnerId(resultSet.getString("owner_id"));
            tweet.setText(resultSet.getString("text"));
            tweet.setQuoteTweetId(resultSet.getString("quote_tweet_id"));
            Array array = resultSet.getArray("media_path");
            Object[] elements = (Object[]) array.getArray();
            ArrayList<String> mediapath = toArrayList(elements);
            tweet.setMediaPaths(mediapath);
            tweet.setLikes(resultSet.getInt("likes"));
            tweet.setRetweets(resultSet.getInt("retweets"));
            tweet.setReplies(resultSet.getInt("replies"));
            tweets.add(tweet);
        }

        return tweets;
    }

    public static ArrayList<String> toArrayList(Object[] objects) {
        ArrayList<String> result = new ArrayList<>();
        for (Object object : objects) {
            result.add(object.toString());
        }
        return result;
    }
}
