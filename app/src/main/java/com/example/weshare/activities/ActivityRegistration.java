package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weshare.R;
import com.google.android.material.button.MaterialButton;

public class ActivityRegistration extends AppCompatActivity {
    private MaterialButton reg_BTN_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViews();
        initBTNs();

    }

    private void initBTNs() {
        reg_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { register();}
        });
    }

    private void register() {
        // register

        Toast.makeText(this, "Registration completed!", Toast.LENGTH_LONG).show();
        finish();
        Intent intent = new Intent(this, ActivityStart.class);
        startActivity(intent);
    }

    private void findViews() {
        reg_BTN_register = findViewById(R.id.reg_BTN_register);
    }
}