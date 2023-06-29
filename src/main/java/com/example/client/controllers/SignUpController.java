package com.example.client.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.example.server.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZoneId;
import java.util.Date;


public class SignUpController {

    @FXML
    private DatePicker birthdayDp;

    @FXML
    private PasswordField confirmPasswordTf;

    @FXML
    private ComboBox<?> countryCmb;

    @FXML
    private TextField emailOrPhoneNumberTf;

    @FXML
    private TextField firstNameTf;

    @FXML
    private TextField lastNameTf;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private PasswordField passwordTf;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTf;

    @FXML
    void onLoginLinkActino(ActionEvent event) {
        System.out.println("OnLoginLinkAction");
    }

    @FXML
    void onRigesterBtnAction(ActionEvent event) {
        try {
            User user = new User();
            user.setId(usernameTf.getText());
            user.setFirstName(firstNameTf.getText());
            user.setLastName(lastNameTf.getText());
            user.setEmail(emailOrPhoneNumberTf.getText());
            user.setPhoneNumber(emailOrPhoneNumberTf.getText());
            user.setPassword(passwordTf.getText());
//            user.setCountry((String) countryCmb.getValue());
            user.setCountry("Iran");
            user.setBirthday(Date.from(birthdayDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            ObjectMapper objectMapper = new ObjectMapper();
            String bodyRequest;
            bodyRequest = objectMapper.writeValueAsString(user);

            URL url;
            url = new URL("http://127.0.0.1:8080/users");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bodyRequest.getBytes());
            outputStream.flush();
            outputStream.close();

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Request was successful
                // Handle the response if needed
            } else {
                // Request failed
                // Handle the error response
            }
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
