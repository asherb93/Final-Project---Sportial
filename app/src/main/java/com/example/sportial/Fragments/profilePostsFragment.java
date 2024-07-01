package com.example.sportial.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.PV_RV_Adapter;
import com.example.sportial.Model.postCardModel;
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

public class profilePostsFragment extends Fragment {

    ArrayList<postCardModel> postCardModelArrayList =new ArrayList<>();
    RecyclerView postRecyclerView;
    View view;
    PV_RV_Adapter adapter;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_posts, container, false);

        postRecyclerView = view.findViewById(R.id.user_posts_RV);

        setPostCardArray(postCardModelArrayList);

        return view;
    }

    //get data from backend
    private void setPostCardArray(ArrayList<postCardModel> postCardModelArrayList) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts/" + userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postCardModelArrayList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    postCardModel post = ds.getValue(postCardModel.class);
                    postCardModelArrayList.add(post);
                    adapter = new PV_RV_Adapter(getActivity(), postCardModelArrayList);
                    postRecyclerView.setAdapter(adapter);
                    postRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getArguments().getSerializable("userId").toString();
        if(userID==null)
        {
            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            userID = firebaseUser.getUid();
        }

    }
}
