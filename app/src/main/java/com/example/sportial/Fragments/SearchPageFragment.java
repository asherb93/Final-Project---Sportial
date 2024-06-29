package com.example.sportial.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.suggestedFriendCV_RV_Adapter;
import com.example.sportial.Data.UserModel;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import com.example.sportial.sportialActivity;
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

public class SearchPageFragment extends Fragment {

    ArrayList<friendCardModel> suggestedFriendsList = new ArrayList<>();

    RecyclerView friendsRecyclerView;
    suggestedFriendCV_RV_Adapter adapter;

    View view;

    private static final String TAG = "ReadAndWriteSnippets";
    FirebaseFunctions func = new FirebaseFunctions();
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    // Define the callback method in your Fragment
    private void fragmentCallback(String data) {
        sendDataToActivity(data);//sending the data to activity
    }

    private void sendDataToActivity(String data) {
        if (getActivity() instanceof sportialActivity) {
            ((sportialActivity) getActivity()).receiveData(data);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_page,container,false);
        friendsRecyclerView = view.findViewById(R.id.friend_recycler_view);

        setFriendList(suggestedFriendsList);
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
                UserModel user = dataSnapshot.getValue(UserModel.class);
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
        
    }

    public void showListBySport(String sportType, String uid){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sports/"+sportType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suggestedFriendsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    UserModel user = ds.getValue(UserModel.class);
                    showSuggestedFriends(user,uid);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void showSuggestedFriends(UserModel user, String uid) {

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
                    suggestedFriendsList.add(friend);
                    adapter = new suggestedFriendCV_RV_Adapter(getActivity(), suggestedFriendsList);
                    friendsRecyclerView.setAdapter(adapter);
                    //
                    // refering to the OnItemClickListener interface called in the adapter to pass the data
                    adapter.setOnItemClickListener(new suggestedFriendCV_RV_Adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String data) {//data is passed from adapter through here
                            fragmentCallback(data); // Call your callback method
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
