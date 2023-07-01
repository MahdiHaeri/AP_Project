package com.example.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.example.server.models.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class SignUpController implements Initializable {

    @FXML
    private DatePicker birthdayDp;

    @FXML
    private PasswordField confirmPasswordTf;

    @FXML
    private ComboBox<String> countryCmb;

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
        try {
            // Load the FXML file for the SignUp page
            Parent loginPage = FXMLLoader.load(getClass().getResource("/com/example/client/login.fxml"));

            // Create a new scene using the SignUp page
            Scene loginScene = new Scene(loginPage);

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) loginLink.getScene().getWindow();
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            user.setCountry((String) countryCmb.getValue());
            user.setBirthday(Date.from(birthdayDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            usernameTf.setText("");
            firstNameTf.setText("");
            lastNameTf.setText("");
            emailOrPhoneNumberTf.setText("");
            passwordTf.setText("");
            confirmPasswordTf.setText("");
            countryCmb.setValue("");
            birthdayDp.setValue(null);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCmb.getItems().addAll("Iran", "USA", "Canada", "Germany", "France", "Italy", "Spain", "Russia", "China", "Japan", "South Korea", "Australia", "Brazil", "Mexico", "Argentina", "Chile", "Peru", "Colombia", "Venezuela", "Ecuador", "Bolivia", "Paraguay", "Uruguay", "Panama", "Costa Rica", "Nicaragua", "Honduras", "El Salvador", "Guatemala", "Cuba", "Dominican Republic", "Puerto Rico", "Jamaica", "Haiti", "Bahamas", "Trinidad and Tobago", "Barbados", "Saint Lucia", "Saint Vincent and the Grenadines", "Grenada", "Antigua and Barbuda", "Dominica", "Saint Kitts and Nevis", "Belize", "Guyana", "Suriname", "Aruba", "Curacao", "Saint Martin", "Sint Maarten", "Bermuda", "Cayman Islands", "British Virgin Islands", "Turks and Caicos Islands", "US Virgin Islands", "Anguilla", "Montserrat", "Martinique", "Guadeloupe", "Saint Barthelemy", "Puerto Rico", "Greenland", "Faroe Islands", "Iceland", "Norway", "Sweden", "Finland", "Denmark");
    }

    public class EmailValidator {
        private static final String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        public static boolean isValidEmail(String email) {
            return pattern.matcher(email).matches();

        }
    }
}
