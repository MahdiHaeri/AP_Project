package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.ArrayList;

public class Tweet {
    @JsonProperty("id")
    private String id;

    @JsonProperty("writerId")
    private String writerId;

    @JsonProperty("ownerId")
    private String ownerId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("quoteTweetId")
    private String quoteTweetId;

    @JsonProperty("mediaPaths")
    private ArrayList<String> mediaPaths;

    @JsonProperty("replies")
    private int replies;

    @JsonProperty("retweets")
    private int retweets;

    @JsonProperty("likes")
    private int likes;

    @JsonProperty("createdAt")
    private Date createdAt;

    public Tweet(String id, String writerId, String ownerId, String text, String quoteTweetId, ArrayList<String> mediaPaths, int replies, int retweets, int likes, Date createdAt) {
        this.id = id;
        this.writerId = writerId;
        this.ownerId = ownerId;
        this.text = text;
        this.quoteTweetId = quoteTweetId;
        this.mediaPaths = mediaPaths;
        this.replies = replies;
        this.retweets = retweets;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public Tweet(String writerId, String ownerId, String text, String quoteTweetId, ArrayList<String> mediaPaths) {
        this.writerId = writerId;
        this.ownerId = ownerId;
        this.text = text;
        this.quoteTweetId = quoteTweetId;
        this.mediaPaths = mediaPaths;
        this.replies = 0;
        this.retweets = 0;
        this.likes = 0;
        this.createdAt = new Date();
    }

    public Tweet() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
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

    public String getQuoteTweetId() {
        return quoteTweetId;
    }

    public void setQuoteTweetId(String quoteTweetId) {
        this.quoteTweetId = quoteTweetId;
    }

    public ArrayList<String> getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(ArrayList<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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
                "id='" + id + '\'' +
                ", writerId='" + writerId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", text='" + text + '\'' +
                ", quoteTweetId='" + quoteTweetId + '\'' +
                ", mediaPaths=" + mediaPaths +
                ", replies=" + replies +
                ", retweets=" + retweets +
                ", likes=" + likes +
                ", createdAt=" + createdAt +
                '}';
    }
}