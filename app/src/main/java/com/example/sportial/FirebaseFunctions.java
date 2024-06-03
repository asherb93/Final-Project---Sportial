package com.example.sportial;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.net.Uri;
import java.io.File;

public class FirebaseFunctions {

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    public FirebaseFunctions() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void uploadPicture(String userId, Uri imageUri) {
        // Create a reference to the Cloud Storage location
        StorageReference storageRef = storage.getReference().child("user_pictures/" + userId + "/" + imageUri);

        // Upload the picture to Cloud Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded picture
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Store the picture URL in Realtime Database
                        database.getReference("users/" + userId + "/picture_url").setValue(uri.toString());
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that may occur
                });
    }
}