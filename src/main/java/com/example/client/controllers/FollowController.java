package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FollowController implements Initializable {

    @FXML
    private Button ConnectBtn;

    @FXML
    private VBox FollowersYouKnowVbox;

    @FXML
    private VBox FollowersVbox;

    @FXML
    private VBox followingVbox;

    @FXML
    private Button backBtn;

    @FXML
    private Tab followersTab;

    @FXML
    private Tab followingTab;

    @FXML
    private Tab followingYouKnowTab;

    @FXML
    private TabPane tabPane;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public Tab getFollowersTab() {
        return followersTab;
    }

    public void setFollowersTab(Tab followersTab) {
        this.followersTab = followersTab;
    }

    public Tab getFollowingTab() {
        return followingTab;
    }

    public void setFollowingTab(Tab followingTab) {
        this.followingTab = followingTab;
    }

    public Tab getFollowingYouKnowTab() {
        return followingYouKnowTab;
    }

    public void setFollowingYouKnowTab(Tab followingYouKnowTab) {
        this.followingYouKnowTab = followingYouKnowTab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setSelectTab(Tab tab) {
        tabPane.getSelectionModel().select(tab);
    }

    public String getConnectBtn() {
        return ConnectBtn.getText();
    }

    public void setConnectBtn(String text) {
        ConnectBtn.setText(text);
    }

//    public VBox getFollowersVbox() {
//        return FollowersVbox;
//    }

//    public void setFollowersVbox(VBox followersVbox) {
//        FollowersVbox = followersVbox;
//    }

//    public VBox getFollowersYouKnowVbox() {
//        return FollowersYouKnowVbox;
//    }

//    public void setFollowersYouKnowVbox(VBox followersYouKnowVbox) {
//        FollowersYouKnowVbox = followersYouKnowVbox;
//    }

    public String getBackBtn() {
        return backBtn.getText();
    }

    public void setBackBtn(String text) {
        this.backBtn.setText(text);
    }

    @FXML
    void onBackBtnAction(ActionEvent event) {

    }

    @FXML
    void onConnectBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void fillFollow(String username) {
        HttpResponse followers;
        HttpResponse following;
//        HttpResponse followersYouKnow;

        try {
            followers = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/followers", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            following = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/following", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode followersJson = null;
        JsonNode followingJson = null;
//        JsonNode followersYouKnowJson = null;

        try {
            followersJson = objectMapper.readTree(followers.getBody());
            followingJson = objectMapper.readTree(following.getBody());
//            followersYouKnowJson = objectMapper.readTree(followersYouKnow.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (JsonNode followerJson : followersJson) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/user.fxml"));
            Parent followerRoot = null;
            try {
                followerRoot = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UserController userController = fxmlLoader.getController();
            userController.setMainController(mainController);
            userController.fillUser(followerJson.get("follower").asText());
            FollowersVbox.getChildren().add(followerRoot);
        }

        for (JsonNode followingJsonNode : followingJson) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/user.fxml"));
            Parent followingRoot = null;
            try {
                followingRoot = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            UserController userController = fxmlLoader.getController();
            userController.setMainController(mainController);
            userController.fillUser(followingJsonNode.get("followed").asText());
            followingVbox.getChildren().add(followingRoot);
        }
    }
}
