package com.example.weshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weshare.R;
import com.example.weshare.objects.User;
import com.example.weshare.support.Validator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegistration extends AppCompatActivity {

    private TextInputLayout reg_EDT_username;
    private TextInputLayout reg_EDT_password;
    private TextInputLayout reg_EDT_name;
    private TextInputLayout reg_EDT_phone;
    private TextInputLayout reg_EDT_email;

    private TextInputLayout[] allFields;
    private MaterialButton reg_BTN_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViews();
        initBTNs();

        checkFormValidation();

    }

    private boolean isValid() {
        for (TextInputLayout field : allFields) {
            if (field.getError() != null || field.getEditText().getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    private void checkFormValidation() {
        Validator.Builder
                .make(reg_EDT_username)
                .addWatcher(new Validator.Watcher_Username("Invalid username"))
                .build();

        Validator.Builder
                .make(reg_EDT_password)
                .addWatcher(new Validator.Watcher_Password("Invalid password"))
                .build();

        Validator.Builder
                .make(reg_EDT_phone)
                .addWatcher(new Validator.Watcher_Phone("Invalid phone number"))
                .build();

        Validator.Builder
                .make(reg_EDT_email)
                .addWatcher(new Validator.Watcher_Email("Invalid email"))
                .build();
    }

    private void initBTNs() {
        reg_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { register();}
        });
    }

    private void register() {
        if (isValid()) {
            addToDB();
            Toast.makeText(this, "Registration completed!", Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(this, ActivityStart.class);
            startActivity(intent);
        }

        else
            Toast.makeText(this, "One or more field are invalid!", Toast.LENGTH_LONG).show();
    }

    private void addToDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("users");

        User user = new User().
                setUsername(reg_EDT_username.getEditText().getText().toString()).
                setPassword(reg_EDT_password.getEditText().getText().toString()).
                setName(reg_EDT_name.getEditText().getText().toString()).
                setPhone(reg_EDT_phone.getEditText().getText().toString()).
                setEmail(reg_EDT_email.getEditText().getText().toString()).
                setDonations(0).setReceived(0);

        myRef.child(user.getUsername()).setValue(user);
    }

    private void findViews() {
        reg_EDT_username = findViewById(R.id.reg_EDT_username);
        reg_EDT_password = findViewById(R.id.reg_EDT_password);
        reg_EDT_name = findViewById(R.id.reg_EDT_name);
        reg_EDT_phone = findViewById(R.id.reg_EDT_phone);
        reg_EDT_email = findViewById(R.id.reg_EDT_email);
        reg_BTN_register = findViewById(R.id.reg_BTN_register);

        allFields = new TextInputLayout[] {
                reg_EDT_username,
                reg_EDT_password,
                reg_EDT_name,
                reg_EDT_phone,
                reg_EDT_email
        };
    }
}