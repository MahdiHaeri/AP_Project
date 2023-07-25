package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


public class Retweet extends Tweet {
    @JsonProperty("retweetId")
    private String retweetId;

    public Retweet(String tweetId, String ownerId, String text, int replyCount, int retweetCount, int likeCount, Date createdAt, String retweetId) {
        super(tweetId, ownerId, text, replyCount, retweetCount, likeCount, createdAt);
        this.retweetId = retweetId;
    }

    public Retweet() {

    }

    public String getRetweetId() {
        return retweetId;
    }

    public void setRetweetId(String retweetId) {
        this.retweetId = retweetId;
    }

    @Override
    public String toString() {
        return "Retweet{" +
                "retweetId='" + retweetId + '\'' +
                '}';
    }
}
