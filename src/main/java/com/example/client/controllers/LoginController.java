package com.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField PhoneEmailUsernameTf;

    @FXML
    private Hyperlink forgotPasswordLink;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
