package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private GNAvatarView avatar;

    @FXML
    private TextArea bioTextArea;

    @FXML
    private DatePicker birthdayDp;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField firstNameTf;

    @FXML
    private AnchorPane headerImagePane;

    @FXML
    private TextField lastNameTf;

    @FXML
    private TextField locationTf;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField websiteTf;
    @FXML
    void onAvatarClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(avatar.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            avatar.setImage(image);
        }
    }

    @FXML
    void onCancelBtnAction(ActionEvent event) {
        // close stage
        cancelBtn.getScene().getWindow().hide();
    }

    @FXML
    void onHeaderPaneClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(headerImagePane.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            headerImagePane.setStyle("-fx-background-image: url('" + selectedFile.toURI().toString() + "'); -fx-background-size: cover;");
        }
    }

    @FXML
    void onSaveBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());
        HttpResponse bioResponse;
        HttpResponse userResponse;
        try {
            bioResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + username + "/bio", HttpMethod.GET, null, null);
            userResponse = HttpController.sendRequest("http://localhost:8080/api/users/" + username, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode bioJson = null;
        JsonNode userJson = null;

        if (bioResponse.getStatusCode() != 200 || userResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        if (!bioResponse.getBody().equals("{}")) {
            try {
                bioJson = objectMapper.readTree(bioResponse.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            bioTextArea.setText(bioJson.get("biography").asText());
            locationTf.setText(bioJson.get("location").asText());
            websiteTf.setText(bioJson.get("website").asText());
        }

        try {
            userJson = objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        firstNameTf.setText(userJson.get("firstName").asText());
        lastNameTf.setText(userJson.get("lastName").asText());

        java.util.Date birthdayDate = new java.util.Date(userJson.get("birthday").asLong());
        // Step 3: Convert the java.util.Date object to a java.time.LocalDate object
        LocalDate birthdayLocalDate = Instant.ofEpochMilli(birthdayDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Step 4: Set the value of the JavaFX DatePicker to the converted LocalDate
        birthdayDp.setValue(birthdayLocalDate);

//        avatar.setImage(new Image(userJson.get("avatarUrl").asText()));
//        headerImagePane.setStyle("-fx-background-image: url('" + userJson.get("headerUrl").asText() + "'); -fx-background-size: cover;");

    }
}
