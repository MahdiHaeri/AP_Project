package com.example.server.controllers;

import com.example.server.data_access.FollowDAO;
import com.example.server.data_access.UserDAO;
import com.example.server.models.Follow;
import com.example.server.models.User;

import java.sql.SQLException;
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

    public void getFollows(String userId) throws SQLException {
        followDAO.getFollows(userId);
    }

    public void getFollowers(String userId) throws SQLException {
        followDAO.getFollowers(userId);
    }
}