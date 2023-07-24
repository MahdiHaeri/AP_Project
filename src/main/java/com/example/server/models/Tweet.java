package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.ArrayList;

public class Tweet {
    @JsonProperty("tweetId")
    private String tweetId;

    @JsonProperty("ownerId")
    private String ownerId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("replyCount")
    private int replyCount;

    @JsonProperty("retweetCount")
    private int retweetCount;

    @JsonProperty("likeCount")
    private int likeCount;

    @JsonProperty("createdAt")
    private Date createdAt;

    public Tweet(String tweetId, String ownerId, String text, int replyCount, int retweetCount, int likeCount) {
        this.tweetId = tweetId;
        this.ownerId = ownerId;
        this.text = text;
        this.replyCount = replyCount;
        this.retweetCount = retweetCount;
        this.likeCount = likeCount;
        this.createdAt = new Date();
    }

    public Tweet() {

    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetId='" + tweetId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", text='" + text + '\'' +
                ", replyCount=" + replyCount +
                ", retweetCount=" + retweetCount +
                ", likeCount=" + likeCount +
                ", createdAt=" + createdAt +
                '}';
    }
}