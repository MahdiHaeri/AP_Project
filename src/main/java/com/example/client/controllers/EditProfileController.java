package com.example.client.controllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;

public class EditProfileController {

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Button cancleBtn;

    @FXML
    private AnchorPane headerImagePane;

    @FXML
    private Button saveBtn;

    @FXML
    void onAvatarClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(avatar.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)avatar.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

    @FXML
    void onCancelBtnAction(ActionEvent event) {

    }

    @FXML
    void onHeaderPaneClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(headerImagePane.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)headerImagePane.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

    @FXML
    void onSaveBtnAction(ActionEvent event) {

    }

}
