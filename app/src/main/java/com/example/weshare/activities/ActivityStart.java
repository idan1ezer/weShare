package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weshare.R;
import com.example.weshare.objects.Meal;
import com.example.weshare.objects.User;
import com.example.weshare.support.MyFirebaseDB;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityStart extends AppCompatActivity {
    private MaterialButton start_BTN_register;
    private MaterialButton start_BTN_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initBTNs();
        initCounters();

        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        //DatabaseReference myRef = database.getReference("DB_counter");
        //myRef.child("users_counter").setValue(0);
        //myRef.child("meals_counter").setValue(0);

    }

    private void initCounters() {
        MyFirebaseDB.getCounter("meals_counter", new MyFirebaseDB.CallBack_Counter() {
            @Override
            public void dataReady(int value) {
                Meal.setCounter(value);
            }
        });
        MyFirebaseDB.getCounter("users_counter", new MyFirebaseDB.CallBack_Counter() {
            @Override
            public void dataReady(int value) {
                User.setCounter(value+1);
            }
        });
    }

    private void initBTNs() {
        start_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { register();}
        });

        start_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { login();}
        });
    }

    private void login() {
        finish();
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
    }

    private void register() {
        finish();
        Intent intent = new Intent(this, ActivityRegistration.class);
        startActivity(intent);
    }


    private void findViews() {
        start_BTN_register = findViewById(R.id.start_BTN_register);
        start_BTN_login = findViewById(R.id.start_BTN_login);
    }
}