package com.example.sportial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportial.Model.friendCardModel;
import com.example.sportial.R;

import java.util.ArrayList;


public class friendCV_RV_Adapter extends RecyclerView.Adapter<friendCV_RV_Adapter.MyViewHolder>{

    Context context;
    ArrayList<friendCardModel> profileFriendArrayList;

    public friendCV_RV_Adapter(Context context,ArrayList<friendCardModel> profileFriendsArrayList) {
        this.context=context;
        this.profileFriendArrayList = profileFriendsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_friend_card_view, parent, false);
        return new friendCV_RV_Adapter.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.friendNameTV.setText(profileFriendArrayList.get(position).getUserName());
        holder.friendLocationTV.setText(profileFriendArrayList.get(position).getUserLocation());
        Glide.with(holder.friendProfileIV).load(profileFriendArrayList.get(position).getUserProfilePic()).into(holder.friendProfileIV);
        // holder.friendProfileIV.setImageURI(suggestedFriendsArrayList.get(position).getUserProfilePic());

    }

    @Override
    public int getItemCount() {
        return profileFriendArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTV;
        TextView friendLocationTV;
        ImageView friendProfileIV;
        Button unFriendButton ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTV = itemView.findViewById(R.id.full_name_TV);
            friendLocationTV =itemView.findViewById(R.id.location_TV);
            friendProfileIV = itemView.findViewById(R.id.friendProfilePictureIV);
            unFriendButton = itemView.findViewById(R.id.unfriendButton);
            unFriendButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    unFriendButton.setText(R.string.request_sent);
                    unFriendButton.setBackgroundColor(R.color.lightGrey);
                }
            });

        }
    }
}
