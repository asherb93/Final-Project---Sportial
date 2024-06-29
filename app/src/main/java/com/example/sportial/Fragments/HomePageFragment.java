package com.example.sportial.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.Homepage_RV_Adapter;
import com.example.sportial.Adapter.PV_RV_Adapter;
import com.example.sportial.Model.postCardModel;
import com.example.sportial.R;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {


    public ArrayList<postCardModel> postCardModelArrayList =new ArrayList<>();
    public RecyclerView homePageRecyclerView;
    private View view;
    public Homepage_RV_Adapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_page, container, false);
        homePageRecyclerView = view.findViewById(R.id.homepage_RV);
        setUsersPosts();
        adapter = new Homepage_RV_Adapter(getActivity(), postCardModelArrayList);
        homePageRecyclerView.setAdapter(adapter);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
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


}