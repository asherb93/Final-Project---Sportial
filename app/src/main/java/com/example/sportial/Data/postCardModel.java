package com.example.sportial.Data;

import android.widget.ImageView;

public class postCardModel {

     String name;
     static int postid = 0;
     String date;
     String text;

    public postCardModel(){

    }
    public postCardModel(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.postid = postid++;
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
}
