package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.ViewHolder.CustomerCartHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerCart extends AppCompatActivity {

    List<CartInfo> Cart;
    RecyclerView recyclerView;
    CustomerCartHolder helperAdapter;
    DatabaseReference databaseReference;
    DatabaseReference databaseRef;
    TextView priceText;
    TextView TaxText;
    TextView FinalTotal;

    //Shared Preference
    private SharedPreferences cartPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone;";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        priceText = findViewById(R.id.totalpricebeforetax);
        TaxText = findViewById(R.id.Tax);
        FinalTotal = findViewById(R.id.totalprice);

        //shared Preference
        cartPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String phone = cartPreferences.getString(KEY_PHONE,null);
        SharedPreferences.Editor editor = cartPreferences.edit();
        editor.commit();


        recyclerView = findViewById(R.id.CartInformation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cart = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
        databaseReference = databaseReference.child(phone);

        databaseRef = FirebaseDatabase.getInstance().getReference("Cart");
        databaseRef = databaseRef.child(phone);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    CartInfo data = ds.getValue(CartInfo.class);
                    Cart.add(data);
                }
                helperAdapter = new CustomerCartHolder(Cart);
                recyclerView.setAdapter(helperAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double sum = 0.0;
                for(DataSnapshot data: snapshot.getChildren()){
                    sum = sum + Double.parseDouble(data.child("TotalFoodPrice").getValue().toString());
                }

                double tax = sum * 0.07;
                double priceandtax = tax + sum;
                DecimalFormat df = new DecimalFormat("#.00");
                String angleFormated = df.format(tax);
                String angleForm = df.format(priceandtax);
                String angle = df.format(sum);
                TaxText.setText(angleFormated);
                FinalTotal.setText(angleForm);
                priceText.setText(angle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}