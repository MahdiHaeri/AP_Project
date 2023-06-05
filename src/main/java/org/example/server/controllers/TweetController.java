package org.example.server.controllers;

import java.sql.Date;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.data_access.TweetDAO;
import org.example.server.models.Tweet;

import java.time.LocalDate;

public class TweetController {
    private final TweetDAO tweetDAO;

    public TweetController() throws SQLException {
        tweetDAO = new TweetDAO();
    }

    public void createTweet(String writerId, String ownerId, String text, String quoteTweetId, String[] mediaPaths, int likes, int retweets, int replies) throws SQLException {
        Tweet tweet = new Tweet();

        tweet.setId(String.format("%d", Tweet.getTotalNumOfTweets()));
        Tweet.IncTotalNumOfTweets();
        tweet.setWriterId(writerId);
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweet.setQuoteTweetId(quoteTweetId);
        tweet.setMediaPaths(mediaPaths);
        tweet.setLikes(likes);
        tweet.setRetweets(retweets);
        tweet.setReplies(replies);
        tweet.setCreatedAt(Date.valueOf(LocalDate.now()));
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

    public Tweet getTweet(String id) throws SQLException {
        return tweetDAO.getTweet(id);
    }
    public String getTweetById(String id) throws SQLException, JsonProcessingException {
        Tweet tweet = getTweet(id);
        if (tweet == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(tweet);
        return response;
    }
}