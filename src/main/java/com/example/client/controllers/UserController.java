package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
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
    void onFollowBtnAction(ActionEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void fillUser(String username) {
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
