package com.example.sportial.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sportial.Adapter.Homepage_RV_Adapter;
import com.example.sportial.Data.UserModel;
import com.example.sportial.Model.postCardModel;
import com.example.sportial.R;
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

public class HomePageFragment extends Fragment {


    public ArrayList<postCardModel> postCardModelArrayList =new ArrayList<>();
    public RecyclerView homePageRecyclerView;
    private View view;
    public Homepage_RV_Adapter adapter;
    public ArrayList <String> usersIds = new ArrayList<>();
    public TextView sportNameTextView;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getSportType();
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home_page, container, false);
        homePageRecyclerView = view.findViewById(R.id.homepage_RV);
        //setUsersPosts();
        adapter = new Homepage_RV_Adapter(getActivity(), postCardModelArrayList);
        homePageRecyclerView.setAdapter(adapter);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        findViews();
        return view;
    }

    private void findViews() {
        sportNameTextView = view.findViewById(R.id.sport_name_textview);
    }

    private void setUsersPosts() {
        //TO DO - get data from backend

        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));
        postCardModelArrayList.add(new postCardModel("bla bla","2006"));

    }

    public void getSportType(){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                String userLevel = user.getLevel();
                String sportType = user.getSportType();
                sportNameTextView.setText(sportType);
                getSportTypeUsers(sportType, userLevel);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getSportTypeUsers(String sportType, String userLevel){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sports/"+sportType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersIds.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    UserModel suggestedUser = ds.getValue(UserModel.class);
                    String suggestedUserId = suggestedUser.getUserId();
                    String suggestedUserLevel = suggestedUser.getLevel();
                    if(userLevel.equals("Beginner")) {
                        if (!suggestedUserId.equals(firebaseUser.getUid())) {
                            usersIds.add(suggestedUserId);
                        }
                    }
                    else if(userLevel.equals("Intermediate")) {
                        if (!suggestedUserId.equals(firebaseUser.getUid()) && (suggestedUserLevel.equals("Intermediate") || suggestedUserLevel.equals("Advanced"))) {
                            usersIds.add(suggestedUserId);
                        }
                    }
                    else if(userLevel.equals("Advanced")) {
                        if (!suggestedUserId.equals(firebaseUser.getUid()) && suggestedUserLevel.equals("Advanced")) {
                            usersIds.add(suggestedUserId);
                        }
                    }
                }
                getUsersPosts(usersIds);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getUsersPosts(ArrayList<String> usersIds) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Posts");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postCardModelArrayList.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        for(String userId : usersIds) {
                            if(ds.getKey().equals(userId)) {
                                long size = ds.getChildrenCount();
                                for(DataSnapshot dsPost: ds.getChildren()) {
                                    postCardModel post = dsPost.getValue(postCardModel.class);
                                    postCardModelArrayList.add(post);
                                }
                            }
                        }
                    }
                    sort(postCardModelArrayList);
                    adapter = new Homepage_RV_Adapter(getActivity(), postCardModelArrayList);
                    homePageRecyclerView.setAdapter(adapter);
                    homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    public static void sort(ArrayList<postCardModel> postCardModelArrayList) {
        postCardModelArrayList.sort((o1, o2)
                -> o2.getDate().compareTo(
                o1.getDate()));
    }




}