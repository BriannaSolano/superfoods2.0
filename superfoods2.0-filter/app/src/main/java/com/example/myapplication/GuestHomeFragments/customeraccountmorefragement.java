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

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.gameopening;
import com.example.myapplication.usermain;
import com.google.firebase.auth.FirebaseAuth;

public class customeraccountmorefragement extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customeraccountabout, container,false);

        firebaseAuth = FirebaseAuth.getInstance();

        Button logout = (Button) view.findViewById(R.id.SignOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
            }
        });


        return view;
    }

}
