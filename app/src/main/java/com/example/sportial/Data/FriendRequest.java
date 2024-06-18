package com.example.sportial.Data;

public class FriendRequest {
    User userReceive;
    User userSend;
    boolean status;

    public FriendRequest(){

    }

    public void sendRequest(User userReceive, User userSend){
        this.userReceive = userReceive;
        this.userSend = userSend;
        //send request to userReceive
    }

}
