package com.example.myapplication.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;

public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public TextView food_calorie;
    public TextView food_sugar;
    public TextView food_salt;
    public TextView food_fat;
    public ImageView food_image;
    public TextView food_title;
    public TextView food_title1;
    public TextView food_title2;
    public TextView food_title3;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodHolder(View itemView) {
        super(itemView);


        food_name = (TextView) itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);
        food_calorie = (TextView) itemView.findViewById(R.id.food_calorie);
        food_salt = (TextView) itemView.findViewById(R.id.food_salt);
        food_sugar = (TextView) itemView.findViewById(R.id.food_sugar);
        food_fat = (TextView) itemView.findViewById(R.id.food_fat);
        food_title = (TextView) itemView.findViewById(R.id.food_calorie2);
        food_title1 = (TextView) itemView.findViewById(R.id.food_sugar2);
        food_title2 = (TextView) itemView.findViewById(R.id.food_fat2);
        food_title3 = (TextView) itemView.findViewById(R.id.food_salt2);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}


