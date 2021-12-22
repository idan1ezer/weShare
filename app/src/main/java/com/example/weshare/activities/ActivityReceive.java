package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.weshare.R;
import com.example.weshare.callbacks.CallBack_List;
import com.example.weshare.callbacks.CallBack_Map;
import com.example.weshare.fragments.FragmentGoogleMaps;
import com.example.weshare.fragments.FragmentList;
import com.example.weshare.objects.Meal;
import com.example.weshare.support.MyFirebaseDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ActivityReceive extends AppCompatActivity {
    private FragmentList fragmentList;
    private FragmentGoogleMaps fragmentGoogleMaps;

    //private double lat;
    //private double lon;
    //private int score;

    //private MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        initFragmentMap();
        initFragmentList();
    }

    private void initFragmentList() {
        fragmentList = new FragmentList();
        fragmentList.setActivity(this);
        fragmentList.setCallBackList(callBack_list);
        //callBack_list.getMealLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.receive_frame1, fragmentList).commit();
    }

    private void initFragmentMap() {
        fragmentGoogleMaps = new FragmentGoogleMaps();
        fragmentGoogleMaps.setActivity(this);
        fragmentGoogleMaps.setCallBackMap(callBack_map);
        //fragmentGoogleMaps.setLat(lat).setLon(lon);
        //callBack_map.getLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.receive_frame2, fragmentGoogleMaps).commit();
    }

    CallBack_List callBack_list = new CallBack_List() {
        @Override
        public ArrayList<Meal> getMeals() {
            ArrayList<Meal> meals = new ArrayList<>();
            MyFirebaseDB.getAllMeals(new MyFirebaseDB.CallBack_Meals() {
                @Override
                public void dataReady(ArrayList<Meal> mealsArr) {
                    for (Meal meal : mealsArr) {
                        meals.add(meal);
                    }
                };
            });
            return meals;
        }

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


}