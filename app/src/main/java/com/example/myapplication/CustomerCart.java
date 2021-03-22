package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ViewHolder.CustomerCartHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerCart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference orderplaced;

    TextView totalbeforetax;
    Button placeorder;

    List<SetOrder> cart = new ArrayList<>();

    CustomerCartHolder adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);


        database = FirebaseDatabase.getInstance();
        orderplaced = database.getReference("OrderPlaced");


        recyclerView = (RecyclerView)findViewById(R.id.CartInformation);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        totalbeforetax = (TextView) findViewById(R.id.totalpricebeforetax);
        placeorder = (Button) findViewById(R.id.placeOrder);

        loadFoodList();

    }

    private void loadFoodList(){

    }


}