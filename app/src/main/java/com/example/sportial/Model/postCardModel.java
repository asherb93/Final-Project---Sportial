package com.example.sportial.Model;

import android.net.Uri;

import java.net.URI;
import java.time.*;
import java.io.Serializable;

public class postCardModel implements Serializable{

     String name;
     String userId;
     String date;
     String text;
     boolean hasImage = false;
     String postPictureUrl;
     String profilePictureUrl;


    public postCardModel(){

    }

    public postCardModel(String text, String date) {
        this.date = date;
        this.text = text;
        this.hasImage = false;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setHasImage(boolean hasImage){
        this.hasImage = hasImage;
    }

    public boolean getHasImage(){
        return hasImage;
    }

    public void setPost_picture(String post_picture) {
        this.postPictureUrl = post_picture;
    }
    public String getPost_picture() {
        return postPictureUrl;
    }
    public void setProfile_picture(String profile_picture) {
        this.profilePictureUrl = profile_picture;
    }
    public String getProfile_picture() {
        return profilePictureUrl;
        }
        public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


}
