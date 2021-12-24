package com.example.weshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weshare.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityMenu extends AppCompatActivity {
    private MaterialTextView menu_TXT_hello;
    private MaterialButton menu_BTN_share;
    private MaterialButton menu_BTN_receive;
    private MaterialTextView menu_TXT_verify;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = fAuth.getCurrentUser();

        //menu_TXT_hello.setText("Hello, " + user.getDisplayName());

        //check for null shit
        findViews();
        //if (user.isEmailVerified())
            initBTNs();
        //else
            //notVerified(user);
    }


    private void initBTNs() {
        menu_BTN_share.setEnabled(true);
        menu_BTN_receive.setEnabled(true);
        menu_TXT_verify.setVisibility(View.INVISIBLE);

        menu_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { share();}
        });

        menu_BTN_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { receive();}
        });
    }

    private void notVerified(FirebaseUser user) {
        menu_BTN_share.setEnabled(false);
        menu_BTN_receive.setEnabled(false);
        menu_TXT_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Toast.makeText(ActivityMenu.this, "Verification mail has been sent!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityMenu.this, "Error!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        finish();
        Intent intent = new Intent(this, ActivityStart.class);
        startActivity(intent);
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
        menu_TXT_hello = findViewById(R.id.menu_TXT_hello);
        menu_BTN_share = findViewById(R.id.menu_BTN_share);
        menu_BTN_receive = findViewById(R.id.menu_BTN_receive);
        menu_TXT_verify = findViewById(R.id.menu_TXT_verify);
    }
}