package com.example.server.data_access;

import com.example.server.models.QuoteTweet;
import com.example.server.models.ReplyTweet;
import com.example.server.models.Retweet;
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
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tweets (tweet_id SERIAL PRIMARY KEY, owner_id VARCHAR(36) NOT NULL, text VARCHAR(280) NOT NULL, reply_count INT DEFAULT 0, retweet_count INT DEFAULT 0, like_count INT DEFAULT 0, retweet_id VARCHAR(36), quote_tweet_id VARCHAR(36), parent_tweet_id VARCHAR(36), created_at TIMESTAMP DEFAULT now())");
        statement.executeUpdate();
    }

    public void saveTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (tweet instanceof Retweet retweet) {
                statement = connection.prepareStatement("INSERT INTO tweets (owner_id, text, reply_count, retweet_count, like_count, retweet_id) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(6, retweet.getRetweetId());

            } else if (tweet instanceof QuoteTweet quoteTweet) {
                statement = connection.prepareStatement("INSERT INTO tweets (owner_id, text, reply_count, retweet_count, like_count, quote_tweet_id) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(6, quoteTweet.getQuoteTweetId());

            } else if (tweet instanceof ReplyTweet replyTweet) {
                statement = connection.prepareStatement("INSERT INTO tweets (owner_id, text, reply_count, retweet_count, like_count, parent_tweet_id) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(6, replyTweet.getParentTweetId());

            } else {
                statement = connection.prepareStatement("INSERT INTO tweets (owner_id, text, reply_count, retweet_count, like_count) VALUES (?, ?, ?, ?, ?)");
            }
            statement.setString(1, tweet.getOwnerId());
            statement.setString(2, tweet.getText());
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void updateTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (tweet instanceof Retweet retweet) {
                statement = connection.prepareStatement("UPDATE tweets SET owner_id = ?, text = ?, reply_count = ?, retweet_count = ?, like_count = ?, retweet_id = ? WHERE tweet_id = ?");
                statement.setString(6, retweet.getRetweetId());

            } else if (tweet instanceof QuoteTweet quoteTweet) {
                statement = connection.prepareStatement("UPDATE tweets SET owner_id = ?, text = ?, reply_count = ?, retweet_count = ?, like_count = ?, quote_tweet_id = ? WHERE tweet_id = ?");
                statement.setString(6, quoteTweet.getQuoteTweetId());

            } else if (tweet instanceof ReplyTweet replyTweet) {
                statement = connection.prepareStatement("UPDATE tweets SET owner_id = ?, text = ?, reply_count = ?, retweet_count = ?, like_count = ?, parent_tweet_id = ? WHERE tweet_id = ?");
                statement.setString(6, replyTweet.getParentTweetId());

            } else {
                statement = connection.prepareStatement("UPDATE tweets SET owner_id = ?, text = ?, reply_count = ?, retweet_count = ?, like_count = ? WHERE tweet_id = ?");
            }
            statement.setString(1, tweet.getOwnerId());
            statement.setString(2, tweet.getText());
            statement.setInt(3, tweet.getReplyCount());
            statement.setInt(4, tweet.getRetweetCount());
            statement.setInt(5, tweet.getLikeCount());
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void deleteTweets() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets");
        statement.executeUpdate();
    }

    public void deleteTweet(Tweet tweet) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE tweet_id = ?");
        statement.setString(1, tweet.getTweetId());
        statement.executeUpdate();
    }

    public void deleteTweet(String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE tweet_id = ?");
        statement.setInt(1, Integer.parseInt(tweetId));
        statement.executeUpdate();
    }

    public Tweet getTweet(String tweetId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE tweet_id = ?");
        statement.setInt(1, Integer.parseInt(tweetId));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String ownerId = (resultSet.getString("owner_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String retweetId = resultSet.getString("retweet_id");
            String quoteTweetId = resultSet.getString("quote_tweet_id");
            String parentTweetId = resultSet.getString("parent_tweet_id");

            if (retweetId != null) {
                return new Retweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, retweetId);
            } else if (quoteTweetId != null) {
                return new QuoteTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, quoteTweetId);
            } else if (parentTweetId != null) {
                return new ReplyTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, parentTweetId);
            } else {
                return new Tweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount);
            }
        }
        return null;
    }

    public ArrayList<Tweet> getTweets() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Tweet> tweets = new ArrayList<>();
        while (resultSet.next()) {
            String tweetId = (resultSet.getString("tweet_id"));
            String ownerId = (resultSet.getString("owner_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String retweetId = resultSet.getString("retweet_id");
            String quoteTweetId = resultSet.getString("quote_tweet_id");
            String parentTweetId = resultSet.getString("parent_tweet_id");

            if (retweetId != null) {
                tweets.add(new Retweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, retweetId));
            } else if (quoteTweetId != null) {
                tweets.add(new QuoteTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, quoteTweetId));
            } else if (parentTweetId != null) {
                tweets.add(new ReplyTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, parentTweetId));
            } else {
                tweets.add(new Tweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount));
            }
        }
        return tweets;
    }

    public ArrayList<Tweet> getTweets(String ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ?");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Tweet> tweets = new ArrayList<>();
        while (resultSet.next()) {
            String tweetId = (resultSet.getString("tweet_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String retweetId = resultSet.getString("retweet_id");
            String quoteTweetId = resultSet.getString("quote_tweet_id");
            String parentTweetId = resultSet.getString("parent_tweet_id");

            if (retweetId != null) {
                tweets.add(new Retweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, retweetId));
            } else if (quoteTweetId != null) {
                tweets.add(new QuoteTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, quoteTweetId));
            } else if (parentTweetId != null) {
                tweets.add(new ReplyTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, parentTweetId));
            } else {
                tweets.add(new Tweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount));
            }
        }
        return tweets;
    }

    public ArrayList<Retweet> getRetweets(String ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ? AND retweet_id IS NOT NULL");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Retweet> retweets = new ArrayList<>();
        while (resultSet.next()) {
            String tweetId = (resultSet.getString("tweet_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String retweetId = resultSet.getString("retweet_id");
            retweets.add(new Retweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, retweetId));
        }
        return retweets;
    }

    public ArrayList<QuoteTweet> getQuoteTweets(String ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ? AND quote_tweet_id IS NOT NULL");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<QuoteTweet> quoteTweets = new ArrayList<>();
        while (resultSet.next()) {
            String tweetId = (resultSet.getString("tweet_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String quoteTweetId = resultSet.getString("quote_tweet_id");
            quoteTweets.add(new QuoteTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, quoteTweetId));
        }
        return quoteTweets;
    }

    public ArrayList<ReplyTweet> getReplyTweets(String ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE owner_id = ? AND parent_tweet_id IS NOT NULL");
        statement.setString(1, ownerId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<ReplyTweet> replyTweets = new ArrayList<>();
        while (resultSet.next()) {
            String tweetId = (resultSet.getString("tweet_id"));
            String text = (resultSet.getString("text"));
            int retweetCount = (resultSet.getInt("retweet_count"));
            int replyCount = (resultSet.getInt("reply_count"));
            int likeCount = (resultSet.getInt("like_count"));
            String parentTweetId = resultSet.getString("parent_tweet_id");
            replyTweets.add(new ReplyTweet(tweetId, ownerId, text, replyCount, retweetCount, likeCount, parentTweetId));
        }
        return replyTweets;
    }
}
