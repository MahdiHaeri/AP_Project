package com.example.server.controllers;

import java.sql.SQLException;

import com.example.server.data_access.FollowDAO;
import com.example.server.data_access.UserDAO;
import com.example.server.models.Follow;
import com.example.server.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.server.data_access.TweetDAO;
import com.example.server.models.Tweet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TweetController {
    private final TweetDAO tweetDAO;

    private final FollowDAO followDAO;
    public TweetController() throws SQLException {
        tweetDAO = new TweetDAO();
        followDAO = new FollowDAO();
    }

    public void createTweet(String writerId, String ownerId, String text, String quoteTweetId, ArrayList<String> mediaPaths,int replies, int retweets, int likes) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setWriterId(writerId);
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweet.setQuoteTweetId(quoteTweetId);
        tweet.setMediaPaths(mediaPaths);
        tweet.setReplies(replies);
        tweet.setRetweets(retweets);
        tweet.setLikes(likes);
        tweet.setCreatedAt(new Date());
        tweetDAO.saveTweet(tweet);
    }

    public void updateTweet(String writerId, String ownerId, String text, String quoteTweetId, ArrayList<String> mediaPaths, int likes, int retweets, int replies) throws SQLException {
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

    public void deleteTweets() throws SQLException {
        tweetDAO.deleteTweets();
    }
    public void deleteTweet(String id) throws SQLException {
        tweetDAO.deleteTweet(id);
    }

    public String getTweets() throws SQLException, JsonProcessingException {
        ArrayList<Tweet> tweets =  tweetDAO.getTweets();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweets);
    }

    public String getTweetsByWriterId(String writerId) throws SQLException, JsonProcessingException {
        ArrayList<Tweet> tweets =  tweetDAO.getTweetsByWriterId(writerId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweets);
    }

    public String getTweetsByOwnerId(String ownerId) throws SQLException, JsonProcessingException {
        ArrayList<Tweet> tweets =  tweetDAO.getTweetsByOwnerId(ownerId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweets);
    }

    public String getTweet(String id) throws SQLException, JsonProcessingException {
        Tweet tweet = tweetDAO.getTweet(id);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweet);
    }
    public String getTweet(Tweet tweet) throws SQLException, JsonProcessingException {
        Tweet resultTweet = tweetDAO.getTweet(tweet);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweet);
    }

    public String getTimeline(String username) throws SQLException, JsonProcessingException {
        // todo : add trends tweet to timeline tweets
        ArrayList<Tweet> tweets = tweetDAO.getTweets();
        ArrayList<Follow> followings = followDAO.getFollowings(username);
        ArrayList<Tweet> timelineTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (isFollowingTweet(tweet.getOwnerId(), followings)) {
                timelineTweets.add(tweet);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(timelineTweets);
    }

    private boolean isFollowingTweet(String tweetOwnerId, ArrayList<Follow> followings) throws SQLException {
        for (Follow follow : followings) {
            if (follow.getFollowed().equals(tweetOwnerId)) {
                return true;
            }
        }
        return false;
    }
}