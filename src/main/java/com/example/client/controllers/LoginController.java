package com.example.client.controllers;

import com.example.server.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameTf;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Label statusMessageLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordTf;

    @FXML
    private Hyperlink signupLink;

    @FXML
    void onForgotPasswordAction(ActionEvent event) {

    }

    @FXML
    void onLoginBtnAction(ActionEvent event) {
        // Make a POST request to the server
        HttpURLConnection connection = null;
        try {
            // Create a body for the request with username and password
            String username = usernameTf.getText();
            String password = passwordTf.getText();
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(Map.of("userId", username, "password", password));

            URL apiUrl = new URL("http://localhost:8080/api/login");
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json"); // Set the content type to JSON

            // Write the request body to the connection's output stream
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = reader.readLine();
                    JWTController.setJwtKey(response);
                    System.out.println(response);

                    try {
                        // Load the FXML file for the SignUp page
                        Parent signupPage = FXMLLoader.load(getClass().getResource("/com/example/client/main.fxml"));

                        // Create a new scene using the SignUp page
                        Scene signupScene = new Scene(signupPage);

                        // Get the current stage (window) and set the new scene
                        Stage currentStage = (Stage) signupLink.getScene().getWindow();
                        currentStage.setScene(signupScene);
                        // currentStage.setFullScreen(true);
                        currentStage.setMaximized(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Handle the error case when the server returns a non-OK response
                System.out.println("Failed to retrieve tweets. Response code: " + responseCode);
                statusMessageLbl.setVisible(true);
                statusMessageLbl.setText("Username or password is not valid");
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


    @FXML
    void onSignupLinkAction(ActionEvent event) {
        try {
            // Load the FXML file for the SignUp page
            Parent signupPage = FXMLLoader.load(getClass().getResource("/com/example/client/signup.fxml"));

            // Create a new scene using the SignUp page
            Scene signupScene = new Scene(signupPage);

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) signupLink.getScene().getWindow();
            currentStage.setScene(signupScene);
//            currentStage.setFullScreen(true);
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
