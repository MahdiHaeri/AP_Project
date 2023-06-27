package com.example.server.controllers;

import com.example.server.data_access.FollowDAO;
import com.example.server.data_access.UserDAO;
import com.example.server.models.Follow;
import com.example.server.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class FollowController {
    private final FollowDAO followDAO;
    private final UserDAO userDAO;
    public FollowController() throws SQLException {
        followDAO = new FollowDAO();
        userDAO = new UserDAO();
    }

    public void createFollowTable() throws SQLException {
        followDAO.createFollowTable();
    }

    public void saveFollow(String follower, String followed) throws SQLException {
        Follow follow = new Follow(follower, followed);
        if (userDAO.getUser(follower) == null || userDAO.getUser(followed) == null) throw new SQLException("User does not exist");
        followDAO.saveFollow(follow);
    }

    public void deleteFollow(String follower, String followed) throws SQLException {
        Follow follow = new Follow(follower, followed);
        followDAO.deleteFollow(follow);
    }

    public String getFollows() throws SQLException, JsonProcessingException {
        ArrayList<Follow> follows = followDAO.getFollows();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(follows);
        return response;
    }

    public String getFollowers(String followedId) throws SQLException, JsonProcessingException {
        ArrayList<Follow> followers = followDAO.getFollowers(followedId);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(followers);
        return response;
    }

    public String getFollowings(String followerId) throws SQLException, JsonProcessingException {
        ArrayList<Follow> followings = followDAO.getFollowings(followerId);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(followings);
        return response;
    }
}