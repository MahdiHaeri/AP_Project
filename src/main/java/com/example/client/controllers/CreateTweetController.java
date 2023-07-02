package com.example.client.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class CreateTweetController {

    @FXML
    private FontAwesomeIconView imageIcon;

    @FXML
    private Button tweetBtn;

    @FXML
    private FontAwesomeIconView videoIcon;

    @FXML
    void onImageIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)imageIcon.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

    @FXML
    void onTweetBtnAction(ActionEvent event) {

    }

    @FXML
    void onVideoIconClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageIcon.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ((ImageView)imageIcon.getParent().getChildrenUnmodifiable().get(3)).setImage(image);
        }
    }

}
