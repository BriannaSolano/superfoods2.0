package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DroneHomePost  extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_post);

        firebaseAuth = FirebaseAuth.getInstance();

        but = (Button)findViewById(R.id.button25);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DroneHomePost.this, DroneHomePostMap.class));
                //Toast.makeText(DroneHomePrep.this, "Order Placed", Toast.LENGTH_SHORT).show();
            }
        });


    }


}