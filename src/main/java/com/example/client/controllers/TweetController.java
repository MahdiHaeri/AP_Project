package com.example.client.controllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetController implements Initializable {

    @FXML
    private GNAvatarView avatarView;

    @FXML
    private ImageView imageView;

    @FXML
    private Button likeBtn;

    @FXML
    private MediaView meidaView;

    @FXML
    private Label ownerNameLbl;

    @FXML
    private Label quoteLbl;

    @FXML
    private Button replyBtn;

    @FXML
    private Button retweetBtn;

    @FXML
    private Button shareBtn;

    @FXML
    private Label textMessageLbl;

    @FXML
    private Label timestapLbl;

    @FXML
    private Label writerNameLbl;

    @FXML
    private Label writerUsernameLbl;

//    public GNAvatarView getAvatarView() {
//        return avatarView;
//    }
//
//    public void setAvatarView(GNAvatarView avatarView) {
//        this.avatarView = avatarView;
//    }

//    public ImageView getImageView() {
//        return imageView;
//    }
//
//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }

//    public Button getLikeBtn() {
//        return likeBtn;
//    }
//
//    public void setLikeBtn(Button likeBtn) {
//        this.likeBtn = likeBtn;
//    }

//    public MediaView getMeidaView() {
//        return meidaView;
//    }
//
//    public void setMeidaView(MediaView meidaView) {
//        this.meidaView = meidaView;
//    }

    public String getOwnerNameLbl() {
        return ownerNameLbl.getText();
    }

    public void setOwnerNameLbl(String text ) {
        this.ownerNameLbl.setText(text); ;
    }

    public String getQuoteLbl() {
        return quoteLbl.getText();
    }

    public void setQuoteLbl(String text) {
        this.quoteLbl.setText(text);
    }

//    public Button getReplyBtn() {
//        return replyBtn;
//    }
//
//    public void setReplyBtn(Button replyBtn) {
//        this.replyBtn = replyBtn;
//    }
//
//    public Button getRetweetBtn() {
//        return retweetBtn;
//    }
//
//    public void setRetweetBtn(Button retweetBtn) {
//        this.retweetBtn = retweetBtn;
//    }
//
//    public Button getShareBtn() {
//        return shareBtn;
//    }
//
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

    public String getWriterNameLbl() {
        return writerNameLbl.getText();
    }

    public void setWriterNameLbl(String text) {
        this.writerNameLbl.setText(text);
    }

    public String getWriterUsernameLbl() {
        return writerUsernameLbl.getText();
    }

    public void setWriterUsernameLbl(String text) {
        this.writerUsernameLbl.setText(text);
    }

    @FXML
    void onLikeBtnAction(ActionEvent event) {
        System.out.println("Like button clicked");
    }

    @FXML
    void onReplyBtnAction(ActionEvent event) {
        System.out.println("Reply button clicked");
    }

    @FXML
    void onRetweetBtnAction(ActionEvent event) {
        System.out.println("Retweet button clicked");
    }

    @FXML
    void onShareBtnAction(ActionEvent event) {
        System.out.println("Share button clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        writerNameLbl.setText("Gleidson");
        writerUsernameLbl.setText("@gleidson28");
        textMessageLbl.setText("This is a tweet message");
        timestapLbl.setText("10m");
        ownerNameLbl.setText("Gleidson");
        quoteLbl.setText("This is a quote");
//        avatarView.setImage(new Image(""));
    }

    public void setTweetText(String text) {
        textMessageLbl.setText(text);
    }
}
