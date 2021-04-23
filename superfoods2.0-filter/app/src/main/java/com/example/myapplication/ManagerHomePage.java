package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.inventory.dashboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerHomePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button accessInventoryPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

            firebaseAuth = FirebaseAuth.getInstance();

            logout = (Button) findViewById(R.id.button21);
            accessInventoryPage = (Button) findViewById(R.id.buttonOpenInventoryPage);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(ManagerHomePage.this, MainActivity.class));
                }
            });

            accessInventoryPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ManagerHomePage.this, dashboardActivity.class));
                }
            });
        }
    }

