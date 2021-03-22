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
    public ImageView food_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodHolder(View itemView) {
        super(itemView);


        food_name = (TextView) itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);
        food_calorie = (TextView) itemView.findViewById(R.id.food_calorie);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}


