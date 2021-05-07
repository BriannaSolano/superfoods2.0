package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.ViewHolder.GuestHomeScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.stream.Stream;

import static android.provider.Contacts.SettingsColumns.KEY;

public class rewards extends AppCompatActivity {

    private SharedPreferences cartPreferences;
    private TextView points;
    private Button redeem;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone;";
    private static final String KEY_REWARDS = "totalrewards";
    String rewardString;
    int reward = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        redeem = (Button)findViewById(R.id.button3);

        cartPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String phone = cartPreferences.getString(KEY_PHONE,null);
        SharedPreferences.Editor editor = cartPreferences.edit();
        editor.commit();

        points = (TextView) findViewById(R.id.textView31);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userCode = database.getReference("User").child(phone);

        userCode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                rewardString = snapshot.child("Reward").getValue().toString();
                reward = Integer.parseInt(rewardString);
                if (reward == 0)
                {
                    redeem.setEnabled(false);
                }
                else
                {
                    redeem.setEnabled(true);
                }
                points.setText(rewardString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reward = reward - 100;
                redeem.setEnabled(false);
                userCode.child("Reward").setValue(String.valueOf(reward));
                Toast.makeText(rewards.this, "Rewards Redeemed", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = cartPreferences.edit();
                editor.putString(KEY_REWARDS, String.valueOf("10.0"));
                editor.apply();
            }
        });


    }
}