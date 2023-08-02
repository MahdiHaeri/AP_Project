package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
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
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
        String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
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
            userController.setUsernameLbl("@" + followerJson.get("follower").asText());
//            userController.setFollowBtn("Unfollow");
            HttpResponse followerInfoResponse;
            HttpResponse followerBioResponse;
            try {
                followerInfoResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + followerJson.get("follower").asText(), HttpMethod.GET, null, null);
                followerBioResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + followerJson.get("follower").asText() + "/bio", HttpMethod.GET, null, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                URL url2 = new URL("http://localhost:8080/api/users/" + followerJson.get("follower").asText() + "/profile-image");
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                InputStream inputStream = conn.getInputStream();
                Image image = new Image(inputStream);
                userController.setAvatar(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JsonNode followerInfoJson = null;
            JsonNode followerBioJson = null;
            try {
                followerInfoJson = objectMapper.readTree(followerInfoResponse.getBody());
                followerBioJson = objectMapper.readTree(followerBioResponse.getBody());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            userController.setFullNameLbl(followerInfoJson.get("firstName").asText() + " " + followerInfoJson.get("lastName").asText());
            userController.setBioLbl(followerBioJson.get("biography").asText());
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
            userController.setUsernameLbl("@" + followingJsonNode.get("followed").asText());
//            userController.setFollowBtn("Unfollow");
            HttpResponse followingInfoResponse;
            HttpResponse followingBioResponse;
            try {
                followingInfoResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + followingJsonNode.get("followed").asText(), HttpMethod.GET, null, null);
                followingBioResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + followingJsonNode.get("followed").asText() + "/bio", HttpMethod.GET, null, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                URL url2 = new URL("http://localhost:8080/api/users/" + followingJsonNode.get("followed").asText() + "/profile-image");
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                InputStream inputStream = conn.getInputStream();
                Image image = new Image(inputStream);
                userController.setAvatar(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JsonNode followingInfoJson = null;
            JsonNode followingBioJson = null;
            try {
                followingInfoJson = objectMapper.readTree(followingInfoResponse.getBody());
                followingBioJson = objectMapper.readTree(followingBioResponse.getBody());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            userController.setFullNameLbl(followingInfoJson.get("firstName").asText() + " " + followingInfoJson.get("lastName").asText());
            userController.setBioLbl(followingBioJson.get("biography").asText());
            followingVbox.getChildren().add(followingRoot);
        }
    }
}
