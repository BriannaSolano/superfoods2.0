package com.example.myapplication.GuestHomeFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.CustomerRegister;
import com.example.myapplication.R;

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

        Button orderfood = (Button) view.findViewById(R.id.button12);
        orderfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment, new guestorderfragement());
                fr.commit();

            }
        });

        return view;
    }


}