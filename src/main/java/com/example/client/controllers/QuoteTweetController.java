package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.ThemeManager;
import com.example.client.util.TimestampController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class QuoteTweetController implements Initializable {
    @FXML
    private BorderPane rootBp;
    private String tweetId;

    @FXML
    private GNAvatarView avatarView;

    @FXML
    private VBox contentContainer;

    @FXML
    private Label ownerNameLbl;

    @FXML
    private Label ownerUsernameLbl;

    @FXML
    private Text textMessageText;

    @FXML
    private Label timestampLbl;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }
    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public Image getAvatarView() {
        return avatarView.getImage();
    }

    public void setAvatarView(Image image) {
        this.avatarView.setImage(image);
    }

    public VBox getContentContainer() {
        return contentContainer;
    }

    public void setContentContainer(VBox contentContainer) {
        this.contentContainer = contentContainer;
    }

    public String getOwnerNameLbl() {
        return ownerNameLbl.getText();
    }

    public void setOwnerNameLbl(String text) {
        this.ownerNameLbl.setText(text);
    }

    public String getOwnerUsernameLbl() {
        return ownerUsernameLbl.getText().substring(1);
    }

    public void setOwnerUsernameLbl(String text) {
        this.ownerUsernameLbl.setText(text);
    }

    public String getTextMessageText() {
        return textMessageText.getText();
    }

    public void setTextMessageText(String text) {
        this.textMessageText.setText(text);
    }

    public String getTimestampLbl() {
        return timestampLbl.getText();
    }

    public void setTimestampLbl(String text) {
        this.timestampLbl.setText(text);
    }


    @FXML
    void onAvatarViewClicked(MouseEvent event) {
        String username = getOwnerUsernameLbl();
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
        ThemeManager.applyTheme(rootBp, url.getPath());
    }

    public void fillQuote(String quoteId) {
        setTweetId(quoteId);
        // Set the tweet information on the controller
        HttpResponse tweetResponse;
        HttpResponse userResponse;
        HttpResponse likeResponse;

        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/tweets/" + quoteId, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetJson;
        JsonNode usersJson;
        try {
            tweetJson = objectMapper.readTree(tweetResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            userResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText(), HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            usersJson = objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        setTweetId(tweetJson.get("tweetId").asText());
        setTextMessageText(tweetJson.get("text").asText());
        setOwnerUsernameLbl("@" + tweetJson.get("ownerId").asText());
        setOwnerNameLbl(usersJson.get("firstName").asText() + " " + usersJson.get("lastName").asText());
        long createdAt = tweetJson.get("createdAt").asLong();
        setTimestampLbl(TimestampController.formatTimestamp(createdAt));
        // ... set other information on the controller

        try {
            URL url2 = new URL("http://localhost:8080/api/users/" + tweetJson.get("ownerId").asText() + "/profile-image");
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream inputStream = conn.getInputStream();
            Image image = new Image(inputStream);
            setAvatarView(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
