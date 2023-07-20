package com.example.server.controllers;

import com.example.server.data_access.LikeDAO;

import java.sql.SQLException;

public class LikeController {
    private final LikeDAO likeDAO;

    public LikeController() throws SQLException {
        likeDAO = new LikeDAO();
    }

    public void createLike(String userId, String tweetId) throws SQLException {
        likeDAO.saveLike(userId, tweetId);
    }

    public void deleteLike(String userId, String tweetId) throws SQLException {
        likeDAO.deleteLike(userId, tweetId);
    }

    public void deleteLikes() throws SQLException {
        likeDAO.deleteLikes();
    }

    public void deleteLikesByTweetId(String tweetId) throws SQLException {
        likeDAO.deleteLikesByTweetId(tweetId);
    }

    public void deleteLikesByUserId(String userId) throws SQLException {
        likeDAO.deleteLikesByUserId(userId);
    }

    public boolean isLiked(String userId, String tweetId) throws SQLException {
        return likeDAO.isLiked(userId, tweetId);
    }
}
