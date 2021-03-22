package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ManagerHomePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

            firebaseAuth = FirebaseAuth.getInstance();

            logout = (Button) findViewById(R.id.button21);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(ManagerHomePage.this, MainActivity.class));
                }
            });
        }
    }

