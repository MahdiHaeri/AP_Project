package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
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

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void fillTimeline(String username) {
        HttpResponse tweetResponse;
        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetsJson = null;
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
            tweetController.setMainController(mainController);
            tweetController.fillTweet(tweetJson.get("tweetId").asText());
            tweetsVbox.getChildren().add(tweetRoot);
        }
    }

    public Node getTimelinePane() {
        return tweetsVbox;
    }
}
