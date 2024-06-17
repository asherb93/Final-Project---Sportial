package com.example.sportial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportial.R;
import com.example.sportial.Model.sportCardModel;

import java.util.ArrayList;


public class SC_RV_Adapter extends RecyclerView.Adapter<SC_RV_Adapter.MyViewHolder>{

    Context context;
    ArrayList<sportCardModel> sportCardModels;


    public SC_RV_Adapter(Context context, ArrayList<sportCardModel> sportCardModels)
    {
        this.context = context;
        this.sportCardModels = sportCardModels;
    }

    @NonNull
    @Override
    public SC_RV_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the Layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sport_card_view,parent,false);
        return new SC_RV_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SC_RV_Adapter.MyViewHolder holder, int position) {
        //assigning the values
        holder.sport_name.setText(sportCardModels.get(position).getSportName());
        holder.sport_image.setImageResource(sportCardModels.get(position).getSportIcon());
        holder.sport_background.setImageResource(sportCardModels.get(position).getSportBackground());
    }
    @Override
    public int getItemCount() {
        //self explanatory
        return sportCardModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //declaring the views
        ImageView sport_image;
        TextView sport_name;
        ImageView sport_background;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sport_image = itemView.findViewById(R.id.sport_icon);
            sport_name = itemView.findViewById(R.id.sport_name);
            sport_background = itemView.findViewById(R.id.sport_background);
        }
    }
}
