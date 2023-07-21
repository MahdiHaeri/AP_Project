package com.example.server.HttpHandlers;

import com.example.server.controllers.BlockController;
import com.example.server.utils.JWTController;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class BlockHandler {
    private final BlockController blockController;

    public BlockHandler() throws SQLException {
        blockController = new BlockController();
    }

    public Object handleGetBlocks(Request request, Response response) {
        try {
            return blockController.getBlocks();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }


    public Object handleGetBlockers(Request request, Response response) {
        try {
            return blockController.getBlockers(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetBlocking(Request request, Response response) {
        try {
            return blockController.getBlockings(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostBlock(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

//        if (!JWTController.validateJwtToken(token)) {
//            response.status(401);
//            return "Unauthorized";
//        }

        String blockerId = JWTController.getUsernameFromJwtToken(token);
        String blockedId = request.params(":username");
        try {
            blockController.saveBlock(blockerId, blockedId);
            response.status(201);
            return "Follow created successfully!";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostUnblock(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

//        if (!JWTController.validateJwtToken(token)) {
//            response.status(401);
//            return "Unauthorized";
//        }

        String blockerId = JWTController.getUsernameFromJwtToken(token);
        String blockedId = request.params(":username");
        try {
            blockController.deleteBlock(blockerId, blockedId);
            response.status(201);
            return "Follow created successfully!";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }
}
