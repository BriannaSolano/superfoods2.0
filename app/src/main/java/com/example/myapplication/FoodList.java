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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.ViewHolder.FoodHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Calorie Button
    FloatingActionButton See;
    private Spinner select;
    private Spinner filter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    private Boolean activate = Boolean.FALSE;
    FirebaseRecyclerAdapter<Food, FoodHolder> adapter;
    private String Text;

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
        //diet and allergy
        select = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Filter, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        select.setAdapter(adapter);
        select.setOnItemSelectedListener(this);

        //Category to Food list

        if(getIntent() != null)
        {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null)
        {
            loadListFood(categoryId, 0, "");


        }


    }

    public void loadListFood(String categoryId, Integer filt, String name) {
    Query query = null;
        switch(filt) {
            case 0: {
                query = foodList.orderByChild("MenuId").equalTo(categoryId);
                break;
            }
            case 1: {
                if (name.equals("Vegetarian")) {
                    query = foodList.orderByChild("Vegetarian").equalTo(categoryId+"_"+"1");

                } else if (name.equals("None")) {
                    query = foodList.orderByChild("MenuId").equalTo(categoryId);

                }
                break;
            }
            case 2: {
                if (name.equals("Peanut")) {
                    query = foodList.orderByChild("Peanut").equalTo(categoryId+"_"+"1");

                } else if (name.equals("None")) {
                    query = foodList.orderByChild("MenuId").equalTo(categoryId);

                }
                break;

            }

        }
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
           protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model){
                holder.food_name.setText(model.getName());
                holder.food_calorie.setText(model.getCalories());
                holder.food_salt.setText(model.getSalt());
                holder.food_fat.setText(model.getFat());
                holder.food_sugar.setText(model.getSugar());
                See.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activate = !activate;
                        notifyDataSetChanged();
                    }

                });
                if(activate) {
                    holder.food_title.setVisibility(recyclerView.VISIBLE);
                    holder.food_title1.setVisibility(recyclerView.VISIBLE);
                    holder.food_title2.setVisibility(recyclerView.VISIBLE);
                    holder.food_title3.setVisibility(recyclerView.VISIBLE);
                    holder.food_calorie.setVisibility(recyclerView.VISIBLE);
                    holder.food_sugar.setVisibility(recyclerView.VISIBLE);
                    holder.food_fat.setVisibility(recyclerView.VISIBLE);
                    holder.food_salt.setVisibility(recyclerView.VISIBLE);
                }
                else{
                    holder.food_title.setVisibility(recyclerView.INVISIBLE);
                    holder.food_title1.setVisibility(recyclerView.INVISIBLE);
                    holder.food_title2.setVisibility(recyclerView.INVISIBLE);
                    holder.food_title3.setVisibility(recyclerView.INVISIBLE);
                    holder.food_calorie.setVisibility(recyclerView.INVISIBLE);
                    holder.food_sugar.setVisibility(recyclerView.INVISIBLE);
                    holder.food_fat.setVisibility(recyclerView.INVISIBLE);
                    holder.food_salt.setVisibility(recyclerView.INVISIBLE);
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

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       Text = parent.getItemAtPosition(position).toString();
       if(Text.equals("Diet")){
           filter = findViewById(R.id.spinner3);
           ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Diet, android.R.layout.simple_spinner_item );
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
           filter.setAdapter(adapter);
           filter.setOnItemSelectedListener(this);
           filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   Text = parent.getItemAtPosition(position).toString();
                   String output = null;
                   Integer code = 1;
                   switch(Text) {
                       case "Vegetarian": {
                           output = "Vegetarian";
                           break;
                       }
                       default:{
                           output = "None";
                           code = 0;
                           break;
                       }

                   }
                   loadListFood(categoryId, code, output);
                   onStart();

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

       }
        else if(Text.equals("Allergy")){
           filter = findViewById(R.id.spinner3);
           ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Allergy, android.R.layout.simple_spinner_item );
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
           filter.setAdapter(adapter);
           filter.setOnItemSelectedListener(this);
           filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   Text = parent.getItemAtPosition(position).toString();
                   String output = null;
                   Integer code = 2;
                   switch(Text) {
                       case "Peanut": {
                           output = "Peanut";
                           code = 2;
                           break;
                       }
                       default:{
                           output = "None";
                           code = 0;
                           break;
                       }

                   }
                   loadListFood(categoryId, code, output);
                   onStart();

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

       }
        else if(Text.equals("None")){
           loadListFood(categoryId, 0, "");
           onStart();
       }


   }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}