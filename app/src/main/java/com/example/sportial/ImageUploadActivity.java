package com.example.sportial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportial.UI.ProfilePageActivity;
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
import java.util.UUID;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFunctions func = new FirebaseFunctions();
    private ImageView profileImageView;
    private Button pickImageButton;

    private Button continueButton;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        profileImageView = findViewById(R.id.profile_image_view);
        pickImageButton = findViewById(R.id.uploadImageButton);
        continueButton = findViewById(R.id.image_upload_continue_btn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Register the activity result launcher for picking an image
        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Get the image URI from the result
                        imageUri = result.getData().getData();

                        // Set the image URI to the ImageView
                        profileImageView.setImageURI(imageUri);
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
            if (imageUri != null) {
                String fileName = "ProfilePicture.png";
                func.uploadPicture(imageUri,fileName);
            }
            Intent intent = new Intent(ImageUploadActivity.this, ProfilePageActivity.class);
            startActivity(intent);
        });
    }
}

