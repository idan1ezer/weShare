package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weshare.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class ActivityShare extends AppCompatActivity {
    private MaterialButton share_BTN_datePicker;
    private ShapeableImageView share_IMG_food;
    private MaterialButton share_BTN_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        findViews();
        initBTNs();


    }

    private void initBTNs() {
        share_BTN_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { datePick();}
        });

        share_IMG_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { imgUpload();}
        });

        share_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { share();}
        });

    }

    private void share() {
        finish();
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
    }

    private void imgUpload() {

    }

    private void datePick() {

    }

    private void findViews() {
        share_BTN_datePicker = findViewById(R.id.share_BTN_datePicker);
        share_IMG_food = findViewById(R.id.share_IMG_food);
        share_BTN_share = findViewById(R.id.share_BTN_share);
    }
}