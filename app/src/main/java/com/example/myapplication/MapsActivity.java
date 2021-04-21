package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> droneList = new ArrayList<Marker>();
    private ArrayList<Polyline> lineList = new ArrayList<Polyline>();
    private LatLng location= new LatLng(40.52243311455501, -74.45817069345065);

    private static LatLng droneLoc = new LatLng(0, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mDatabase = FirebaseDatabase.getInstance().getReference("Drones");
        mDatabase.child("Drone1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    droneLoc = new LatLng((double)task.getResult().child("lat").getValue(), (double)task.getResult().child("long").getValue());
                }
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                droneLoc = new LatLng((double)snapshot.child("Drone1").child("lat").getValue(), (double)snapshot.child("Drone1").child("long").getValue());
                droneList.get(0).setPosition(droneLoc);

                lineList.get(0).setPoints(Arrays.asList(location,droneLoc));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //mDatabase.addChildEventListener(postListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(13);
        mMap.setMaxZoomPreference(18);


        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));




        Marker drone = mMap.addMarker(new MarkerOptions()
                .position(droneLoc)
                .title("Drone")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        drone.setTag(0);
        droneList.add(drone);

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(location)
                .add(droneLoc);
        Polyline polyline = mMap.addPolyline(polylineOptions);
        lineList.add(polyline);
    }
}