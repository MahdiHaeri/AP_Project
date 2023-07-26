package com.example.client.controllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetController implements Initializable {

    @FXML
    private GNAvatarView avatarView;

    @FXML
    private Button likeBtn;

    @FXML
    private Label ownerNameLbl;

    @FXML
    private Label ownerUsernameLbl;

    @FXML
    private Button replyBtn;

    @FXML
    private Button retweetBtn;

    @FXML
    private Label retweeterNameLbl;

    @FXML
    private Button shareBtn;

    @FXML
    private Label textMessageLbl;

    @FXML
    private Label timestapLbl;

//    public GNAvatarView getAvatarView() {
//        return avatarView;
//    }
//
//    public void setAvatarView(GNAvatarView avatarView) {
//        this.avatarView = avatarView;
//    }
//
    public String getLikeBtn() {
        return likeBtn.getText();
    }
//
    public void setLikeBtn(String text) {
        this.likeBtn.setText(text);
    }

    public String getOwnerNameLbl() {
        return ownerNameLbl.getText();
    }

    public void setOwnerNameLbl(String text) {
        this.ownerNameLbl.setText(text);
    }

    public String getOwnerUsernameLbl() {
        return ownerUsernameLbl.getText();
    }

    public void setOwnerUsernameLbl(String text) {
        this.ownerUsernameLbl.setText(text);
    }

    public String getReplyBtn() {
        return replyBtn.getText();
    }

    public void setReplyBtn(String text) {
        this.replyBtn.setText(text);
    }

    public String getRetweetBtn() {
        return retweetBtn.getText();
    }

    public void setRetweetBtn(String text) {
        this.retweetBtn.setText(text);
    }

    public String getRetweeterNameLbl() {
        return retweeterNameLbl.getText();
    }

    public void setRetweeterNameLbl(String text) {
        this.retweeterNameLbl.getText();
    }

//    public Button getShareBtn() {
//        return shareBtn;
//    }

//    public void setShareBtn(Button shareBtn) {
//        this.shareBtn = shareBtn;
//    }

    public String getTextMessageLbl() {
        return textMessageLbl.getText();
    }

    public void setTextMessageLbl(String text) {
        this.textMessageLbl.setText(text);
    }

    public String getTimestapLbl() {
        return timestapLbl.getText();
    }

    public void setTimestapLbl(String text) {
        this.timestapLbl.setText(text);
    }


// event handling :

    @FXML
    void onAvatarViewClicked(MouseEvent event) {

    }

    @FXML
    void onLikeBtnAction(ActionEvent event) {

    }

    @FXML
    void onReplyBtnAction(ActionEvent event) {

    }

    @FXML
    void onRetweetBtnAction(ActionEvent event) {

    }

    @FXML
    void onShareBtnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOwnerNameLbl("Gleidson");
        setOwnerUsernameLbl("@gleidson28");
        setTextMessageLbl("Hello World!");
        setTimestapLbl("1h");
    }
}
