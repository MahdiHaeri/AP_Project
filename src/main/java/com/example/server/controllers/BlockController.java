package com.example.server.controllers;

import com.example.server.data_access.BlockDAO;
import com.example.server.data_access.UserDAO;
import com.example.server.models.Block;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public class BlockController {
    private final BlockDAO blockDAO;
    private final UserDAO userDAO;
    public BlockController() throws SQLException {
        blockDAO = new BlockDAO();
        userDAO = new UserDAO();
    }

    public void createBlockTable() throws SQLException {
        blockDAO.createBlockTable();
    }

    public void saveBlock(String blocker, String blocked) throws SQLException {
        Block block = new Block(blocker, blocked);
        if (userDAO.getUser(blocker) == null || userDAO.getUser(blocked) == null) throw new SQLException("User does not exist");
        blockDAO.saveBlock(block);
    }

    public void deleteBlocks() throws SQLException {
        blockDAO.deleteBlocks();
    }

    public void deleteBlock(String blocker, String blocked) throws SQLException {
        Block block = new Block(blocker, blocked);
        blockDAO.deleteBlock(block);
    }

    public String getBlocks() throws SQLException, JsonProcessingException {
        ArrayList<Block> blocks = blockDAO.getBlocks();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(blocks);
        return response;
    }

    public String getBlockers(String blockedId) throws SQLException, JsonProcessingException {
        ArrayList<Block> blockers = blockDAO.getBlockers(blockedId);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(blockers);
        return response;
    }

    public String getBlocking(String blockerId) throws SQLException, JsonProcessingException {
        ArrayList<Block> blocking = blockDAO.getBlockings(blockerId);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(blocking);
        return response;
    }
}
