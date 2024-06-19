package com.example.sportial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import java.util.ArrayList;

public class suggestedFriendCV_RV_Adapter extends RecyclerView.Adapter<suggestedFriendCV_RV_Adapter.MyViewHolder> {

    Context context;
    ArrayList<friendCardModel> suggestedFriendsArrayList;

    public suggestedFriendCV_RV_Adapter(Context context, ArrayList<friendCardModel> suggestedFriendsArrayList) {
        this.context = context;
        this.suggestedFriendsArrayList = suggestedFriendsArrayList;
    }

    @NonNull
    @Override
    public suggestedFriendCV_RV_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.friend_card_view, parent, false);
        return new suggestedFriendCV_RV_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull suggestedFriendCV_RV_Adapter.MyViewHolder holder, int position) {
        holder.friendNameTV.setText(suggestedFriendsArrayList.get(position).getUserName());
        holder.friendLocationTV.setText(suggestedFriendsArrayList.get(position).getUserLocation());
        Glide.with(holder.friendProfileIV).load(suggestedFriendsArrayList.get(position).getUserProfilePic()).into(holder.friendProfileIV);
        // holder.friendProfileIV.setImageURI(suggestedFriendsArrayList.get(position).getUserProfilePic());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTV = itemView.findViewById(R.id.full_name_TV);
            friendLocationTV = itemView.findViewById(R.id.location_TV);
            friendProfileIV = itemView.findViewById(R.id.friendProfilePictureIV);
            requestFriendBtn = itemView.findViewById(R.id.button);

            requestFriendBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    requestFriendBtn.setText(R.string.request_sent);
                    requestFriendBtn.setBackgroundColor(R.color.lightGrey);
                }
            });

        }
    }
}