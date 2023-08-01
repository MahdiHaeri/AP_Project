package com.example.client.controllers;

import com.example.client.http.HttpController;
import com.example.client.http.HttpMethod;
import com.example.client.http.HttpResponse;
import com.example.client.util.JWTController;
import com.example.server.models.Bio;
import com.example.server.models.User;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
        Bio bio = new Bio();
        bio.setUserId(JWTController.getSubjectFromJwt(JWTController.getJwtKey()));
        bio.setBiography(bioTextArea.getText());
        bio.setLocation(locationTf.getText());
        bio.setWebsite(websiteTf.getText());

        ObjectMapper objectMapper = new ObjectMapper();
        String bioJson = null;
        try {
            bioJson = objectMapper.writeValueAsString(bio);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse response;
        try {
            response = HttpController.sendRequest("http://localhost:8080/api/users/" + bio.getUserId() + "/bio", HttpMethod.POST, bioJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!(response.getStatusCode() >= 200 && response.getStatusCode() < 300)) throw new RuntimeException("Error saving bio");

        // -------------------------- save user info ---------------------------------

        HttpResponse userResponse = null;
        try {
            userResponse = HttpController.sendRequest("http://localhost:8080/api/users/" +  JWTController.getSubjectFromJwt(JWTController.getJwtKey()), HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!(userResponse.getStatusCode() >= 200 && userResponse.getStatusCode() < 300)) throw new RuntimeException("Error getting user data");

        JsonNode userJson = null;
        try {
            userJson = objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        User user = new User();
        user.setId(userJson.get("id").asText());
        user.setFirstName(firstNameTf.getText());
        user.setLastName(lastNameTf.getText());
        user.setEmail(userJson.get("email").asText());
        user.setPhoneNumber(userJson.get("phoneNumber").asText());
        user.setPassword(userJson.get("password").asText());
        user.setCountry(userJson.get("country").asText());
        user.setCreatedAt(new Date(userJson.get("createdAt").asLong()));
        user.setBirthday(new Date(userJson.get("birthday").asLong()));
//        user.setBirthday(birthdayDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

        ObjectMapper objectMapper2 = new ObjectMapper();
        String userJson2 = null;
        try {
            userJson2 = objectMapper2.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse userResponse2 = null;
        try {
            userResponse2 = HttpController.sendRequest("http://localhost:8080/api/users/" + user.getId(), HttpMethod.PUT, userJson2, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image profileImage = avatar.getImage();
        Image headerImage = (Image) headerImagePane.getBackground().getImages().get(0).getImage();

        byte[] profileImageBytes = null;
        byte[] headerImageBytes = null;

        try {
            profileImageBytes = convertImageToByteArray(profileImage);
            headerImageBytes = convertImageToByteArray(headerImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            URL url = new URL("http://localhost:8080/api/users/" + user.getId() + "/profile-image");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "image/png");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(profileImageBytes);
            outputStream.close();
            int responseCode = conn.getResponseCode();
            if (!(responseCode >= 200 && responseCode < 300)) throw new RuntimeException("Error saving profile image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            URL url = new URL("http://localhost:8080/api/users/" + user.getId() + "/header-image");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "image/png");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(headerImageBytes);
            outputStream.close();
            int responseCode = conn.getResponseCode();
            if (!(responseCode >= 200 && responseCode < 300)) throw new RuntimeException("Error saving header image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // close stage
        saveBtn.getScene().getWindow().hide();
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

        // set avatar
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

        // set header image
        try {
            // Get the current timestamp as the cache buster
            long cacheBuster = System.currentTimeMillis();
            URL url3 = new URL("http://localhost:8080/api/users/" + JWTController.getSubjectFromJwt(JWTController.getJwtKey()) + "/header-image" + "?" + cacheBuster);
            headerImagePane.setStyle("-fx-background-image: url('" + url3 + "'); -fx-background-repeat: no-repeat; -fx-background-size: cover; -fx-background-position: center center;");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public byte[] convertImageToByteArray(Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
