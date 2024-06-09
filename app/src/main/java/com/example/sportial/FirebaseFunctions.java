package com.example.sportial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.net.Uri;

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

    public void uploadPicture(Uri imageUri, String imageFileName) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
            // Defining the child of storageReference
            StorageReference ref = storageReference.child(firebaseUser.getUid()+"/images/"+ imageFileName);
            ref.putFile(imageUri);
    }

    public void uploadDetails(String firstNameStr, String lastNameStr, int userBirthDay, String userBirthMonth, int userBirthYear,
                              String genderStr, String countryStr, String cityStr){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        // creating a variable for
        // our object class
        User user = new User(firebaseUser.getUid(), firstNameStr, lastNameStr, userBirthDay, userBirthMonth, userBirthYear, genderStr, cityStr, countryStr);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid());
        databaseReference.setValue(user);
    }

    public Uri getPicture(String Uid, String imageFileName) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        // Defining the child of storageReference
        StorageReference ref = storageReference.child(Uid+"/images/"+imageFileName);
        Uri imageUri = null;
        ref.getFile(imageUri);
        return imageUri;
    }
}