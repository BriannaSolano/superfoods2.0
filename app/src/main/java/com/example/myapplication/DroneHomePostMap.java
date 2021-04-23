package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DroneHomePostMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> droneList = new ArrayList<Marker>();
    private ArrayList<LatLng> droneLoc = new ArrayList<LatLng>();
    private ArrayList<Polyline> lineList = new ArrayList<Polyline>();
    private LatLng location= new LatLng(40.52243311455501, -74.45817069345065);
    private LatLng destLoc = new LatLng(40.49288922920576, -74.4230543395916);


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
                droneLoc.set(0, new LatLng((double) snapshot.child("Drone"+4).child("lat").getValue(), (double) snapshot.child("Drone"+4).child("long").getValue()));
                droneList.get(0).setPosition(droneLoc.get(0));

                lineList.get(0).setPoints(Arrays.asList(location, droneLoc.get(0),destLoc));
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.50884792517122, -74.43638237354567)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(DroneHomePostMap.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(DroneHomePostMap.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(DroneHomePostMap.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        Marker drone = mMap.addMarker(new MarkerOptions()
                .position(droneLoc.get(0))
                .title("Drone "+4)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .snippet("Status: en route \nEstimate Time: 7 minute"));
        drone.setTag(0);
        droneList.add(drone);


        Marker dest = mMap.addMarker(new MarkerOptions()
                .position(destLoc)
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        dest.setTag(0);


        PolylineOptions polylineOptions = new PolylineOptions()
                .add(location)
                .add(droneLoc.get(0))
                .add(destLoc);
        Polyline polyline = mMap.addPolyline(polylineOptions);
        lineList.add(polyline);


    }
}