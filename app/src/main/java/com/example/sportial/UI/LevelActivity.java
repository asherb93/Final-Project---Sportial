package com.example.sportial.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportial.Data.UserModel;
import com.example.sportial.ImageUploadActivity;
import com.example.sportial.R;

public class LevelActivity extends AppCompatActivity {
    String[] level = {"Beginner", "Intermediate", "Professional-requires certificate"};
    AutoCompleteTextView autoCompleteTextView;
    UserModel user;
    ArrayAdapter<String> adapterLevel;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    Button continueBtn;
    private Button uploadCertBtn;
    ImageView certImageView;
    Uri certImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        findViews();

        Intent intent = getIntent();
        user = (UserModel) intent.getSerializableExtra("user");
        EdgeToEdge.enable(this);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterLevel = new ArrayAdapter<String>(this, R.layout.list_items, level);
        autoCompleteTextView.setAdapter(adapterLevel);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedLevel = adapterView.getItemAtPosition(position).toString();
                if(selectedLevel.equals("Professional-requires certificate"))
                {
                    //show cert image view and upload button
                    uploadCertBtn.setVisibility(View.VISIBLE);
                    certImageView.setVisibility(View.VISIBLE);
                }
                else{
                    uploadCertBtn.setVisibility(View.INVISIBLE);
                    certImageView.setVisibility(View.INVISIBLE);
                }
                if(!selectedLevel.isEmpty()) {
                    user.setLevel(selectedLevel);
                }

            }
        });

        // Register the activity result launcher for picking an image
        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Get the image URI from the result
                        certImage = result.getData().getData();

                        // Set the image URI to the ImageView
                        certImageView.setImageURI(certImage);
                    }
                });

        // Set a click listener for the pick image button
            uploadCertBtn.setOnClickListener(v -> {
                // Create an intent to pick an image
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                // Launch the image picker activity
                pickImageLauncher.launch(intent1);
            });



        continueBtn.setOnClickListener(v -> {
            Intent nextIntent=new Intent(this, ImageUploadActivity.class);
            nextIntent.putExtra("user", user);
            startActivity(nextIntent);
        });

    }

    private void findViews() {
        continueBtn = findViewById(R.id.continueButtonLevel);
        certImageView = findViewById(R.id.certificate_image_view);
        uploadCertBtn = findViewById(R.id.upload_certificate_button);
        uploadCertBtn.setVisibility(View.INVISIBLE);
        certImageView.setVisibility(View.INVISIBLE);

    }



}
