package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.TimestampController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private VBox tweetsVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HttpResponse tweetResponse;
        HttpResponse userResponse;
        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetsJson = null;
        JsonNode usersJson = null;
        try {
            tweetsJson = objectMapper.readTree(tweetResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Iterate over the tweets
        for (JsonNode tweetJson : tweetsJson) {
            // Create an FXML loader and load the tweet FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/tweet.fxml"));
            Parent tweetRoot = null;
            try {
                tweetRoot = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Access the controller of the tweet FXML
            TweetController tweetController = fxmlLoader.getController();

            // Set the tweet information on the controller

            try {
                userResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText(), HttpMethod.GET, null, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                usersJson = objectMapper.readTree(userResponse.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            long createdAt = tweetJson.get("createdAt").asLong();
            tweetController.setTimestapLbl(TimestampController.formatTimestamp(createdAt));
            tweetController.setTextMessageLbl(tweetJson.get("text").asText());
            tweetController.setOwnerUsernameLbl("@" + tweetJson.get("ownerId").asText());
            tweetController.setReplyBtn(tweetJson.get("replyCount").asText());
            tweetController.setRetweetBtn(tweetJson.get("retweetCount").asText());
            tweetController.setLikeBtn(tweetJson.get("likeCount").asText());
            tweetController.setOwnerNameLbl(usersJson.get("firstName").asText() + " " + usersJson.get("lastName").asText());
            // ... set other information on the controller

            try {

                URL url2 = new URL("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText() + "/profile-image");
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                InputStream inputStream = conn.getInputStream();
                Image image = new Image(inputStream);
                tweetController.setAvatarView(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Add the tweet to the tweet box
            tweetsVbox.getChildren().add(tweetRoot);
        }
    }

    public Node getTimelinePane() {
        return tweetsVbox;
    }
}
