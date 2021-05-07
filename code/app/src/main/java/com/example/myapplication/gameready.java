package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class gameready extends AppCompatActivity {
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameready);
        timer = (TextView) findViewById(R.id.time);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                startActivity(new Intent(gameready.this, Gamestart.class));
            }
        }.start();
    }


}