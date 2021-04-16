package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.ViewHolder.FoodHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    //Calorie Button
    FloatingActionButton See;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    private Boolean activate = Boolean.FALSE;
    FirebaseRecyclerAdapter<Food, FoodHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Calorie Button

        See = findViewById(R.id.floatingActionButton);





        //iniate FireBase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Category to Food list

        if(getIntent() != null)
        {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null)
        {
            loadListFood(categoryId);


        }


    }

    private void loadListFood(String categoryId) {
        Query query = foodList.orderByChild("MenuId").equalTo(categoryId);

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodHolder>(options) {
            @Override
            public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item, parent, false);
                return new FoodHolder(itemView);
            }
            @Override
            protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
                holder.food_name.setText(model.getName());
                holder.food_calorie.setText(model.getCalories());
                See.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activate = !activate;
                        notifyDataSetChanged();
                    }

                });
                if(activate) {
                    holder.food_title.setVisibility(recyclerView.VISIBLE);
                    holder.food_calorie.setVisibility(recyclerView.VISIBLE);
                }
                else{
                    holder.food_title.setVisibility(recyclerView.INVISIBLE);
                    holder.food_calorie.setVisibility(recyclerView.INVISIBLE);
                }

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.food_image);
                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodCode = new Intent(FoodList.this,SelectedFood.class);
                        foodCode.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodCode);
                    }
                });

            }
        };
        Log.d("TAG", ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
