package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.example.weshare.R;
import com.example.weshare.callbacks.CallBack_List;
import com.example.weshare.callbacks.CallBack_Map;
import com.example.weshare.fragments.FragmentGoogleMaps;
import com.example.weshare.fragments.FragmentList;
import com.example.weshare.objects.Adapter_Meal;
import com.example.weshare.objects.Meal;
import com.example.weshare.support.MyFirebaseDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityReceive extends AppCompatActivity implements LocationListener {
    private FragmentList fragmentList;
    private FragmentGoogleMaps fragmentGoogleMaps;
    private MaterialButton receive_BTN_back;

    private LocationManager locationManager;
    private double lat, lon;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        initBTN();
        checkLocationPermission();
        initFragmentMap();
        initFragmentList();
    }

    private void initBTN() {
        receive_BTN_back = findViewById(R.id.receive_BTN_back);
        receive_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ActivityReceive.this, ActivityMenu.class);
                startActivity(intent);
            }
        });

    }

    private void initFragmentList() {
        fragmentList = new FragmentList();
        fragmentList.setActivity(this);
        fragmentList.setCallBackList(callBack_list);
        callBack_list.getMealLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.receive_frame1, fragmentList).commit();
    }

    private void initFragmentMap() {
        fragmentGoogleMaps = new FragmentGoogleMaps();
        fragmentGoogleMaps.setActivity(this);
        fragmentGoogleMaps.setCallBackMap(callBack_map);
        fragmentGoogleMaps.setLat(lat).setLon(lon);
        callBack_map.getLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.receive_frame2, fragmentGoogleMaps).commit();
    }

    CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void getMealLocation(double lat, double lon) {
            fragmentGoogleMaps.setLat(lat).setLon(lon);
            callBack_map.getLocation(lat,lon);
        }
    };

    CallBack_Map callBack_map = (lat, lon) -> {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(lat, lon);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Meal is here!"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
        });
    };


    private void checkLocationPermission() {
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(ActivityReceive.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivityReceive.this,new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION }, 100);
        }

        getLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (LocationListener) ActivityReceive.this);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lon = location.getLongitude();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(ActivityReceive.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            lat = location.getLatitude();
            lon = location.getLongitude();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


}