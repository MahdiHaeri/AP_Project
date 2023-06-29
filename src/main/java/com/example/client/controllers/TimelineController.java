package com.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private VBox tweetsVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int n = 10;
        for (int i = 0; i < n; i++) {
            try {
//                TweetController tweetController = new TweetController();
//                tweetController.setTweetText("Tweet " + i);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/tweet.fxml"));
                Node tweet = fxmlLoader.load();
                tweetsVbox.getChildren().add(tweet);
//                tweetsVbox.getChildren().add(tweetController.getTweet());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
