package com.example.sportial;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.sportial.Data.UploadPictureCallback;
import com.example.sportial.Data.User;
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

    public void uploadDetails(String firstNameStr, String lastNameStr, int userBirthDay, String userBirthMonth, int userBirthYear,
                              String genderStr, String cityStr){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // creating a variable for
        // our object class
        User user = new User(firebaseUser.getUid(), firstNameStr, lastNameStr, userBirthDay, userBirthMonth, userBirthYear, genderStr, cityStr);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid());
        databaseReference.setValue(user);
    }


    public void uploadSport(String sportName){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Sports/"+sportName+"/"+firebaseUser.getUid());
        //level is constant until Ahser will add the levels in the view
        databaseReference.setValue("Level beginner");
    }

    public void uploadPost(postCardModel post){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Posts/"+firebaseUser.getUid()+"/"+post.getPostid());
        databaseReference.setValue(post);
    }

    public void uploadFriend(User friend){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // creating a variable for
        // our object class
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Friends/"+friend.getUserId());
        databaseReference.setValue(friend);
    }

}