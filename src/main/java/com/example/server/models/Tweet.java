package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.ArrayList;

public class Tweet {
    private static int TotalNumOfTweets;

    static {
        TotalNumOfTweets = 0;
    }
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

    @JsonProperty("likes")
    private int likes;

    @JsonProperty("retweets")
    private int retweets;

    @JsonProperty("replies")
    private int replies;

    @JsonProperty("createdAt")
    private Date createdAt;

    public Tweet(String id, String writerId, String ownerId, String text, String quoteTweetId, Date createdAt, int likes, int retweets, int replies) {
        this.id = id;
        this.writerId = writerId;
        this.ownerId = ownerId;
        this.text = text;
        this.quoteTweetId = quoteTweetId;
        this.createdAt = createdAt;
        this.likes = likes;
        this.retweets = retweets;
        this.replies = replies;
        mediaPaths = new ArrayList<String>();
    }

    public Tweet(String id, String writerId, String ownerId, String text, String quoteTweetId, Date createdAt) {
        this.id = id;
        this.writerId = writerId;
        this.ownerId = ownerId;
        this.text = text;
        this.quoteTweetId = quoteTweetId;
        this.createdAt = createdAt;
        likes = 0;
        retweets = 0;
        replies = 0;
        mediaPaths = new ArrayList<String>();
    }

    public Tweet() {

    }

    public String getId() {
        return id;
    }
    public static int getTotalNumOfTweets() { return TotalNumOfTweets;}
    public static void IncTotalNumOfTweets() {TotalNumOfTweets++;}
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

    public void setMediaPaths(String[] mediaPaths) {
        for (String mediaPath : mediaPaths) {
            this.mediaPaths.add(mediaPath);
        }
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void incrementLikes() {
        this.likes++;
    }

    public void incrementRetweets() {
        this.retweets++;
    }

    public void incrementReplies() {
        this.replies++;
    }

    public void decrementLikes() {
        this.likes--;
    }

    public void decrementRetweets() {
        this.retweets--;
    }

    public void decrementReplies() {
        this.replies--;
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
                ", likes=" + likes +
                ", retweets=" + retweets +
                ", replies=" + replies +
                ", createdAt=" + createdAt +
                '}';
    }
}