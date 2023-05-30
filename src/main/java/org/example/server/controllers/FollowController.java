package org.example.server.controllers;

import org.example.server.data_access.FollowDAO;
import org.example.server.models.Follow;
import java.sql.SQLException;
public class FollowController {
    private final FollowDAO followDAO;
    public FollowController() throws SQLException {
        followDAO = new FollowDAO();
    }

    public void createFollowTable() throws SQLException {
        followDAO.createFollowTable();
    }

    public void saveFollow(String follower, String followed) throws SQLException {
        Follow follow = new Follow(follower, followed);
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