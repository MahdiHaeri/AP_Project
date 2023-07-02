package com.example.server.data_access;

import com.example.server.models.Block;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlockDAO {
    private final Connection connection;

    public BlockDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createBlockTable();
    }

    public void createBlockTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS blocks (blocker VARCHAR(36), blocked VARCHAR(36), PRIMARY KEY (blocker, blocked))");
        preparedStatement.executeUpdate();
    }

    public void saveBlock(Block block) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO blocks (blocker, blocked) VALUES (?, ?)");
        preparedStatement.setString(1, block.getBlocker());
        preparedStatement.setString(2, block.getBlocked());
        preparedStatement.executeUpdate();
    }

    public void deleteBlocks() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM blocks");
        preparedStatement.executeUpdate();
    }

    public void deleteBlock(Block block) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM blocks WHERE blocker = ? AND blocked = ?");
        preparedStatement.setString(1, block.getBlocker());
        preparedStatement.setString(2, block.getBlocked());
        preparedStatement.executeUpdate();
    }

    public ArrayList<Block> getBlocks() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM blocks");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Block> blocks = new ArrayList<>();
        while (resultSet.next()) {
            Block block = new Block();
            block.setBlocker(resultSet.getString("blocker"));
            block.setBlocked(resultSet.getString("blocked"));
            blocks.add(block);
        }
        return blocks;
    }

    public ArrayList<Block> getBlockers(String userId) throws SQLException { PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM blocks WHERE blocked = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Block> blocks = new ArrayList<>();
        while (resultSet.next()) {
            Block block = new Block();
            block.setBlocker(resultSet.getString("blocker"));
            block.setBlocked(resultSet.getString("blocked"));
            blocks.add(block);
        }
        return blocks;
    }
    public ArrayList<Block> getBlockings(String userId) throws SQLException { PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM blocks WHERE blocker = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Block> blocks = new ArrayList<>();
        while (resultSet.next()) {
            Block block = new Block();
            block.setBlocker(resultSet.getString("blocker"));
            block.setBlocked(resultSet.getString("blocked"));
            blocks.add(block);
        }
        return blocks;
    }


    public boolean isBlocking(String blockerId, String blockedId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM blocks WHERE blocker = ? AND blocked = ?");
        preparedStatement.setString(1, blockerId);
        preparedStatement.setString(2, blockedId);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isBlocking = resultSet.next();
        return isBlocking;

    }
}
