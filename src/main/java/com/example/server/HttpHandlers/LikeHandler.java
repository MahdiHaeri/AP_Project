package com.example.server.HttpHandlers;

import com.example.server.controllers.LikeController;
import com.example.server.utils.JWTController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class LikeHandler {
    private final LikeController likeController;

    public LikeHandler() throws SQLException {
        likeController = new LikeController();
    }

    public Object handleGetLike(Request request, Response response) {
        try {
            return likeController.getLikes();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetLikeByTweetId(Request request, Response response) {
        String tweetId = request.params(":tweetId");
        try {
            response.body(likeController.getLikesByTweetId(tweetId));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handleGetLikeByUserId(Request request, Response response) {
        String userId = request.params(":username");
        try {
            response.body(likeController.getLikesByUserId(userId));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handlePostLike(Request request, Response response) throws JsonProcessingException {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        String userId = JWTController.getSubjectFromJwt(token);
        String tweetId = request.params(":tweetId");

        try {
            likeController.createLike(userId, tweetId);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Like created successfully");
        return response.body();
    }

    public Object handlePostUnlike(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        String userId = JWTController.getSubjectFromJwt(token);
        String tweetId = request.params(":tweetId");

        try {
            likeController.deleteLike(userId, tweetId);
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }

        response.body("Like deleted successfully");
        return response.body();
    }
}