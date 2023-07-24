package com.example.server.controllers;

import java.sql.SQLException;

import com.example.server.data_access.FollowDAO;
import com.example.server.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.server.data_access.TweetDAO;

import java.util.ArrayList;
import java.util.Date;

public class TweetController {
    private final TweetDAO tweetDAO;

    private final FollowDAO followDAO;
    public TweetController() throws SQLException {
        tweetDAO = new TweetDAO();
        followDAO = new FollowDAO();
    }

    public void createTweet(String ownerId, String text) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweetDAO.saveTweet(tweet);
    }

    public void createRetweet(String ownerId, String text, String retweetId) throws SQLException {
        Retweet retweet = new Retweet();
        retweet.setOwnerId(ownerId);
        retweet.setText(text);
        retweet.setRetweetId(retweetId);
        tweetDAO.saveTweet(retweet);
    }

    public void createQuoteTweet(String ownerId, String text, String quoteTweetId) throws SQLException {
        QuoteTweet quoteTweet = new QuoteTweet();
        quoteTweet.setOwnerId(ownerId);
        quoteTweet.setText(text);
        quoteTweet.setQuoteTweetId(quoteTweetId);
        tweetDAO.saveTweet(quoteTweet);
    }

    public void createReplyTweet(String ownerId, String text, String replyTweetId) throws SQLException {
        ReplyTweet replyTweet = new ReplyTweet();
        replyTweet.setOwnerId(ownerId);
        replyTweet.setText(text);
        replyTweet.setParentTweetId(replyTweetId);
        tweetDAO.saveTweet(replyTweet);
    }

    public void updateRetweet(String ownerId, String text, int replyCount, int retweetCount, int likeCount, String retweetId) throws SQLException {
        Retweet retweet = new Retweet();
        retweet.setOwnerId(ownerId);
        retweet.setText(text);
        retweet.setReplyCount(replyCount);
        retweet.setRetweetCount(retweetCount);
        retweet.setLikeCount(likeCount);
        retweet.setRetweetId(retweetId);
        tweetDAO.updateTweet(retweet);
    }

    public void updateTweet(String ownerId, String text, int replyCount, int retweetCount, int likeCount) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setOwnerId(ownerId);
        tweet.setText(text);
        tweet.setReplyCount(replyCount);
        tweet.setRetweetCount(retweetCount);
        tweet.setLikeCount(likeCount);
        tweetDAO.updateTweet(tweet);
    }

    public void updateQuoteTweet(String ownerId, String text, int replyCount, int retweetCount, int likeCount, String quoteTweetId) throws SQLException {
        QuoteTweet quoteTweet = new QuoteTweet();
        quoteTweet.setOwnerId(ownerId);
        quoteTweet.setText(text);
        quoteTweet.setReplyCount(replyCount);
        quoteTweet.setRetweetCount(retweetCount);
        quoteTweet.setLikeCount(likeCount);
        quoteTweet.setQuoteTweetId(quoteTweetId);
        tweetDAO.updateTweet(quoteTweet);
    }

    public void updateReplyTweet(String ownerId, String text, int replyCount, int retweetCount, int likeCount, String replyTweetId) throws SQLException {
        ReplyTweet replyTweet = new ReplyTweet();
        replyTweet.setOwnerId(ownerId);
        replyTweet.setText(text);
        replyTweet.setReplyCount(replyCount);
        replyTweet.setRetweetCount(retweetCount);
        replyTweet.setLikeCount(likeCount);
        replyTweet.setParentTweetId(replyTweetId);
        tweetDAO.updateTweet(replyTweet);
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

    public String getTweetsByOwnerId(String ownerId) throws SQLException, JsonProcessingException {
        ArrayList<Tweet> tweets =  tweetDAO.getTweets(ownerId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweets);
    }

    public String getRetweetsByOwnerId(String ownerId) throws SQLException, JsonProcessingException {
        ArrayList<Retweet> retweets =  tweetDAO.getRetweets(ownerId);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(retweets);
    }

    public String getTweet(String id) throws SQLException, JsonProcessingException {
        Tweet tweet = tweetDAO.getTweet(id);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweet);
    }

    public String getTimeline(String username) throws SQLException, JsonProcessingException {
        // todo : add trends tweet to timeline tweets
        // todo : remove Blocked users tweets from timeline tweets:w
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