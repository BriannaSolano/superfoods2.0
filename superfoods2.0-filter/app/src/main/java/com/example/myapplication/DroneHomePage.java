package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class DroneHomePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private EditText address;
    private EditText city;
    private Button confirm;
    TextView restLatLong;
    TextView deliverLatLong;
    TextView milesAway;
    TextView eligibility;

    double[] userCoordinates = new double[2];
    double[] restaurantCoordinates = {40.522425, -74.4581546};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_home_page);

        firebaseAuth = FirebaseAuth.getInstance();

        address = (EditText) findViewById(R.id.drone_address_input);
        city = (EditText) findViewById(R.id.drone_city_input);
        logout = (Button) findViewById(R.id.button23);
        confirm = (Button) findViewById(R.id.drone_address_input_confirmation);
        restLatLong = (TextView)findViewById(R.id.restLatLong);
        deliverLatLong = (TextView)findViewById(R.id.deliverLatLong);
        milesAway = (TextView)findViewById(R.id.milesAway);
        eligibility = (TextView)findViewById(R.id.eligibility);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String fullAddress = buildAddress(address.getText().toString(), city.getText().toString());
                            userCoordinates = getCoordinate(fullAddress);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                restLatLong.setText(restaurantCoordinates[0]+", "+restaurantCoordinates[1]);
                deliverLatLong.setText(userCoordinates[0]+ ", "+userCoordinates[1]);
                double dist = calculateDistance(restaurantCoordinates[0], userCoordinates[0], restaurantCoordinates[1], userCoordinates[1]);
                DecimalFormat tresDecimals = new DecimalFormat("#.###");
                milesAway.setText(String.valueOf(tresDecimals.format(dist))+ " miles");
                boolean dispatch = canDispatch(dist);
                if(dispatch){
                    eligibility.setText("Your order is eligible for drone delivery.");
                }
                else{
                    eligibility.setText("Your order is eligible for driven delivery.");
                }
                confirm.setText(String.valueOf(dispatch));

            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(DroneHomePage.this, MainActivity.class));
            }
        });
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static double[] getCoordinate(String address) throws JSONException, IOException {
        String key = "AIzaSyAdG6u7YLRCGRtNnkEDDuEWKmJD7Srrz10";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + key;
        //String url = "http://localhost/test/temp/SoftEng/json.json";
        JSONObject json = readJsonFromUrl(url);
        JSONObject location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
                .getJSONObject("location");
        double lat = location.getDouble("lat");
        double lng = location.getDouble("lng");
        //System.out.println("lat: " + lat + " lng: " + lng);

        double[] out = {lat, lng};
        return out;
    }

    private static String buildAddress(String address, String city) {
        String out = address + ", " + city + ", NJ";
        out = out.replace(" ", "+");
        return out;
    }

    public static double calculateDistance(double lat1, double lat2, double lon1, double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }

    private static boolean canDispatch(double distance) {

        //Distance Checking
        if(distance > 8.143) {
            return false;
        }

//    //Weather checking
//    Int wind; => //online data scrub
//    Boolean Precipitation; => //Online data scrub 1 for yes 0 for no
//            if(precipitation ==1||wind >10)
//            return false;
//
//    //Weight check
//    Double weight = total weight of order
//    if(weight>5.5 kg or 12.1254lb)
//        return false;
//am i in?
        return true;
    }

}