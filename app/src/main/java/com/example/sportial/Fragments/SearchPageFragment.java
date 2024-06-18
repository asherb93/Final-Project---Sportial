package com.example.sportial.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sportial.Adapter.FriendCV_RV_Adapter;
import com.example.sportial.Data.User;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SearchPageFragment extends Fragment {

    ArrayList<friendCardModel> suggestedFriendsList = new ArrayList<>();

    RecyclerView friendsRecyclerView;
    FriendCV_RV_Adapter adapter;

    View view;

    private static final String TAG = "ReadAndWriteSnippets";
    FirebaseFunctions func = new FirebaseFunctions();
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_page,container,false);
        friendsRecyclerView = view.findViewById(R.id.friend_recycler_view);
        setFriendList(suggestedFriendsList);
        //adapter = new FriendCV_RV_Adapter(requireContext(),suggestedFriendsList);
        //friendsRecyclerView.setAdapter(adapter);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    private void setFriendList(ArrayList<friendCardModel> suggestedFriendsList) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/" + firebaseUser.getUid());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                String sportType = user.getSportType();
                String uid = user.getUserId();
                showListBySport(sportType,uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(userListener);

//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Lavy Cohen","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Hezi","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Libi Elkalay","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
//        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
    }

    public void showListBySport(String sportType, String uid){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sports/"+sportType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suggestedFriendsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    showSuggestedFriends(user,uid);
                    /*if(!((user.getUserId()).equals(uid))) {
                        friendCardModel friend = new friendCardModel(user.getFullName(), user.getCity(),R.drawable.ic_profile );
                        suggestedFriendsList.add(friend);
                    }

                    adapter = new FriendCV_RV_Adapter(getActivity(), suggestedFriendsList);
                    friendsRecyclerView.setAdapter(adapter);*/
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showSuggestedFriends(User user, String uid) {

        if(!((user.getUserId()).equals(uid))) {
            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            String imageFileName = "ProfilePicture.png";
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.child(user.getUserId() + "/images/" + imageFileName);
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(getContext(), "Profile Picture", Toast.LENGTH_SHORT).show();
                    User friendDetails= user;
                    friendCardModel friend = new friendCardModel(friendDetails.getFullName(), friendDetails.getCity(),uri);
                    suggestedFriendsList.add(friend);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getContext(), "Profile Picture", Toast.LENGTH_SHORT).show();
                    // Handle any errors
                }
            });
        }
        adapter = new FriendCV_RV_Adapter(getActivity(), suggestedFriendsList);
        friendsRecyclerView.setAdapter(adapter);
    }

}
