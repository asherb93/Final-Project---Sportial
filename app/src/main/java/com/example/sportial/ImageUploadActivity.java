package com.example.sportial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;

import com.example.sportial.UI.sportChoiceActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private ImageView profileImageView;
    private Button pickImageButton;

    private Button continueButton;
    private FirebaseFunctions uploadim;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        profileImageView = findViewById(R.id.profile_image_view);
        pickImageButton = findViewById(R.id.uploadImageButton);
        continueButton = findViewById(R.id.image_upload_continue_btn);

        // Register a launcher for the image picker activity
        ActivityResultLauncher<Intent> pickImageLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                // Get the image URI from the result
                                Uri imageUri = result.getData().getData();

                            }
                        });

        // Create a reference to the Firebase Storage location where you want to store the image
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + imageUri.getLastPathSegment());

// Upload the image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Store the download URL in the Firebase user database
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateProfile(new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(downloadUri)
                                        .build());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                    }
                });

        // Set a click listener for the pick image button
        pickImageButton.setOnClickListener(view -> {
            // Create an intent to pick an image
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // Launch the image picker activity
            pickImageLauncher.launch(intent);
        });

        continueButton.setOnClickListener(view -> {
            Intent intent = new Intent(ImageUploadActivity.this, sportChoiceActivity.class);
            startActivity(intent);
        });
    }
}

