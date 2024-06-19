package com.example.sportial.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.friendCV_RV_Adapter;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import java.util.ArrayList;

public class profilefriendsfragment extends Fragment {

    ArrayList<friendCardModel> profileFriendsArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_friends, container, false);
        recyclerView = view.findViewById(R.id.profileFriendRV);
        setPostCardArray(profileFriendsArrayList);
        friendCV_RV_Adapter adapter = new friendCV_RV_Adapter(requireContext(),profileFriendsArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    private void setPostCardArray(ArrayList<friendCardModel> profileFriendsArrayList) {
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));
        profileFriendsArrayList.add(new friendCardModel("kaki","pipi"));

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}