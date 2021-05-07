package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.concurrent.ThreadLocalRandom;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> droneList = new ArrayList<Marker>();
    private ArrayList<LatLng> droneLoc = new ArrayList<LatLng>();
    private ArrayList<Polyline> lineList = new ArrayList<Polyline>();
    private LatLng location= new LatLng(40.52243311455501, -74.45817069345065);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        for(int i = 1; i <= 4; i++) {
            droneLoc.add(new LatLng(0, 0));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("Drones");



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= 4; i++) {
                    droneLoc.set(i-1, new LatLng((double) snapshot.child("Drone"+i).child("lat").getValue(), (double) snapshot.child("Drone"+i).child("long").getValue()));
                    droneList.get(i-1).setPosition(droneLoc.get(i-1));

                    lineList.get(i-1).setPoints(Arrays.asList(location, droneLoc.get(i-1)));
                }
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(MapsActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(MapsActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(MapsActivity.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        for(int i = 1; i <= 4; i++) //draw markers and lines for each drone. For now the quantity of drones is hardcoded. For the future query the database
        {
            Marker drone = mMap.addMarker(new MarkerOptions()
                    .position(droneLoc.get(i-1))
                    .title("Drone "+i)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .snippet("Status: en route \nBattery: " + ThreadLocalRandom.current().nextInt(60,80) +"%"));
            drone.setTag(i);
            droneList.add(drone);

            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(location)
                    .add(droneLoc.get(i-1));
            Polyline polyline = mMap.addPolyline(polylineOptions);
            lineList.add(polyline);
        }


    }
}