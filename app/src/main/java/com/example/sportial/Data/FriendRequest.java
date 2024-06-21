package com.example.sportial.Data;

public class FriendRequest {
    UserModel userReceive;
    UserModel userSend;
    boolean status;

    public FriendRequest(){

    }

    public void sendRequest(UserModel userReceive, UserModel userSend){
        this.userReceive = userReceive;
        this.userSend = userSend;
        //send request to userReceive
    }

}
