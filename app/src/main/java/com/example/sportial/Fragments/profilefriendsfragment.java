package com.example.sportial.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.friendCV_RV_Adapter;
import com.example.sportial.Adapter.suggestedFriendCV_RV_Adapter;
import com.example.sportial.Data.UserModel;
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

public class profilefriendsfragment extends Fragment {

    ArrayList<friendCardModel> profileFriendsArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    friendCV_RV_Adapter adapter;
    View view;

    private static final String TAG = "ReadAndWriteSnippets";
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference friendDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_friends, container, false);
        recyclerView = view.findViewById(R.id.profileFriendRV);
        setFriendsList(profileFriendsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    private void setFriendsList(ArrayList<friendCardModel> profileFriendsArrayList) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/" + firebaseUser.getUid()+ "/FriendRequests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileFriendsArrayList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String status = ds.child("status").getValue(String.class);
                    if(status.equals("friends")){
                        String friendUid = ds.getKey();
                        friendDatabaseReference = firebaseDatabase.getReference("Users/" + friendUid);
                        friendDatabaseReference.addValueEventListener(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                UserModel user = dataSnapshot.getValue(UserModel.class);
                                showFriends(user,firebaseUser.getUid());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showFriends(UserModel user, String uid){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String imageFileName = "ProfilePicture.png";
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child(user.getUserId() + "/images/" + imageFileName);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                UserModel friendDetails= user;
                friendCardModel friend = new friendCardModel(friendDetails.getFullName(), friendDetails.getCity(),uri,friendDetails.getUserId());
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users/" + uid+"/FriendRequests/"+friendDetails.getUserId());
                ValueEventListener userListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.child("status").getValue(String.class);
                        friend.setStatus(status);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                };
                databaseReference.addValueEventListener(userListener);
                profileFriendsArrayList.add(friend);
                adapter = new friendCV_RV_Adapter(getActivity(), profileFriendsArrayList);
                recyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}