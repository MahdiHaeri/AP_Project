package com.example.client.controllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label BioLbl;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Button followBtn;

    @FXML
    private Label fullNameLbl;

    @FXML
    private Label usernameLbl;

    public String getBioLbl() {
        return BioLbl.getText();
    }

    public void setBioLbl(String text) {
        BioLbl.setText(text);
    }

    public Image getAvatar() {
        return avatar.getImage();
    }

    public void setAvatar(Image image) {
        this.avatar.setImage(image);
    }

    public String getFollowBtn() {
        return followBtn.getText();
    }

    public void setFollowBtn(String text) {
        this.followBtn.setText(text);
    }

    public String getFullNameLbl() {
        return fullNameLbl.getText();
    }

    public void setFullNameLbl(String text) {
        this.fullNameLbl.setText(text);
    }

    public String getUsernameLbl() {
        return usernameLbl.getText();
    }

    public void setUsernameLbl(String text) {
        this.usernameLbl.setText(text);
    }

    @FXML
    void onFollowBtnAction(ActionEvent event) {

    }

    @FXML
    void onRootMouseClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
