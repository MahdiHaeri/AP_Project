package com.example.server.HttpHandlers;

import com.example.server.controllers.TweetController;
import com.example.server.utils.JWTController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TweetHandler {
    private final TweetController tweetController;

    public TweetHandler() throws SQLException {
        tweetController = new TweetController();
    }

    public Object handleGetTweets(Request request, Response response) {
        try {
            return tweetController.getTweets();
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetTweetById(Request request, Response response) {
        try {
            return tweetController.getTweet(request.params(":tweetId"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetTweetsByOwnerId(Request request, Response response) {
        try {
            return tweetController.getTweetsByOwnerId(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostTweet(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));


        JSONObject jsonObject = new JSONObject(request.body());
        String writerId = jsonObject.getString("writerId");
        String ownerId = JWTController.getUsernameFromJwtToken(token);
        String text = jsonObject.getString("text");
        String quotedTweetId = jsonObject.getString("quoteTweetId");
        JSONArray mediaPaths = jsonObject.getJSONArray("mediaPaths");
        int repliesCount = jsonObject.getInt("replies");
        int retweetsCount = jsonObject.getInt("retweets");
        int likesCount = jsonObject.getInt("likes");


        ArrayList<String> mediaPathsList = new ArrayList<>();
        for (int i = 0; i < mediaPaths.length(); i++) {
            mediaPathsList.add(mediaPaths.getString(i));
        }

        try {
            tweetController.createTweet(writerId, ownerId, text, quotedTweetId, mediaPathsList, repliesCount, retweetsCount, likesCount);
            response.status(201);
            return "Tweet created successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleDeleteTweetByTweetId(Request request, Response response) {
        try {
            tweetController.deleteTweet(request.params(":tweetId"));
            response.status(200);
            return "Tweet deleted successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleDeleteTweet(Request request, Response response) {
        try {
            tweetController.deleteTweets();
            response.status(200);
            return "Tweets deleted successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }
}