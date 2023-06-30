package com.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button bookmarksBtn;

    @FXML
    private Button exploreBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button listsBtn;

    @FXML
    private Button messagesBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Button notificationBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private BorderPane rootBp;

    @FXML
    private Button tweetBtn;

    @FXML
    void onBookmarksBtnAction(ActionEvent event) {

    }

    @FXML
    void onExploreBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHomeBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onListsBtnAction(ActionEvent event) {

    }

    @FXML
    void onMessagesBtnAction(ActionEvent event) {

    }

    @FXML
    void onMoreBtnAction(ActionEvent event) {

    }

    @FXML
    void onNotificatinosBtnAction(ActionEvent event) {

    }

    @FXML
    void onProfileBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/profile.fxml"));
            Parent profileRoot = fxmlLoader.load();
            rootBp.setCenter(profileRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
