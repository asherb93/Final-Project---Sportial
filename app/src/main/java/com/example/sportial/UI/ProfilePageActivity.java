package com.example.sportial.UI;

import android.os.Bundle;
import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.sportial.Data.postCardModel;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.R;
import com.example.sportial.profilePostsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;

public class ProfilePageActivity extends AppCompatActivity {

    ArrayList<postCardModel> postCardArray=new ArrayList<>();

    Button postsFragmentBtn;
    private Uri profileImage;
    FirebaseFunctions func = new FirebaseFunctions();
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;

    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_screen);
        profileImageView = findViewById(R.id.imageView);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String imageFileName = "ProfilePicture.png";
        StorageReference ref = storageReference.child(firebaseUser.getUid()+"/images/"+imageFileName);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profileImageView.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        //profileImageView.setImageURI(profileImage);

        postsFragmentBtn = findViewById(R.id.profile_posts_btn);
        postsFragmentBtn.setOnClickListener(v->{
                replaceFragment(new profilePostsFragment());
        });
//        RecyclerView recyclerView = findViewById(R.id.user_posts_RV);
//        setPostCardArray(postCardArray);
//        PV_RV_Adapter adapter = new PV_RV_Adapter(this,postCardArray);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void replaceFragment(Fragment profilePostsFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main,profilePostsFragment);
        fragmentTransaction.commit();
    }



}