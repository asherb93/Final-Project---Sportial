package com.example.sportial.Model;

import android.net.Uri;

public class friendCardModel {

    private String userName ;
    private String userLocation;
    private int userProfilePic;

    public friendCardModel(String userName, String userLocation, int userProfilePic) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.userProfilePic = userProfilePic;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public int getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(int userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
