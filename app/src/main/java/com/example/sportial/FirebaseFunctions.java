package com.example.sportial;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.sportial.Data.FriendRequest;
import com.example.sportial.Data.UploadPictureCallback;
import com.example.sportial.Data.UserModel;
import com.example.sportial.Model.postCardModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;

public class FirebaseFunctions {

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    public void uploadPicture(Uri imageUri, String imageFileName, UploadPictureCallback callback) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
            // Defining the child of storageReference
        StorageReference ref = storageReference.child(firebaseUser.getUid()+"/images/"+ imageFileName);
        UploadTask uploadTask = ref.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // If upload fails:
                callback.onUploadComplete(false);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // After successful upload:
                callback.onUploadComplete(true);
            }
        });
    }

    public void uploadDetails(UserModel user){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // creating a variable for
        // our object class
        firebaseDatabase = FirebaseDatabase.getInstance();
        user.setUserId(firebaseUser.getUid());
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid());
        databaseReference.setValue(user);


        String authKey = "0a834b669662dbbfad2becdf11e24f02a396d6e0"; // Replace with your App Auth Key
        User chatUser = new User();
        chatUser.setUid(user.getUserId()); // Replace with the UID for the user to be created
        chatUser.setName(user.getFullName());// Replace with the name of the user
        chatUser.setRole(user.getSportType());

        CometChat.createUser(chatUser, authKey, new CometChat.CallbackListener <User> () {
            @Override
            public void onSuccess(User chatUser) {
                Log.d("createUser", chatUser.toString());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e("createUser", e.getMessage());
            }
        });

        CometChatUIKit.login(user.getUserId(), new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Login Successful : " + user.toString());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Login Failed : " + e.getMessage());
            }

        });
    }


    public void uploadSport(UserModel user){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Sports/"+user.getSportType()+"/"+firebaseUser.getUid());
        //level is constant until Ahser will add the levels in the view
        databaseReference.setValue(user);

    }

    public void uploadPost(postCardModel post){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Posts/"+firebaseUser.getUid()+"/"+post.getPostid());
        databaseReference.setValue(post);
    }

    public void uploadFriend(UserModel friend){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // creating a variable for
        // our object class
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Friends/"+friend.getUserId());
        databaseReference.setValue(friend);
    }

    public void sendFriendRequest(String receiverUid){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.sendRequest(receiverUid, firebaseUser.getUid(), "sent");
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/FriendRequests/"+receiverUid);
        databaseReference.setValue(friendRequest)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                }
});

        friendRequest.setStatus("received");
        databaseReference = firebaseDatabase.getReference("Users/"+receiverUid+"/FriendRequests/"+firebaseUser.getUid());
        databaseReference.setValue(friendRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });
    }

    public void acceptFriendRequest(String receiverUid){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.sendRequest(receiverUid, firebaseUser.getUid(), "friends");
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/FriendRequests/"+receiverUid);
        databaseReference.setValue(friendRequest);
        databaseReference = firebaseDatabase.getReference("Users/"+receiverUid+"/FriendRequests/"+firebaseUser.getUid());
        databaseReference.setValue(friendRequest);
    }

    public void declineFriendRequest(String receiverUid){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        //FriendRequest friendRequest = new FriendRequest();
        //friendRequest.sendRequest(receiverUid, firebaseUser.getUid(), "friends");
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/FriendRequests/"+receiverUid);
        databaseReference.removeValue();
        databaseReference = firebaseDatabase.getReference("Users/"+receiverUid+"/FriendRequests/"+firebaseUser.getUid());
        databaseReference.removeValue();
    }

}