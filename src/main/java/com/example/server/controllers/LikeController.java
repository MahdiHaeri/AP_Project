package com.example.server.controllers;

import com.example.server.data_access.LikeDAO;
import com.example.server.models.Like;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public int getLikesCount(String tweetId) throws SQLException {
        return likeDAO.getLikesCount(tweetId);
    }

    public String getLikes() throws SQLException, JsonProcessingException {
        ArrayList<Like> likes = likeDAO.getLikes();
        if (likes == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(likes);
    }

    public String getLikesByTweetId(String tweetId) throws SQLException, JsonProcessingException {
        ArrayList<Like> likes = likeDAO.getLikesByTweetId(tweetId);
        if (likes == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(likes);
    }

    public String getLikesByUserId(String userId) throws SQLException, JsonProcessingException {
        ArrayList<Like> likes = likeDAO.getLikesByUserId(userId);
        if (likes == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(likes);
    }

    public boolean isLiked(String userId, String tweetId) throws SQLException {
        return likeDAO.isLiked(userId, tweetId);
    }
}
