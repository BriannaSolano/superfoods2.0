package com.example.myapplication.ViewHolder;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.GuestHomeFragments.customeraccounthomefragement;
import com.example.myapplication.GuestHomeFragments.customeraccountlocationfragement;
import com.example.myapplication.GuestHomeFragments.guesthomefragement;
import com.example.myapplication.GuestHomeFragments.guestlocationfragement;
import com.example.myapplication.GuestHomeFragments.guestmorefragement;
import com.example.myapplication.GuestHomeFragments.guestorderfragement;
import com.example.myapplication.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class GuestHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new guesthomefragement()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new guesthomefragement();
                            break;
                        case R.id.navigation_location:
                            selectedFragment = new guestlocationfragement();
                            break;
                        case R.id.navigation_order:
                            selectedFragment = new guestorderfragement();
                            break;
                        case R.id.navigation_more:
                            selectedFragment = new guestmorefragement();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();
                    return true;
                }
            };

}