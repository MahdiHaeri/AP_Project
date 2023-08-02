package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import com.example.client.util.TimestampController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.http.*;

public class ProfileController implements Initializable {
    @FXML
    private Label DateLbl;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Label bioLbl;

    @FXML
    private Button blockBtn;

    @FXML
    private Button editProfileBtn;
    @FXML
    private Label fullNameLbl;

    @FXML
    private Button followBtn;

    @FXML
    private Label followerCountLbl;

    @FXML
    private Label followerLbl;

    @FXML
    private Label followingCountLbl;

    @FXML
    private Label followingLbl;

    @FXML
    private AnchorPane headerImagePane;

    @FXML
    private Label joinedLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label usernameLbl;


    @FXML
    private Button followersBtn;

    @FXML
    private Button followingBtn;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }


    public String getUsernameLbl() {
        return usernameLbl.getText().substring(1);
    }

    public void setUsernameLbl(String text) {
        this.usernameLbl.setText("@" + text);
    }

    @FXML
    void onBlockBtnAction(ActionEvent event) {

    }

    @FXML
    void onEditProfileBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/editProfile.fxml"));
            Parent profileRoot = fxmlLoader.load();
            // set to the center of the main border pane

            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(new Scene(profileRoot));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();

            // update profile page when edit profile is closed
            stage.setOnHidden(e -> {
                initialize(null, null);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onFollowBtnAction(ActionEvent event) {
        if (followBtn.getText().equals("Follow")) {
            follow();
        } else {
            unfollow();
        }
    }

    private void unfollow() {
        HttpResponse response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + getUsernameLbl() + "/unfollow", HttpMethod.POST, "", headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            followBtn.setText("Follow");
        }
    }

    private void follow() {
        HttpResponse response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + getUsernameLbl() + "/follow", HttpMethod.POST, "", headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            followBtn.setText("Unfollow");
        }
    }

    @FXML
    void onFollowersBtnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/follow.fxml"));
        try {
            String username = getUsernameLbl();
            Parent followRoot = fxmlLoader.load();
            FollowController followController = fxmlLoader.getController();
            followController.setSelectTab(followController.getFollowersTab());
            followController.setMainController(mainController);
            followController.fillFollow(username);
            mainController.getRootBp().setCenter(followRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onFollowingBtnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/follow.fxml"));
        try {
            String username = getUsernameLbl();
            Parent followRoot = fxmlLoader.load();
            FollowController followController = fxmlLoader.getController();
            followController.setSelectTab(followController.getFollowingTab());
            followController.setMainController(mainController);
            followController.fillFollow(username);
            mainController.getRootBp().setCenter(followRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFollowing(String username) {
        String usernameFromJwt = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
        HttpResponse response = null;
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + usernameFromJwt + "/following", HttpMethod.GET, "", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            for (JsonNode node : jsonNode) {
                if (node.get("followed").asText().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void fillProfile(String username) {

        if (username.equals(JWTController.getSubjectFromJwt(JWTController.getJwtKey()))) {
            editProfileBtn.setVisible(true);
            followBtn.setVisible(false);
            blockBtn.setVisible(false);
        } else {
            editProfileBtn.setVisible(false);
            followBtn.setVisible(true);
            blockBtn.setVisible(true);
            if (isFollowing(username)) {
                followBtn.setText("Unfollow");
            } else {
                followBtn.setText("Follow");
            }
        }

        HttpResponse response = null;
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + username, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode userJson = null;
        try {
            userJson = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Set the labels
        usernameLbl.setText("@" + userJson.get("id").asText());
        fullNameLbl.setText(userJson.get("firstName").asText() + " " + userJson.get("lastName").asText());
        DateLbl.setText(TimestampController.formatTimestamp(userJson.get("createdAt").asLong()));

        // set Followers and Following Count
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/followers", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        JsonNode followers = null;
        try {
            followers = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int followerCount = 0;
        for (JsonNode followerJson: followers) {
            followerCount++;
        }

        followerCountLbl.setText(Integer.toString(followerCount));

        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/following", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        JsonNode followings = null;
        try {
            followings = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        int followingCount = 0;
        for (JsonNode followerJson: followings) {
            followingCount++;
        }
        followingCountLbl.setText(Integer.toString(followingCount));

        // set bio
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/bio", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!response.getBody().equals("{}")) {
            JsonNode bioJson = null;
            try {
                bioJson = objectMapper.readTree(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            bioLbl.setText(bioJson.get("biography").asText());
            locationLbl.setText(bioJson.get("location").asText());
        }

        // Set the avatar and header image
        try {
            URL url2 = new URL("http://localhost:8080/api/users/" + username + "/profile-image");
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream inputStream = conn.getInputStream();
            Image image = new Image(inputStream);
            avatar.setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Get the current timestamp as the cache buster
            long cacheBuster = System.currentTimeMillis();
            URL url3 = new URL("http://localhost:8080/api/users/" + username + "/header-image" + "?" + cacheBuster);
            headerImagePane.setStyle("-fx-background-image: url('" + url3 + "'); -fx-background-repeat: no-repeat; -fx-background-size: cover; -fx-background-position: center center;");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
