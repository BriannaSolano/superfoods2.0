package com.example.myapplication.GuestHomeFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.CustomerRegister;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class guesthomefragement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guesthome, container,false);

        Button btnOpen = (Button) view.findViewById(R.id.button11);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),CustomerRegister.class);
                startActivity(in);
            }
        });

        return view;
    }
}