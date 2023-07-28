package com.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    public BorderPane rootBp;

    @FXML
    private Button tweetBtn;

    @FXML
    void onBookmarksBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/bookmarks.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/messages.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onMoreBtnAction(ActionEvent event) {

    }

    @FXML
    void onNotificatinosBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/notifications.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onProfileBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/profile.fxml"));
            ProfileController profileController = fxmlLoader.getController();
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());


            Parent profileRoot = fxmlLoader.load();
            rootBp.setCenter(profileRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/createTweet.fxml"));
            Stage stage = new Stage();
            Parent createTweetRoot = fxmlLoader.load();
            stage.setTitle("Create Tweet");
            stage.setScene(new Scene(createTweetRoot));
            stage.show();
            // when stage closed, refresh the timeline to show the new tweet

            stage.setOnHidden(e -> {
                initialize(null, null);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/timeline.fxml"));
            Parent timelineRoot = fxmlLoader.load();
            rootBp.setCenter(timelineRoot);

            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/example/client/trends.fxml"));
            Parent trendsRoot = fxmlLoader2.load();
            rootBp.setRight(trendsRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
