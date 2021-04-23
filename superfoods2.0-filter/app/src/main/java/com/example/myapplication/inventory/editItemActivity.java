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

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.google.firebase.database.Transaction.success;

public class editItemActivity extends AppCompatActivity {
    String itemId;
    DatabaseReference databaseReference;
    private EditText itemname, itemcategory, itemprice, itemcount, itemmin;
    Button saveitembutton, cancelbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        saveitembutton = findViewById(R.id.additemadditembutton);
        cancelbutton = findViewById(R.id.additemcancelbutton);
        itemname = findViewById(R.id.additemitemnamefield);
        itemcategory = findViewById(R.id.additemitemcategoryfield);
        itemprice = findViewById(R.id.additemitempricefield);
        itemcount = findViewById(R.id.additemitemcountfield);
        itemmin = findViewById(R.id.additemitemminfield);

        saveitembutton.setText("Save Item");
        cancelbutton.setText("Return");
        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editItemActivity.this, viewInventoryActivity.class));
            }
        });

        if(getIntent()!= null) {
            itemId = getIntent().getStringExtra("ItemId");
        }
        if(itemId != null && itemId.isEmpty()) {
            finish();
        } else {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        try {
                            Map<String, Object> map = (Map<String, Object>) snapshot.child(itemId).getValue();
                            itemname.setText((String) map.get("name"));
                            itemcategory.setText((String) map.get("category"));
                            itemprice.setText((String) map.get("price"));
                            itemcount.setText(String.valueOf(map.get("count")));
                            itemmin.setText(String.valueOf(map.get("min")));
                        } catch (Exception e) {

                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ERROR", error.getMessage());
                }
            };
            databaseReference.addListenerForSingleValueEvent(valueEventListener);

            saveitembutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                                Log.d("TAG", "Editing item with id: " + itemId);
                                Items items = new Items(itemnameValue, itemcategoryValue, itempriceValue, Integer.parseInt(itemcountValue), Integer.parseInt(itemminValue));
                                currentData.child(itemId).setValue(items);
                                return success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Toast.makeText(editItemActivity.this, "Item Successfully Edited", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(editItemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(editItemActivity.this, dashboardActivity.class);
        startActivity(intent);
    }

}

