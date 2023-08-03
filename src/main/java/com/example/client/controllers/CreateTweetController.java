package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpHeaders;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTweetController implements Initializable {

    @FXML
    private Button tweetBtn;

    @FXML
    private TextArea textArea;

    @FXML
    private FontAwesomeIconView videoIcon;

    @FXML
    private FontAwesomeIconView imageIcon;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private HBox imageContainerHbox;


    @FXML
    void onImageIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(250);
            imageView.setFitHeight(180);
            // radius of the image
            // Create a Rectangle with rounded corners
            Rectangle clipRectangle = new Rectangle(250, 180);
            clipRectangle.setArcWidth(20);
            clipRectangle.setArcHeight(20);
            imageView.setClip(clipRectangle);


            imageContainerHbox.getChildren().add(imageView);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            URL url2 = new URL("http://localhost:8080/api/users/" + JWTController.getSubjectFromJwt(JWTController.getJwtKey()) + "/profile-image");
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
    }
}
