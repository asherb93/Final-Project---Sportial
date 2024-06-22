package com.example.sportial.Data;

public class FriendRequest {
    String userReceive;
    String userSend;
    String status;

    public FriendRequest(){

    }

    public void sendRequest(String userReceive, String userSend, String status){
        this.userReceive = userReceive;
        this.userSend = userSend;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
