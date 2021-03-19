package com.example.myapplication;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Gamestart extends AppCompatActivity {
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView iv;
        iv = (ImageView) findViewById(R.id.imageView3);

        //Buttons
        Button newquestion = (Button)findViewById(R.id.button16);

        newquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gamestart.this, gameready.class));
            }
        });


        Button quit = (Button)findViewById(R.id.button17);

            quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gamestart.this, game.class));
            }
        });




        //Creates a random number from 7 to 1;
        int max = 7;
        int min = 1;
        int question_number = (int) ((Math.random() * (7 - 1)) + 1);
        int answer_number = (int) ((Math.random() * (2 - 1)) + 1);

        ProgressBar mProgressBar;

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(i);

        TextView drink = (TextView)findViewById(R.id.textView9);
        drink.setVisibility(View.INVISIBLE);




        switch(question_number)
        {
            case 1:
                iv.setImageResource(R.drawable.question1);
                new CountDownTimer(20000, 1000) { // 100000 = 100 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question1answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question1answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
            case 2:
                iv.setImageResource(R.drawable.question2);
                new CountDownTimer(20000, 1000) { // 10000 = 10 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question2answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question2answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
            case 3:
                iv.setImageResource(R.drawable.question3);
                new CountDownTimer(20000, 1000) { // 10000 = 10 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question3answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question3answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);

                    }
                }.start();
                break;
            case 4:
                iv.setImageResource(R.drawable.question4);
                new CountDownTimer(20000, 1000) { // 20000 = 20 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000)); // 200000 = 20 sec (Add an extra 0)
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question4answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question4answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
                break;
            case 5:
                iv.setImageResource(R.drawable.question5);
                new CountDownTimer(20000, 1000) { // 10000 = 10 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question5answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question5answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
                break;
            case 6:
                iv.setImageResource(R.drawable.question6);
                new CountDownTimer(20000, 1000) { // 10000 = 10 sec
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question6answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question6answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
                break;
            case 7:
                iv.setImageResource(R.drawable.question7);
                new CountDownTimer(20000, 1000) { //
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                        i++;
                        mProgressBar.setProgress((int)i*100/(20000/1000));
                    }
                    public void onFinish() {
                        if (answer_number == 1) {
                            iv.setImageResource(R.drawable.question7answer1);
                            drink.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            iv.setImageResource(R.drawable.question7answer2);
                            drink.setVisibility(View.VISIBLE);
                        }
                        i++;
                        mProgressBar.setProgress(100);
                    }
                }.start();
                break;



        }


    }
}