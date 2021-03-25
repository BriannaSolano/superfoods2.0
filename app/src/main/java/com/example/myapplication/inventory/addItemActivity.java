
package com.example.myapplication.inventory;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

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
                finish();
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
            Items items = new Items(itemnameValue, itemcategoryValue, itempriceValue, Integer.parseInt(itemcountValue), Integer.parseInt(itemminValue));
            databaseReference.child(String.valueOf(databaseReference.get().getResult().getChildrenCount()+1)).setValue(items);
            itemname.setText("");
            itemprice.setText("");
            itemprice.setText("");
            itemcount.setText("");
            itemmin.setText("");
            Toast.makeText(addItemActivity.this, itemnameValue + " Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(addItemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}
