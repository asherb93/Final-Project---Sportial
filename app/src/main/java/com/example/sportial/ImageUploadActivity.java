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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView profileImageView;
    private Button pickImageButton;
    private FirebaseFunctions uploadim;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        profileImageView = findViewById(R.id.profile_image_view);
        pickImageButton = findViewById(R.id.uploadImageButton);

        // Register the activity result launcher for picking an image
        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Get the image URI from the result
                        imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // Set the image URI to the ImageView
                        profileImageView.setImageURI(imageUri);
                    }
                });

        // Set a click listener for the pick image button
        pickImageButton.setOnClickListener(view -> {
            FirebaseUser user = mAuth.getCurrentUser();
            uploadim.uploadPicture(user.getUid(), imageUri);
            // Create an intent to pick an image
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            // Launch the image picker activity
            pickImageLauncher.launch(intent);
        });
    }
}