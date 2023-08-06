package com.example.server.HttpHandlers;

import com.example.server.controllers.TweetController;
import com.example.server.utils.JWTController;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public Object handleGetRetweetsByOwnerId(Request request, Response response) {
        try {
            return tweetController.getRetweetsByOwnerId(request.params(":username"));
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handleGetTimeline(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));
        String username = JWTController.getSubjectFromJwt(token);
        try {
            return tweetController.getTimeline(username);
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }


    public Object handlePostTweet(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        JSONObject jsonObject = new JSONObject(request.body());
        String ownerId = JWTController.getSubjectFromJwt(token);
        String text = jsonObject.getString("text");

        try {
            tweetController.createTweet(ownerId, text);
            response.status(201);
            return "Tweet created successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostRetweet(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        JSONObject jsonObject = new JSONObject(request.body());
        String ownerId = JWTController.getSubjectFromJwt(token);
        String text = jsonObject.getString("text");
        String retweetId = request.params(":tweetId");

        try {
            tweetController.createRetweet(ownerId, text, retweetId);
            response.status(201);
            return "Retweet created successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }

    public Object handlePostQuoteTweet(Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        JSONObject jsonObject = new JSONObject(request.body());
        String ownerId = JWTController.getSubjectFromJwt(token);
        String text = jsonObject.getString("text");
        String quoteTweetId = request.params(":tweetId");

        try {
            tweetController.createQuoteTweet(ownerId, text, quoteTweetId);
            response.status(201);
            return "Quote Tweet created successfully";
        } catch (Exception e) {
            response.status(500);
            return e.getMessage();
        }
    }


    public Object handlePostReplyTweet (Request request, Response response) {
        String token = JWTController.getJwtTokenFromHeader(request.headers("Authorization"));

        JSONObject jsonObject = new JSONObject(request.body());
        String ownerId = JWTController.getSubjectFromJwt(token);
        String text = jsonObject.getString("text");
        String replyTweetId = request.params(":tweetId");

        try {
            tweetController.createReplyTweet(ownerId, text, replyTweetId);
            response.status(201);
            return "Reply Tweet created successfully";
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

    public Object handleGetReplies(Request request, Response response) {
        String tweetId = request.params(":tweetId");
        try {
            response.body(tweetController.getRepliesByParentTweetId(tweetId));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handleGetRetweets(Request request, Response response) {
        String tweetId = request.params(":tweetId");
        try {
            response.body(tweetController.getRetweetsByRetweetId(tweetId));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }

    public Object handleGetQuotes(Request request, Response response) {
        String tweetId = request.params(":tweetId");
        try {
            response.body(tweetController.getQuotesByQuoteTweetId(tweetId));
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return response.body();
        }
        return response.body();
    }
}