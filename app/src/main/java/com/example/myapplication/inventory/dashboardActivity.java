package com.example.myapplication.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.ManagerHomePage;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboardActivity extends AppCompatActivity implements View.OnClickListener  {
    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;
    Button toast;

    private CardView addItems;
    private CardView viewInventory;
    private CardView orderFromSupplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebasenameview = findViewById(R.id.firebasename);

        addItems = (CardView)findViewById(R.id.addItems);
        viewInventory = (CardView) findViewById(R.id.viewInventory);
        orderFromSupplier = (CardView) findViewById(R.id.orderFromSupplier);


        addItems.setOnClickListener(this);
        viewInventory.setOnClickListener(this);
        orderFromSupplier.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.addItems : i = new Intent(this,addItemActivity.class); startActivity(i); break;
            case R.id.viewInventory : i = new Intent(this,viewInventoryActivity.class);startActivity(i); break;
            case R.id.orderFromSupplier : i = new Intent(this,orderFromSupplierActivity.class);startActivity(i); break;
            default: break;
        }
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(dashboardActivity.this, MainActivity.class));
        Toast.makeText(dashboardActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
    }

}
