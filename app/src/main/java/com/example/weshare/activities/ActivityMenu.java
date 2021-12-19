package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weshare.R;
import com.google.android.material.button.MaterialButton;

public class ActivityMenu extends AppCompatActivity {
    private MaterialButton menu_BTN_share;
    private MaterialButton menu_BTN_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        initBTNs();

    }

    private void initBTNs() {
        menu_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { share();}
        });

        menu_BTN_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { receive();}
        });
    }


    private void share() {
        finish();
        Intent intent = new Intent(this, ActivityShare.class);
        startActivity(intent);
    }

    private void receive() {
        finish();
        Intent intent = new Intent(this, ActivityReceive.class);
        startActivity(intent);
    }

    private void findViews() {
        menu_BTN_share = findViewById(R.id.menu_BTN_share);
        menu_BTN_receive = findViewById(R.id.menu_BTN_receive);
    }
}