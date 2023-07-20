package com.example.server.models;

public class Like {
    private String id;
    private String userId;
    private String tweetId;

    public Like(String id, String userId, String tweetId) {
        this.id = id;
        this.userId = userId;
        this.tweetId = tweetId;
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

    @Override
    public String toString() {
        return "Like{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", tweetId='" + tweetId + '\'' +
                '}';
    }
}
