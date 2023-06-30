package com.example.server.HttpHandlers;

import com.example.server.controllers.TweetController;
import com.example.server.utils.ExtractUserAuth;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import org.json.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

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

        InputStream requestBody = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        requestBody.close();


        // ip:port/tweets/tweet-type :(
        switch (method) {
            case "GET":
                try {
                    response = tweetController.getTweets();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "POST":
//                String user_id = ExtractUserAuth.extract(exchange);
//                if (user_id == null) {
//                    response = "token not valid!";
//                    break;
//                }

                JSONObject jsonObject = new JSONObject(body.toString());

//                 Process the user creation based on the request body
                try {
                    tweetController.createTweet(jsonObject.getString("writerId"), jsonObject.getString("ownerId"), jsonObject.getString("text"), jsonObject.getString("quoteTweetId"), toStringArray(jsonObject.getJSONArray("mediaPaths")), jsonObject.getInt("likes"), jsonObject.getInt("retweets"), jsonObject.getInt("replies"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                response = "Tweet successfully tweeted!";
                break;
//            case "PUT":
//                try {
//                    tweetController.updateTweet(jsonObject.getString("writerId"), jsonObject.getString("ownerId"), jsonObject.getString("text"), jsonObject.getString("quoteTweetId"), toStringArray(jsonObject.getJSONArray("mediaPaths")), jsonObject.getInt("likes"), jsonObject.getInt("retweets"), jsonObject.getInt("replies"));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
            case "DELETE":
                try {
                    tweetController.deleteTweets();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "Tweet successfully deleted!";
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

    public static ArrayList<String> toStringArray(JSONArray jsonArray) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String element = jsonArray.getString(i);
                arrayList.add(element);
            } catch (JSONException e) {
                // Handle any JSON exception if necessary
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}