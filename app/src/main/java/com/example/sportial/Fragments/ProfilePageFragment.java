package com.example.sportial.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.sportial.Data.postCardModel;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.R;
import com.example.sportial.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfilePageFragment extends Fragment {

    private static final int MENU_ITEM_HOME = R.id.navigation_home;
    private static final int MENU_ITEM_POSTS = R.id.nav_posts;
    private static final int MENU_ITEM_SEARCH = R.id.navigation_search;

    private static final String TAG = "ReadAndWriteSnippets";
    ArrayList<postCardModel> postCardArray = new ArrayList<>();

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

    Fragment postsFragment;

    BottomNavigationView bottomNavigationView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile_page, container, false);
        findViews(view);

        Fragment postsFragment = new profilePostsFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_fragment_container, postsFragment).commit();

        return view;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String imageFileName = "ProfilePicture.png";
        StorageReference ref = storageReference.child(firebaseUser.getUid() + "/images/" + imageFileName);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(getContext(), "Profile Picture", Toast.LENGTH_SHORT).show();
                Glide.with(ProfilePageFragment.this).load(uri).into(profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "Profile Picture", Toast.LENGTH_SHORT).show();
                // Handle any errors
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/" + firebaseUser.getUid());
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



    }









    private void findViews(View view) {
        profileImageView = view.findViewById(R.id.imageView);
        profileBackgroundImageView = view.findViewById(R.id.profile_background_image);
        profileNameTextView = view.findViewById(R.id.profile_name_TV);
        profileNameTextView.setVisibility(View.INVISIBLE);
        bottomNavigationView = view.findViewById(R.id.profile_menu);

    }
}
