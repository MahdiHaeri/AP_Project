package com.example.client.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label DateLbl;

    @FXML
    private Label bioLbl;

    @FXML
    private Button blockBtn;

    @FXML
    private Button editProfileBtn;

    @FXML
    private Label firstNameLbl;

    @FXML
    private Button followBtn;

    @FXML
    private Label followerLbl;

    @FXML
    private Label followerCountLbl;

    @FXML
    private Label followingCountLbl;

    @FXML
    private Label followingLbl;

    @FXML
    private Label joinedLbl;

    @FXML
    private Label lastNameLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label usernameLbl;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onFolllowBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make a GET request to the server
        HttpURLConnection connection = null;
        try {
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());

            URL apiUrl = new URL("http://localhost:8080/users/" + username);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode userJson = objectMapper.readTree(response);

                // Set the labels
                usernameLbl.setText(userJson.get("id").asText());
                firstNameLbl.setText(userJson.get("firstName").asText());
                lastNameLbl.setText(userJson.get("lastName").asText());
//                bioLbl.setText(userJson.get("bio").asText());
                locationLbl.setText(userJson.get("country").asText());

                Date date = new Date(userJson.get("createdAt").asLong());
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                String dateString = sdf.format(date);
                DateLbl.setText(dateString);

//                followercountLbl.setText(userJson.get("followerCount").asText());
//                followingCountLbl.setText(userJson.get("followingCount").asText());


            } else {
                // Handle the error case when the server returns a non-OK response
                System.out.println("Failed to retrieve tweets. Response code: " + responseCode);
            }
        } catch (IOException e) {
            // Handle any IO exception that occurs during the request
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


        try {
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());

            URL apiUrl = new URL("http://localhost:8080/users/" + username + "/follower");
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode followers = objectMapper.readTree(response);

                int followerCount = 0;
                for (JsonNode followerJson: followers) {
                    followerCount++;
                }

                followerCountLbl.setText(Integer.toString(followerCount));

//                followingCountLbl.setText(userJson.get("followingCount").asText());

            } else {
                // Handle the error case when the server returns a non-OK response
                System.out.println("Failed to retrieve tweets. Response code: " + responseCode);
            }
        } catch (IOException e) {
            // Handle any IO exception that occurs during the request
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        try {
            String username = JWTController.getSubjectFromJwt(JWTController.getJwtKey());

            URL apiUrl = new URL("http://localhost:8080/users/" + username + "/following");
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode followings = objectMapper.readTree(response);

                int followingCount = 0;
                for (JsonNode followerJson: followings) {
                    followingCount++;
                }

                followingCountLbl.setText(Integer.toString(followingCount));

            } else {
                // Handle the error case when the server returns a non-OK response
                System.out.println("Failed to retrieve tweets. Response code: " + responseCode);
            }
        } catch (IOException e) {
            // Handle any IO exception that occurs during the request
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
