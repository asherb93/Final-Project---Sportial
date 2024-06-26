package com.example.sportial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportial.Data.UploadPictureCallback;

public class PostActivity extends AppCompatActivity {

    ImageView imageUploadImageView;
    private Uri imageUri;
    private Button post_button;
    FirebaseFunctions func = new FirebaseFunctions();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        // Register the activity result launcher for picking an image
        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Get the image URI from the result
                        imageUri = result.getData().getData();

                        // Set the image URI to the ImageView
                        imageUploadImageView.setImageURI(imageUri);
                    }
                });



        imageUploadImageView.setOnClickListener(view -> {
            // Create an intent to pick an image
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // Launch the image picker activity
            pickImageLauncher.launch(intent);
        });

        post_button.setOnClickListener(view -> {
           uploadImage();
        });


    }

    private void uploadImage() {
        String fileName = "postImage.png";
        func.uploadPicture(imageUri, fileName, new UploadPictureCallback() {
            @Override
            public void onUploadComplete(boolean success) {
                if (success) {
                    // Image uploaded successfully, start next activity
                    Toast.makeText(PostActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostActivity.this, sportialActivity.class);
                    startActivity(intent);
                } else {
                    // Handle upload failure (e.g., show an error message)
                    Toast.makeText(PostActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findViews() {
        imageUploadImageView = findViewById(R.id.post_image_upload_button);
        post_button = findViewById(R.id.post_post_button);
    }
}