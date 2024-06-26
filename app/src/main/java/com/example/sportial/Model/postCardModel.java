package com.example.sportial.Model;

import android.net.Uri;

import java.net.URI;

public class postCardModel {

     String name;
     static int postid = 0;
     String date;
     String text;
     private Uri post_picture;

    public static void setPostid(int postid) {
        postCardModel.postid = postid;
    }


    public postCardModel(){

    }
    public postCardModel(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.postid = postid++;

    }

    public postCardModel(String name, String date, String text,Uri post_picture) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.postid = postid++;
        this.post_picture = post_picture;

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

    public int getPostid() {
        return postid;
    }

    public Uri getPost_picture() {
        return post_picture;
    }

    public void setPost_picture(Uri post_picture) {
        this.post_picture = post_picture;
    }

}
