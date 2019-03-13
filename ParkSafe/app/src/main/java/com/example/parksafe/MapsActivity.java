package com.example.parksafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnInfoWindowClickListener, InfoWindowAdapter {
    private Button button;
    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private FusedLocationProviderClient fusedLocationClient;
    private FloatingActionButton filterCameraButton, filterAlldayButton;
    ArrayList<Circle> ParkAreas;
    boolean isChecked=false;
    boolean isChecked_=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Choose camera to filter areas
        filterCameraButton = (FloatingActionButton) findViewById(R.id.camera);
        filterAlldayButton = (FloatingActionButton) findViewById(R.id.allday);
        filterCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked){
                    filterCameraButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF008577"))); //set Fab background color
                    ParkAreas.get(0).setFillColor(Color.TRANSPARENT);
                    ParkAreas.get(0).setStrokeColor(Color.TRANSPARENT);
                    ParkAreas.get(1).setFillColor(Color.TRANSPARENT);
                    ParkAreas.get(1).setStrokeColor(Color.TRANSPARENT);
                    isChecked=false;
                }
                else{
                    filterCameraButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))); //set Fab background color
                    ParkAreas.get(0).setFillColor(Color.RED);
                    ParkAreas.get(0).setStrokeColor(Color.RED);
                    ParkAreas.get(1).setFillColor(Color.GREEN);
                    ParkAreas.get(1).setStrokeColor(Color.GREEN);
                    isChecked=true;

                }
            }
        });

        filterAlldayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked_){
                    filterAlldayButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF008577"))); //set Fab background color
                    ParkAreas.get(2).setFillColor(Color.TRANSPARENT);
                    ParkAreas.get(2).setStrokeColor(Color.TRANSPARENT);
                    ParkAreas.get(3).setFillColor(Color.TRANSPARENT);
                    ParkAreas.get(3).setStrokeColor(Color.TRANSPARENT);
                    isChecked_=false;
                }
                else{
                    filterAlldayButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))); //set Fab background color
                    ParkAreas.get(2).setFillColor(Color.RED);
                    ParkAreas.get(2).setStrokeColor(Color.RED);
                    ParkAreas.get(3).setFillColor(Color.GREEN);
                    ParkAreas.get(3).setStrokeColor(Color.GREEN);
                    isChecked_=true;

                }
            }
        });

//        button = findViewById(R.id.btn_detail_one);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                opeAactivityReviewList();
//            }
//        });

    }

    // determine the level of accuracy for location requests, will need this when dealing with location updates
    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ParkAreas = new ArrayList<Circle>();


        // Add circles to the map from json file
        try {
            JSONArray dataarray = new JSONArray(loadJSONFromAsset("markerdata.json"));
            for (int i = 0; i < dataarray.length();i++){
                double x = dataarray.getJSONObject(i).getDouble("latitude");
                double y = dataarray.getJSONObject(i).getDouble("longitude");
                LatLng latlng = new LatLng(x, y);
                ParkAreas.add(mMap.addCircle(new CircleOptions().center(latlng).radius(10)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set varying colours
        ParkAreas.get(0).setFillColor(Color.RED);
        ParkAreas.get(0).setStrokeColor(Color.RED);
        ParkAreas.get(1).setFillColor(Color.GREEN);
        ParkAreas.get(1).setStrokeColor(Color.GREEN);
        ParkAreas.get(2).setFillColor(Color.RED);
        ParkAreas.get(2).setStrokeColor(Color.RED);
        ParkAreas.get(3).setFillColor(Color.GREEN);
        ParkAreas.get(3).setStrokeColor(Color.GREEN);
        ParkAreas.get(4).setFillColor(Color.GREEN);
        ParkAreas.get(4).setStrokeColor(Color.GREEN);

        //Set info window and click listener
        //mMap.setInfoWindowAdapter(new com.example.parksafe.CustomInfoWindowAdapter(MapsActivity.this));
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Enable my current location
            mMap.setMyLocationEnabled(true);
            // Get last location (it's almost equivalent to getting the current location of the device) and do something
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                // Get lat and long of current location and move camera to this position when app is opened
                                LatLng initLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initLocation, 15));
//                                Log.e("LOCATION***********", initLoc.toString());
                            }
                        }
                    });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        System.out.println("info window has been clicked1");
        opeAactivityReviewList();
    }


    @Override
    public View getInfoWindow(Marker marker) {
        View mWindow = LayoutInflater.from(getApplicationContext()).inflate(R.layout.info_window1, null);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    public void opeAactivityReviewList(){
        Intent intent = new Intent(this, Activity_ReviewList.class);
        startActivity(intent);

    }
}
