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

import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.sportial.Data.postCardModel;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.R;
import com.example.sportial.User;
import com.example.sportial.profilePostsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;

public class ProfilePageActivity extends AppCompatActivity {

    private static final int MENU_ITEM_HOME = R.id.navigation_home;
    private static final int MENU_ITEM_PROFILE = R.id.navigation_profile;
    private static final int MENU_ITEM_SEARCH = R.id.navigation_search;

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

    BottomNavigationView bottomNavigationView;

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

        profileFragmentSelector();
        navigationViewListeners();


//        RecyclerView recyclerView = findViewById(R.id.user_posts_RV);
//        setPostCardArray(postCardArray);
//        PV_RV_Adapter adapter = new PV_RV_Adapter(this,postCardArray);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void profileFragmentSelector() {
        postsFragmentBtn = findViewById(R.id.profile_posts_btn);
        postsFragmentBtn.setOnClickListener(v->{
            replaceFragment(new profilePostsFragment());
        });

    }

    private void navigationViewListeners() {


        bottomNavigationView.setSelectedItemId(MENU_ITEM_PROFILE);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == MENU_ITEM_HOME) {
                startActivity(new Intent(ProfilePageActivity.this, HomePageActivity.class));
                onPause();
                return true;
            }
            else if (item.getItemId() == MENU_ITEM_SEARCH) {
              //  startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            }
            return false;
        });

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
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
    }





}