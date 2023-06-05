package org.example.server.HttpHandlers;

import org.example.server.controllers.TweetController;
import org.example.server.utils.ExtractUserAuth;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import org.json.*;

import java.io.*;
import java.sql.SQLException;

public class TweetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        TweetController tweetController = null;
        try {
            tweetController = new TweetController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitedPath = path.split("/");
        switch (method) {
            // ip:port/tweets/tweet-type
            case "POST":
                String user_id = ExtractUserAuth.extract(exchange);
                if (user_id == null) {
                    response = "token not valid!";
                    break;
                }
                switch (splitedPath[splitedPath.length - 1]) {
                    case "tweet": {
                        InputStream requestBody = exchange.getRequestBody();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                        StringBuilder body = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            body.append(line);
                        }
                        requestBody.close();

                        // Process the user creation based on the request body
                        String newTweet = body.toString();
                        JSONObject jsonObject = new JSONObject(newTweet);
                        try {
                            tweetController.createTweet(jsonObject.getString("writerId"), jsonObject.getString("ownerId"), jsonObject.getString("text"), null, toStringArray(jsonObject.getJSONArray("mediaPaths")), jsonObject.getInt("likes"), jsonObject.getInt("retweets"), jsonObject.getInt("replies"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        response = "Tweet successfully tweeted!";
                        break;
                    }
                    case "retweet": {
                        InputStream requestBody = exchange.getRequestBody();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                        StringBuilder body = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            body.append(line);
                        }
                        requestBody.close();

                        // Process the user creation based on the request body
                        String newreTweet = body.toString();
                        JSONObject jsonObject = new JSONObject(newreTweet);
                        try {
                            String reTweetId = jsonObject.getString("quoteTweetId");
                            if (tweetController.getTweet(reTweetId) == null) {
                                response = "tweet not found!";
                                break;
                            }
                            tweetController.createTweet(jsonObject.getString("writerId"), jsonObject.getString("ownerId"), null, jsonObject.getString("quoteTweetId"), toStringArray(jsonObject.getJSONArray("mediaPaths")), jsonObject.getInt("likes"), jsonObject.getInt("retweets"), jsonObject.getInt("replies"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        response = "Tweet successfully retweeted!";
                        break;
                    }
                    case "quoteTweet": {
                        InputStream requestBody = exchange.getRequestBody();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                        StringBuilder body = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            body.append(line);
                        }
                        requestBody.close();

                        // Process the user creation based on the request body
                        String newreTweet = body.toString();
                        JSONObject jsonObject = new JSONObject(newreTweet);
                        try {
                            String quotedTweetId = jsonObject.getString("quoteTweetId");
                            if (tweetController.getTweet(quotedTweetId) == null) {
                                response = "tweet not found!";
                                break;
                            }
                            tweetController.createTweet(jsonObject.getString("writerId"), jsonObject.getString("ownerId"), jsonObject.getString("text"), jsonObject.getString("quoteTweetId"), toStringArray(jsonObject.getJSONArray("mediaPaths")), jsonObject.getInt("likes"), jsonObject.getInt("retweets"), jsonObject.getInt("replies"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        response = "Tweet successfully retweeted!";
                        break;
                    }

                    default:
                        response = "unknown-request";
                        break;
                }
                break;
            case "GET":
                String tweetId = splitedPath[splitedPath.length - 1];
                try {
                    response = tweetController.getTweetById(tweetId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                response = "unknown-request";
                break;
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    public static String[] toStringArray(JSONArray array) {
        if(array == null)
            return new String[0];

        String[] arr = new String[array.length()];
        for(int i = 0; i < arr.length; i++)
            arr[i] = array.optString(i);
        return arr;
    }
}