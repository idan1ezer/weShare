package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weshare.R;
import com.google.android.material.button.MaterialButton;

public class ActivityStart extends AppCompatActivity {
    private MaterialButton start_BTN_register;
    private MaterialButton start_BTN_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initBTNs();

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