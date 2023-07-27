package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpHeaders;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class CreateTweetController {

    @FXML
    private Button tweetBtn;

    @FXML
    private TextArea textArea;

    @FXML
    private FontAwesomeIconView videoIcon;

    @FXML
    private FontAwesomeIconView imageIcon;

    @FXML
    void onImageIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)imageIcon.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + JWTController.getJwtKey());

        if (textArea.getText().isEmpty()) {
            return;
        }

        String body = "{\"text\": \"" + textArea.getText() + "\"}";
        try {
            HttpResponse response = HttpController.sendRequest("http://localhost:8080/api/tweets", HttpMethod.POST, body, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textArea.setText("");

        textArea.getScene().getWindow().hide();

    }

    @FXML
    void onVideoIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)imageIcon.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

}
