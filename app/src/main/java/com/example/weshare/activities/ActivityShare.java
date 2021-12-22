package com.example.weshare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.weshare.R;
import com.example.weshare.objects.Meal;
import com.example.weshare.objects.User;
import com.example.weshare.support.MyFirebaseDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityShare extends AppCompatActivity {
    public static final int GALLERY_REQUEST_CODE = 105;

    private StorageReference storageReference;
    private String currentPhotoPath;

    private Uri contentUri;
    private String imageFileName;

    private LocationManager locationManager;
    private FusedLocationProviderClient client;
    double lat, lon;

    private TextInputLayout share_EDT_meal;
    private TextInputLayout share_EDT_amount;
    private MaterialButton share_BTN_datePicker;
    private ShapeableImageView share_IMG_food;
    private MaterialButton share_BTN_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        checkLocationPermission();
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

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("meals");

        Meal meal = new Meal().
                setName(share_EDT_meal.getEditText().getText().toString()).
                setAmount(Integer.valueOf(share_EDT_amount.getEditText().getText().toString())).
                setDates("").
                setImage("").
                setLat(lat).setLon(lon).
                setAvailable(true);

        myRef.child("meal_"+meal.getMealId()).setValue(meal);
        uploadImageToFirebase(contentUri, meal);

        MyFirebaseDB.setCounter("meals_counter", User.getCounter()+1);

        finish();
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
    }








    private void imgUpload() {
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            contentUri = data.getData();
            //Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
            share_IMG_food.setImageURI(contentUri);
        }
    }

    private void uploadImageToFirebase(Uri contentUri, Meal meal) {
        imageFileName = "meal_" + meal.getMealId() + "." + getFileExt(contentUri);
        final StorageReference image = storageReference.child("pictures/" + imageFileName);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }
                });

                Toast.makeText(ActivityShare.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityShare.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private void checkLocationPermission() {
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(ActivityShare.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivityShare.this,new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION }, 100);
        }

        getLocation();
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (LocationListener) ActivityShare.this);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lon = location.getLongitude();

        }catch (Exception e){
            e.printStackTrace();
        }
    }








    private void datePick() {

    }

    private void findViews() {
        share_EDT_meal = findViewById(R.id.share_EDT_meal);
        share_EDT_amount = findViewById(R.id.share_EDT_amount);
        share_BTN_datePicker = findViewById(R.id.share_BTN_datePicker);
        share_IMG_food = findViewById(R.id.share_IMG_food);
        share_BTN_share = findViewById(R.id.share_BTN_share);
    }
}