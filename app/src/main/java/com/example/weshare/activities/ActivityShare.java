package com.example.weshare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityShare extends AppCompatActivity implements LocationListener {
    public static final int GALLERY_REQUEST_CODE = 105;

    private StorageReference storageReference;
    private Uri contentUri;
    private String imageFileName;
    private String imageLink;
    private boolean isImgOk = false;

    private MaterialDatePicker.Builder builder;
    private MaterialDatePicker materialDatePicker;
    private String dateString;
    private boolean isDateOk = false;

    private FusedLocationProviderClient client;
    private LocationManager locationManager;
    private double lat, lon;

    private TextInputLayout share_EDT_meal;
    private TextInputLayout share_EDT_amount;
    private MaterialButton share_BTN_datePicker;
    private MaterialButton share_BTN_back;
    private ShapeableImageView share_IMG_food;
    private MaterialButton share_BTN_share;
    private TextInputLayout[] allFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        checkLocationPermission();
        initDate();
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
            public void onClick(View v) {
                try {
                    share();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        share_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ActivityShare.this, ActivityMenu.class);
                startActivity(intent);
            }
        });
    }

    private void initDate() {
        builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select A Date");
        materialDatePicker = builder.build();
    }

    private void share() throws IOException {
        if (isValid()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
            DatabaseReference myRef = database.getReference("meals");


            Meal meal = new Meal().
                    setName(share_EDT_meal.getEditText().getText().toString()).
                    setAmount(Integer.valueOf(share_EDT_amount.getEditText().getText().toString())).
                    setDateString(dateString).
                    setImage("").
                    setLat(lat).setLon(lon).
                    setAvailable(true);

            getLocation(meal);
            uploadImageToFirebase(contentUri, meal);
            Log.d("checkImg", "" + imageLink);
            meal.setImage("" + imageLink);
            myRef.child("meal_" + meal.getMealId()).setValue(meal);

            MyFirebaseDB.setCounter("meals_counter", Meal.getCounter());

            finish();
            Intent intent = new Intent(this, ActivityMenu.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "One or more field are invalid!", Toast.LENGTH_LONG).show();
    }

    private void getLocation(Meal meal) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, lon, 1);
        meal.setLocation(addresses.get(0).getAddressLine(0));

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
            share_IMG_food.setImageURI(contentUri);
            isImgOk = true;
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
                        imageLink = "" + uri.toString();

                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
                        DatabaseReference myRef = database.getReference("meals");
                        myRef.child("meal_"+meal.getMealId()).child("image").setValue(imageLink);
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

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(ActivityShare.this, Locale.getDefault());
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





    private void datePick() {
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Object selection) {
                //Calendar c = Calendar.getInstance();
                dateString = "" + materialDatePicker.getHeaderText();
                isDateOk = true;
            }
        });
    }

    private boolean isValid() {
        for (TextInputLayout field : allFields) {
            if (field.getError() != null || field.getEditText().getText().toString().isEmpty())
                return false;
        }
        if (!isImgOk || !isDateOk)
            return false;
        return true;
    }


    private void findViews() {
        share_EDT_meal = findViewById(R.id.share_EDT_meal);
        share_EDT_amount = findViewById(R.id.share_EDT_amount);
        share_BTN_datePicker = findViewById(R.id.share_BTN_datePicker);
        share_IMG_food = findViewById(R.id.share_IMG_food);
        share_BTN_share = findViewById(R.id.share_BTN_share);
        share_BTN_back = findViewById(R.id.share_BTN_back);
        allFields = new TextInputLayout[] {
                share_EDT_meal,
                share_EDT_amount
        };
    }
}