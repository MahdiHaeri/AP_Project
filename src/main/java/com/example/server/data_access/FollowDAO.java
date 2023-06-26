package com.example.server.data_access;

import com.example.server.models.Follow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowDAO {

    private final Connection connection;
    public FollowDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createFollowTable();
    }

    public void createFollowTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS follows (follower VARCHAR(36), followed VARCHAR(36), PRIMARY KEY (follower, followed))");
        preparedStatement.executeUpdate();
    }

    public void saveFollow(Follow follow) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO follows (follower, followed) VALUES (?, ?)");
        preparedStatement.setString(1, follow.getFollower());
        preparedStatement.setString(2, follow.getFollowed());
        preparedStatement.executeUpdate();
    }

    public void deleteFollow(Follow follow) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM follows WHERE follower = ? AND followed = ?");
        preparedStatement.setString(1, follow.getFollower());
        preparedStatement.setString(2, follow.getFollowed());
        preparedStatement.executeUpdate();
    }

    public List<Follow> getFollows(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE follower = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Follow> follows = new ArrayList<>();
        while (resultSet.next()) {
            Follow follow = new Follow();
            follow.setFollower(resultSet.getString("follower"));
            follow.setFollowed(resultSet.getString("followed"));
            follows.add(follow);
        }
        return follows;
    }

    public List<Follow> getFollowers(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE followed = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Follow> follows = new ArrayList<>();
        while (resultSet.next()) {
            Follow follow = new Follow();
            follow.setFollower(resultSet.getString("follower"));
            follow.setFollowed(resultSet.getString("followed"));
            follows.add(follow);
        }
        return follows;
    }

    public boolean isFollowing(String followerId, String followedId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM follows WHERE follower = ? AND followed = ?");
        preparedStatement.setString(1, followerId);
        preparedStatement.setString(2, followedId);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isFollowing = resultSet.next();
        return isFollowing;
    }
}
