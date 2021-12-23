package com.example.weshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weshare.R;
import com.example.weshare.objects.Meal;
import com.example.weshare.objects.User;
import com.example.weshare.support.MyFirebaseDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class ActivityStart extends AppCompatActivity {
    private TextInputLayout start_EDT_email;
    private TextInputLayout start_EDT_password;
    private MaterialButton start_BTN_register;
    private MaterialButton start_BTN_login;
    private MaterialTextView start_TXT_forgot;

    private FirebaseAuth fAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        fAuth = FirebaseAuth.getInstance();

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
                Log.d("counter1", "meals = " +value);
            }
        });
        MyFirebaseDB.getCounter("users_counter", new MyFirebaseDB.CallBack_Counter() {
            @Override
            public void dataReady(int value) {
                User.setCounter(value);
                Log.d("counter1", "users = " +value);
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

        start_TXT_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { forgotPassword(v);}
        });
    }


    private void login() {
        String email, password;
        email = start_EDT_email.getEditText().getText().toString().trim();
        password = start_EDT_password.getEditText().getText().toString().trim();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ActivityStart.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ActivityStart.this, ActivityMenu.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ActivityStart.this, "Email or Password are incorrect!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void register() {
        finish();
        Intent intent = new Intent(this, ActivityRegistration.class);
        startActivity(intent);
    }

    private void forgotPassword(View v) {
        EditText resetMail = new EditText(v.getContext());
        AlertDialog.Builder passwordResetDiaglog = new AlertDialog.Builder(v.getContext());
        passwordResetDiaglog.setTitle("Reset Password");
        passwordResetDiaglog.setMessage("Enter your email to receive a link for password reset");
        passwordResetDiaglog.setView(resetMail);

        passwordResetDiaglog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Toast.makeText(ActivityStart.this, "Reset link sent to your mail!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityStart.this, "Error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        passwordResetDiaglog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // to do
            }
        });

        passwordResetDiaglog.create().show();
    }


    private void findViews() {
        start_EDT_email = findViewById(R.id.start_EDT_email);
        start_EDT_password = findViewById(R.id.start_EDT_password);
        start_BTN_register = findViewById(R.id.start_BTN_register);
        start_BTN_login = findViewById(R.id.start_BTN_login);
        start_TXT_forgot = findViewById(R.id.start_TXT_forgot);
    }
}