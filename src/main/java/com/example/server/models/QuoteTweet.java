package com.example.server.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class QuoteTweet extends Tweet {
    @JsonProperty("quoteTweetId")
    private String quoteTweetId;


    public QuoteTweet(String tweetId, String ownerId, String text, int replyCount, int retweetCount, int likeCount, Date createdAt, String quoteTweetId) {
        super(tweetId, ownerId, text, replyCount, retweetCount, likeCount, createdAt);
        this.quoteTweetId = quoteTweetId;
    }

    public QuoteTweet() {

    }

    public String getQuoteTweetId() {
        return quoteTweetId;
    }

    public void setQuoteTweetId(String quoteTweetId) {
        this.quoteTweetId = quoteTweetId;
    }

    @Override
    public String toString() {
        return "QuoteTweet{" +
                "quoteTweetId='" + quoteTweetId + '\'' +
                '}';
    }
}
