package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DroneHomePrep  extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_prep);

        firebaseAuth = FirebaseAuth.getInstance();

        pay = (Button) findViewById(R.id.button27);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DroneHomePrep.this, DroneHomePage.class));
                //Toast.makeText(DroneHomePrep.this, "Order Placed", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
