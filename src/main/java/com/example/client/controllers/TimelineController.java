package com.example.client.controllers;

import com.example.server.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private VBox tweetsVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make a GET request to the server
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL("http://localhost:8080/api/tweets");
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode tweetsJson = objectMapper.readTree(response);

                // Iterate over the tweets
                for (JsonNode tweetJson : tweetsJson) {
                    // Create an FXML loader and load the tweet FXML file
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/tweet.fxml"));
                    Parent tweetRoot = fxmlLoader.load();

                    // Access the controller of the tweet FXML
                    TweetController tweetController = fxmlLoader.getController();

                    // Set the tweet information on the controller
                    tweetController.setTweetText(tweetJson.get("text").asText());
                    tweetController.setOwnerNameLbl(tweetJson.get("ownerId").asText());
                    tweetController.setReplyBtn(tweetJson.get("replyCount").asText());
                    tweetController.setRetweetBtn(tweetJson.get("retweetCount").asText());
                    tweetController.setLikeBtn(tweetJson.get("likeCount").asText());
                    // ... set other information on the controller

                    // Add the tweet to the tweet box
                    tweetsVbox.getChildren().add(tweetRoot);
                }
            } else {
                // Handle the error case when the server returns a non-OK response
                System.out.println("Failed to retrieve tweets. Response code: " + responseCode);
            }
        } catch (IOException e) {
            // Handle any IO exception that occurs during the request
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public Node getTimelinePane() {
        return tweetsVbox;
    }
}
