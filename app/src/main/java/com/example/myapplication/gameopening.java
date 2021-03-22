package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.ViewHolder.CustomerHomeScreen;
import com.example.myapplication.ViewHolder.GuestHomeScreen;

public class gameopening extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        Button start = (Button)findViewById(R.id.button10);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gameopening.this, GameTutorial.class));
            }
        });

        Button leave = (Button)findViewById(R.id.button18);

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gameopening.this, CustomerHomeScreen.class));
            }
        });

    }


}