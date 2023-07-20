package com.example.server.models;

import java.util.Date;

public class Like {
    private String id;
    private String userId;
    private String tweetId;
    private Date createdAt;

    public Like(String id, String userId, String tweetId, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }

    public Like() {

    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", tweetId='" + tweetId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
