package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpHeaders;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label BioLbl;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Button followBtn;

    @FXML
    private Label fullNameLbl;

    @FXML
    private Label usernameLbl;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public String getBioLbl() {
        return BioLbl.getText();
    }

    public void setBioLbl(String text) {
        BioLbl.setText(text);
    }

    public Image getAvatar() {
        return avatar.getImage();
    }

    public void setAvatar(Image image) {
        this.avatar.setImage(image);
    }

    public String getFollowBtn() {
        return followBtn.getText();
    }

    public void setFollowBtn(String text) {
        this.followBtn.setText(text);
    }

    public String getFullNameLbl() {
        return fullNameLbl.getText();
    }

    public void setFullNameLbl(String text) {
        this.fullNameLbl.setText(text);
    }

    public String getUsernameLbl() {
        return usernameLbl.getText().substring(1);
    }

    public void setUsernameLbl(String text) {
        this.usernameLbl.setText(text);
    }

    @FXML
    void onRootMouseClicked(MouseEvent event) {
        String username = getUsernameLbl();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/profile.fxml"));
            Parent profileRoot = fxmlLoader.load();
            ProfileController profileController = fxmlLoader.getController();
            profileController.fillProfile(username);
            profileController.setMainController(mainController);
            mainController.setCenter(profileRoot);
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

    public void fillUser(String username) {
        if (isFollowing(username)) {
            followBtn.setText("Unfollow");
        } else {
            followBtn.setText("Follow");
        }

        setUsernameLbl("@" + username);

        HttpResponse followerInfoResponse;
        HttpResponse followerBioResponse;
        try {
            followerInfoResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + username, HttpMethod.GET, null, null);
            followerBioResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/bio", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode followerInfoJson = null;
        JsonNode followerBioJson = null;
        try {
            followerInfoJson = objectMapper.readTree(followerInfoResponse.getBody());
            followerBioJson = objectMapper.readTree(followerBioResponse.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            URL url2 = new URL("http://localhost:8080/api/users/" + username + "/profile-image");
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream inputStream = conn.getInputStream();
            Image image = new Image(inputStream);
            setAvatar(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setFullNameLbl(followerInfoJson.get("firstName").asText() + " " + followerInfoJson.get("lastName").asText());
        setBioLbl(followerBioJson.get("biography").asText());
    }
}
