package com.example.sportial.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportial.Fragments.ProfilePageFragment;
import com.example.sportial.R;
import com.example.sportial.Model.postCardModel;

import java.util.ArrayList;

public class PV_RV_Adapter extends RecyclerView.Adapter<PV_RV_Adapter.MyViewHolder>{

    Context context;
    ArrayList<postCardModel> postCardModelArrayList;


    public PV_RV_Adapter(Context context, ArrayList<postCardModel> postCardModelArrayList)
    {
        this.context = context;
        this.postCardModelArrayList = postCardModelArrayList;
    }

    @NonNull
    @Override
    public PV_RV_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the Layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_card_view,parent,false);
        return new PV_RV_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PV_RV_Adapter.MyViewHolder holder, int position) {
        //assigning the values
        holder.post_name.setText(postCardModelArrayList.get(position).getName());
        holder.post_date.setText(postCardModelArrayList.get(position).getDate().toString());
        holder.post_text.setText(postCardModelArrayList.get(position).getText());
        Glide.with(holder.post_profile_picture).load(Uri.parse(postCardModelArrayList.get(position).getProfile_picture())).into(holder.post_profile_picture);
        //holder.post_profile_picture.setImageURI(Uri.parse(postCardModelArrayList.get(position).getProfile_picture()));
        if(postCardModelArrayList.get(position).getHasImage()){
            Glide.with(holder.post_image).load(Uri.parse(postCardModelArrayList.get(position).getPost_picture())).into(holder.post_image);
            //holder.post_image.setImageURI(Uri.parse(postCardModelArrayList.get(position).getPost_picture()));
            holder.post_image.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public int getItemCount() {
        //self explanatory
        return postCardModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //declaring the views
        TextView post_name;
        TextView post_date;
        TextView post_text;
        ImageView post_image;
        ImageView post_profile_picture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            post_name = itemView.findViewById(R.id.post_name);
            post_date = itemView.findViewById(R.id.post_date);
            post_text = itemView.findViewById(R.id.post_text);
            post_profile_picture =itemView.findViewById(R.id.friendProfilePictureIV);
            post_image = itemView.findViewById(R.id.post_image_view);
            post_image.setVisibility(View.INVISIBLE);
        }
    }
}
