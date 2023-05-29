package org.example.controllers;

import java.sql.SQLException;

import org.example.data_access.TweetDAO;
import org.example.models.Tweet;

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
}
