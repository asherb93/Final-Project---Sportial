package com.example.sportial.Model;

import android.net.Uri;

public class friendCardModel {

    private String userName ;
    private String userLocation;
    private Uri userProfilePic;
    private String userId;
    private String status = null;

    public friendCardModel(String userName, String userLocation, Uri userProfilePic, String userId) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.userProfilePic = userProfilePic;
        this.userId = userId;
    }

    public friendCardModel(String userName, String userLocation) {
        this.userName = userName;
        this.userLocation = userLocation;
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
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
