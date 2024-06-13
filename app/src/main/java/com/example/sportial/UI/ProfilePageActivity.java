package com.example.sportial.UI;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.example.sportial.Data.postCardModel;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.R;
import com.example.sportial.User;
import com.example.sportial.profilePostsFragment;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;

public class ProfilePageActivity extends AppCompatActivity {
    private static final String TAG = "ReadAndWriteSnippets";
    ArrayList<postCardModel> postCardArray=new ArrayList<>();

    Button postsFragmentBtn;
    private Uri profileImage;
    FirebaseFunctions func = new FirebaseFunctions();
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView profileBackgroundImageView;
    ImageView profileImageView;
    TextView profileNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String imageFileName = "ProfilePicture.png";
        StorageReference ref = storageReference.child(firebaseUser.getUid()+"/images/"+imageFileName);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(ProfilePageActivity.this, "Profile Picture", Toast.LENGTH_SHORT).show();
                Glide.with(ProfilePageActivity.this).load(uri).into(profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(ProfilePageActivity.this, "Profile Picture", Toast.LENGTH_SHORT).show();
                // Handle any errors
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                profileNameTextView.setText(user.getFullName());
                profileNameTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(userListener);

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

    private void findViews()
    {
        profileImageView = findViewById(R.id.imageView);
        profileBackgroundImageView = findViewById(R.id.profile_background_image);
        profileNameTextView = findViewById(R.id.profile_name_TV);
        profileNameTextView.setVisibility(View.INVISIBLE);
    }



}