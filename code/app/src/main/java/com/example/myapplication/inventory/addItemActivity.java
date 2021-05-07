
package com.example.myapplication.inventory;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Map;

import static com.google.firebase.database.Transaction.success;

public class addItemActivity extends AppCompatActivity {
    private EditText itemname, itemcategory, itemprice, itemcount, itemmin;
    private FirebaseAuth firebaseAuth;
    Button additembutton, cancelbutton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");
        additembutton = findViewById(R.id.additemadditembutton);
        cancelbutton = findViewById(R.id.additemcancelbutton);
        itemname = findViewById(R.id.additemitemnamefield);
        itemcategory = findViewById(R.id.additemitemcategoryfield);
        itemprice = findViewById(R.id.additemitempricefield);
        itemcount = findViewById(R.id.additemitemcountfield);
        itemmin = findViewById(R.id.additemitemminfield);

        additembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addItemActivity.this, dashboardActivity.class));
            }
        });

    }

    public void additem() {
        String itemnameValue = itemname.getText().toString();
        String itemcategoryValue = itemcategory.getText().toString();
        String itempriceValue = itemprice.getText().toString();
        String itemcountValue = itemcount.getText().toString();
        String itemminValue = itemmin.getText().toString();
        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(itempriceValue) && !TextUtils.isEmpty(itemcountValue) && !TextUtils.isEmpty(itemminValue)) {
            databaseReference.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    String itemId = String.valueOf(currentData.getChildrenCount()+1);
                    Log.d("TAG", "Adding item with id: " + itemId);
                    Items items = new Items(itemnameValue, itemcategoryValue, itempriceValue, Integer.parseInt(itemcountValue), Integer.parseInt(itemminValue));
                    currentData.child(itemId).setValue(items);
                    return success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    Toast.makeText(addItemActivity.this, "Item Successfully Added", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(addItemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}
