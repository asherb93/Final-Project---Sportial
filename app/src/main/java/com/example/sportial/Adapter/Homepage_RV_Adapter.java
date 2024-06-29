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
import com.example.sportial.Model.postCardModel;
import com.example.sportial.R;

import java.util.ArrayList;

public class Homepage_RV_Adapter extends RecyclerView.Adapter<Homepage_RV_Adapter.MyViewHolder> {

    Context context;
    ArrayList<postCardModel> postCardModels;





    public Homepage_RV_Adapter(Context context, ArrayList<postCardModel> postCardModels) {
        this.context = context;
        this.postCardModels = postCardModels;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//MyViewHolder Constructor
        LayoutInflater layoutInflater = LayoutInflater.from(context);//obtains the layout inflater from the given context
        View view = layoutInflater.inflate(R.layout.post_card_view, parent, false);//inflating the layout into the view Object
        return new MyViewHolder(view);//returns ViewHolder with the layout
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {//binding View components inside of the View itself
        //assigning the values
        holder.post_name.setText(postCardModels.get(position).getName());
        holder.post_date.setText(postCardModels.get(position).getDate().toString());
        holder.post_text.setText(postCardModels.get(position).getText());
//        Glide.with(holder.post_profile_picture).load(Uri.parse(postCardModels.get(position).getProfile_picture())).into(holder.post_profile_picture);
        //holder.post_profile_picture.setImageURI(Uri.parse(postCardModelArrayList.get(position).getProfile_picture()));
        if(postCardModels.get(position).getHasImage()){
            Glide.with(holder.post_image).load(Uri.parse(postCardModels.get(position).getPost_picture())).into(holder.post_image);
            //holder.post_image.setImageURI(Uri.parse(postCardModelArrayList.get(position).getPost_picture()));
        }
    }






    @Override
    public int getItemCount() {
        return postCardModels.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView post_name;
        TextView post_date;
        TextView post_text;
        ImageView post_image;
        ImageView post_profile_picture;

        public MyViewHolder(@NonNull View itemView) {//initializing the View components by id
            super(itemView);
            post_name = itemView.findViewById(R.id.post_name);
            post_date = itemView.findViewById(R.id.post_date);
            post_text = itemView.findViewById(R.id.post_text);
            post_profile_picture =itemView.findViewById(R.id.friendProfilePictureIV);
            post_image = itemView.findViewById(R.id.post_image_view);


        }
    }






}
