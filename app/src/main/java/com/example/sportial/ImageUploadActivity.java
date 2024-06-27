package com.example.sportial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportial.Data.UploadPictureCallback;

import com.example.sportial.Data.UserModel;
import com.example.sportial.UI.sportChoiceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private static boolean isUploaded = false;
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

        Intent intent = getIntent();
        UserModel userModel = (UserModel) intent.getSerializableExtra("user");

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
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("image/*");
            // Launch the image picker activity
            pickImageLauncher.launch(intent1);
        });

        continueButton.setOnClickListener(view -> {
                String fileName = "ProfilePicture.png";
                userModel.setProfilePictureUrl(imageUri.toString());
                func.uploadDetails(userModel);
                func.uploadSport(userModel);
                func.uploadPicture(imageUri, fileName, new UploadPictureCallback() {
                    @Override
                    public void onUploadComplete(boolean success) {
                        if (success) {
                            // Image uploaded successfully, start next activity
                            Toast.makeText(ImageUploadActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ImageUploadActivity.this, sportialActivity.class);
                            startActivity(intent);
                        } else {
                            // Handle upload failure (e.g., show an error message)
                            Toast.makeText(ImageUploadActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        );

    }
}

