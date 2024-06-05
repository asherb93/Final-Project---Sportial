package com.example.sportial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportial.Adapter.PV_RV_Adapter;
import com.example.sportial.Data.postCardModel;

import java.util.ArrayList;

public class profilePostsFragment extends Fragment {

    ArrayList<postCardModel> postCardModelArrayList =new ArrayList<>();
    RecyclerView postRecyclerView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_posts, container, false);
        postRecyclerView = view.findViewById(R.id.user_posts_RV);
        setPostCardArray(postCardModelArrayList);
        PV_RV_Adapter adapter = new PV_RV_Adapter(requireContext(), postCardModelArrayList);
        postRecyclerView.setAdapter(adapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    //get data from backend
    private void setPostCardArray(ArrayList<postCardModel> postCardModelArrayList) {
        postCardModelArrayList.add(new postCardModel("","",""));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Posuere urna nec tincidunt praesent semper feugiat nibh. Nibh mauris cursus mattis molestie a iaculis. Ullamcorper malesuada proin libero nunc consequat interdum varius sit. Tristique senectus et netus et malesuada fames. Commodo quis imperdiet massa tincidunt nunc pulvinar sapien et ligula. Pellentesque dignissim enim sit amet venenatis urna cursus eget. Eget dolor morbi non arcu. Amet consectetur adipiscing elit pellentesque habitant morbi tristique. Feugiat pretium nibh ipsum consequat. Lectus nulla at volutpat diam ut. Odio aenean sed adipiscing diam donec adipiscing tristique risus.\n" +
                "\n"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text5"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text6"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text7"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text8"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text9"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text10"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text11"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text12"));
        postCardModelArrayList.add(new postCardModel("Michael Jordan","May 25th 2024","Text13"));
    }

}