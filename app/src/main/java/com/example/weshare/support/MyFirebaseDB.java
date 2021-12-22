package com.example.weshare.support;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFirebaseDB {

    public interface CallBack_Counter {
        void dataReady(int value);
    }

    public static void getCounter(String type, CallBack_Counter callBack_counter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("DB_counter");
        myRef.child(type).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = Integer.valueOf(snapshot.getValue().toString());
                if (callBack_counter != null)
                    callBack_counter.dataReady(counter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });
    }

    public static void setCounter(String type, int value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("DB_counter");
        myRef.child(type).setValue(value);
    }



}
