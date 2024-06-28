package com.example.sportial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportial.Data.MyCallback;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.Fragments.ProfilePageFragment;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class suggestedFriendCV_RV_Adapter extends RecyclerView.Adapter<suggestedFriendCV_RV_Adapter.MyViewHolder> {

    Context context;
    static ArrayList<friendCardModel> suggestedFriendsArrayList;
    static FirebaseFunctions func = new FirebaseFunctions();
    LayoutInflater inflater;
    View view;

    public suggestedFriendCV_RV_Adapter(Context context, ArrayList<friendCardModel> suggestedFriendsArrayList) {
        this.context = context;
        this.suggestedFriendsArrayList = suggestedFriendsArrayList;
    }

    @NonNull
    @Override
    public suggestedFriendCV_RV_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.friend_card_view, parent, false);
        return new suggestedFriendCV_RV_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull suggestedFriendCV_RV_Adapter.MyViewHolder holder, int position) {
        holder.friendNameTV.setText(suggestedFriendsArrayList.get(position).getUserName());
        holder.friendLocationTV.setText(suggestedFriendsArrayList.get(position).getUserLocation());
        Glide.with(holder.friendProfileIV).load(suggestedFriendsArrayList.get(position).getUserProfilePic()).into(holder.friendProfileIV);
        if (suggestedFriendsArrayList.get(position).getStatus() != null) {
            if ((suggestedFriendsArrayList.get(position).getStatus().equals("sent"))) {
                holder.requestFriendBtn.setText(R.string.request_sent);
                holder.requestFriendBtn.setBackgroundColor(R.color.lightGrey);
                holder.acceptButton.setVisibility(View.INVISIBLE);
                holder.declineButton.setVisibility(View.INVISIBLE);
            }
            if ((suggestedFriendsArrayList.get(position).getStatus().equals("received"))) {
                holder.requestFriendBtn.setVisibility(View.INVISIBLE);
            }
            if ((suggestedFriendsArrayList.get(position).getStatus().equals("friends"))) {
                holder.requestFriendBtn.setVisibility(View.INVISIBLE);
                holder.acceptButton.setVisibility(View.INVISIBLE);
                holder.declineButton.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.acceptButton.setVisibility(View.INVISIBLE);
            holder.declineButton.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String userId = suggestedFriendsArrayList.get(position).getUserId();

                        //change to profile fragment
                        Fragment profileFragment = new ProfilePageFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userId", userId);
                        profileFragment.setArguments(bundle);
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.profile_fragment_container, profileFragment).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return suggestedFriendsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTV;
        TextView friendLocationTV;
        ImageView friendProfileIV;
        Button requestFriendBtn;
        Button acceptButton;
        Button declineButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTV = itemView.findViewById(R.id.full_name_TV);
            friendLocationTV = itemView.findViewById(R.id.location_TV);
            friendProfileIV = itemView.findViewById(R.id.friendProfilePictureIV);
            requestFriendBtn = itemView.findViewById(R.id.button);
            acceptButton = itemView.findViewById(R.id.accept_btn);
            declineButton = itemView.findViewById(R.id.decline_btn);

            requestFriendBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    func.sendFriendRequest(suggestedFriendsArrayList.get(getAdapterPosition()).getUserId(), new MyCallback() {

                        @Override
                        public void FriendRequestCallBack(boolean success) {
                          if(success){
                              suggestedFriendsArrayList.get(getAdapterPosition()).setStatus("received");
                              requestFriendBtn.setText(R.string.request_sent);
                              requestFriendBtn.setBackgroundColor(R.color.lightGrey);
                          }



                        }
                    });
                    suggestedFriendsArrayList.get(getAdapterPosition()).setStatus("sent");
                }
            });

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    func.acceptFriendRequest(suggestedFriendsArrayList.get(getAdapterPosition()).getUserId());
                    suggestedFriendsArrayList.get(getAdapterPosition()).setStatus("friends");
                    acceptButton.setVisibility(View.INVISIBLE);
                    declineButton.setVisibility(View.INVISIBLE);
                }
            });

            declineButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    func.declineFriendRequest(suggestedFriendsArrayList.get(getAdapterPosition()).getUserId());
                    suggestedFriendsArrayList.get(getAdapterPosition()).setStatus("\0");
                    acceptButton.setVisibility(View.INVISIBLE);
                    declineButton.setVisibility(View.INVISIBLE);
                    requestFriendBtn.setVisibility(View.VISIBLE);
                }

            });

        }
    }
}