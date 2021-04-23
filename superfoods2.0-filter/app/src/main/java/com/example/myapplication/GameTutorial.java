package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class GameTutorial extends AppCompatActivity {

    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tutorial);

        int images [] = {R.drawable.gameslide1, R.drawable.gameslide2, R.drawable.gameslide3, R.drawable.gameslide4};


        v_flipper = findViewById(R.id.v_flipper);

        for(int image: images)
        {
            flipperImage(image);
        }

        Button skip = (Button)findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameTutorial.this, gameready.class));
            }
        });
    }


    public void flipperImage(int image){
        ImageView imageView  = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000); //5 sec
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
    }
}