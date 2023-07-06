package com.example.client.controllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Button cancelBtn;

    @FXML
    private AnchorPane headerImagePane;

    @FXML
    private Button saveBtn;

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

    }
}
