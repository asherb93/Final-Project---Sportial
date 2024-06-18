package com.example.sportial.Model;

import android.net.Uri;

public class friendCardModel {

    private String userName ;
    private String userLocation;
    private Uri userProfilePic;

    public friendCardModel(String userName, String userLocation, Uri userProfilePic) {
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

    public Uri getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(Uri userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
