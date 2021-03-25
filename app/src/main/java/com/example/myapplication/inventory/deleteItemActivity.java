package com.example.myapplication.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.App;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.database.Transaction.success;

public class deleteItemActivity extends AppCompatActivity {
    String itemId;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");
        if(getIntent()!= null) {
            itemId = getIntent().getStringExtra("ItemId");
        }
        if(itemId != null && itemId.isEmpty()) {
            finish();
        } else {
            databaseReference.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Log.d("TAG", "Deleting item with id: " + itemId);
                    String numchildren = String.valueOf(currentData.getChildrenCount());
                    currentData.child(String.valueOf(itemId)).setValue(currentData.child(numchildren).getValue());
                    currentData.child(numchildren).setValue(null);
                    return success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    Toast.makeText(deleteItemActivity.this, "Item Successfully Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(deleteItemActivity.this, dashboardActivity.class);
        startActivity(intent);
    }

}

