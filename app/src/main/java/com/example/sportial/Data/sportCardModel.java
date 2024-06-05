package com.example.sportial.Data;

public class sportCardModel {
    String sportName;
    int sportIcon;
    int sportBackground;
    int sportFellows;

    public sportCardModel(String sportName, int sportImage,int sportBackground) {
        this.sportName = sportName;
        this.sportIcon = sportImage;
        this.sportBackground = sportBackground;
    }

    public sportCardModel(String sportName, int sportImage) {
        this.sportName = sportName;
        this.sportIcon = sportImage;
    }


    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getSportIcon() {
        return sportIcon;
    }

    public void setSportIcon(int sportIcon) {
        this.sportIcon = sportIcon;
    }

    public int getSportBackground() {
        return sportBackground;
    }

    public void setSportBackground(int sportBackground) {
        this.sportBackground = sportBackground;
    }
}
