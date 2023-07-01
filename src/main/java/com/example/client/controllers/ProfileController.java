package com.example.client.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    private Label firstNameLbl;

    @FXML
    private Label followerLbl;

    @FXML
    private Label followercountLbl;

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
    }
}
