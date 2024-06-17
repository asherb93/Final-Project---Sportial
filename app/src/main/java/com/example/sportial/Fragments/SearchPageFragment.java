package com.example.sportial.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.FriendCV_RV_Adapter;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import java.util.ArrayList;

public class SearchPageFragment extends Fragment {

    ArrayList<friendCardModel> suggestedFriendsList = new ArrayList<>();

    RecyclerView friendsRecyclerView;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_page,container,false);
        friendsRecyclerView = view.findViewById(R.id.friend_recycler_view);
        setFriendList(suggestedFriendsList);
        FriendCV_RV_Adapter adapter = new FriendCV_RV_Adapter(requireContext(),suggestedFriendsList);
        friendsRecyclerView.setAdapter(adapter);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    private void setFriendList(ArrayList<friendCardModel> suggestedFriendsList) {
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Lavy Cohen","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Hezi","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Libi Elkalay","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
        suggestedFriendsList.add(new friendCardModel("Yossi yosipov","Or akiva",R.drawable.ic_profile));
    }
}