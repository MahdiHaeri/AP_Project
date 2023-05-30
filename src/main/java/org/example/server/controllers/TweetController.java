package org.example.server.controllers;

import java.sql.SQLException;

import org.example.server.data_access.TweetDAO;
import org.example.server.models.Tweet;

public class TweetController {
    private final TweetDAO tweetDAO;

    public TweetController() throws SQLException {
        tweetDAO = new TweetDAO();
    }

    public void createTweet(String writerId, String ownerId, String text, String quoteTweetId, String[] mediaPaths, int likes, int retweets, int replies) throws SQLException {
        Tweet tweet = new Tweet();

        tweet.setWriterId(writerId);
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweet.setQuoteTweetId(quoteTweetId);
        tweet.setMediaPaths(mediaPaths);
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        tweet.setReplies(replies);

        tweetDAO.saveTweet(tweet);
    }

    public void updateTweet(String writerId, String ownerId, String text, String quoteTweetId, String[] mediaPaths, int likes, int retweets, int replies) throws SQLException {
        Tweet tweet = new Tweet();

        tweet.setWriterId(writerId);
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweet.setQuoteTweetId(quoteTweetId);
        tweet.setMediaPaths(mediaPaths);
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        tweet.setReplies(replies);

        tweetDAO.updateTweet(tweet);
    }

    public void deleteTweet(String id) throws SQLException {
        tweetDAO.deleteTweet(id);
    }

    public void getTweetByWriterId(String writerId) throws SQLException {
        tweetDAO.getTweetByWriterId(writerId);
    }

    public void getTweetByOwnerId(String ownerId) throws SQLException {
        tweetDAO.getTweetByOwnerId(ownerId);
    }
}