package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.ThemeManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RetweetHeaderController implements Initializable {

    @FXML
    private BorderPane rootBp;

    @FXML
    private Label retweetedNameLbl;

    public String getRetweetedNameLbl() {
        return retweetedNameLbl.getText();
    }

    public void setRetweetedNameLbl(String text) {
        this.retweetedNameLbl.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ThemeManager.applyTheme(rootBp, url.getPath());
    }

    public void fillRetweetHeader(String username) {
        HttpResponse tweetResponse;
        try {
            tweetResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + username, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tweetsJson = null;
        try {
            tweetsJson = objectMapper.readTree(tweetResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        setRetweetedNameLbl(tweetsJson.get("firstName").asText() + " " + tweetsJson.get("lastName").asText());
    }
}