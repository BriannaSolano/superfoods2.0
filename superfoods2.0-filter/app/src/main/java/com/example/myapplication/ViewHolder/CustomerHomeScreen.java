package com.example.myapplication.ViewHolder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GuestHomeFragments.customeraccountgamefragement;
import com.example.myapplication.GuestHomeFragments.customeraccounthomefragement;
import com.example.myapplication.GuestHomeFragments.customeraccountlocationfragement;
import com.example.myapplication.GuestHomeFragments.customeraccountmorefragement;
import com.example.myapplication.GuestHomeFragments.customerorderfragement;
import com.example.myapplication.GuestHomeFragments.guesthomefragement;
import com.example.myapplication.GuestHomeFragments.guestlocationfragement;
import com.example.myapplication.GuestHomeFragments.guestorderfragement;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerHomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_account_home_page);
        BottomNavigationView navView = findViewById(R.id.nav_view_accountholder);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_accountholder, new customeraccounthomefragement()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.navigation_home_customeraccount:
                            selectedFragment = new customeraccounthomefragement();
                            break;
                        case R.id.navigation_location_customeraccount:
                            selectedFragment = new customeraccountlocationfragement();
                            break;
                        case R.id.navigation_order_customeraccount:
                            selectedFragment = new customerorderfragement();
                            break;
                        case R.id.navigation_game_customeraccount:
                            selectedFragment = new customeraccountgamefragement();
                            break;
                        case R.id.navigation_more_customeraccount:
                            selectedFragment = new customeraccountmorefragement();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_accountholder,selectedFragment).commit();
                    return true;
                }
            };

}