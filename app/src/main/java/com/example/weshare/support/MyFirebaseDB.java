package com.example.weshare.support;

import androidx.annotation.NonNull;

import com.example.weshare.objects.Meal;
import com.example.weshare.objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFirebaseDB {

    public interface CallBack_Counter {
        void dataReady(int value);
    }
    public interface CallBack_Meals {
        void dataReady(ArrayList<Meal> meals);
    }

    public interface CallBack_Users {
        void dataReady(ArrayList<User> users);
    }

    public static void getAllMeals(CallBack_Meals callBack_meals) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meals");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meal> meals = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        Meal meal = child.getValue(Meal.class);
                        meals.add(meal);
                    } catch (Exception ex) {}
                }
                if (callBack_meals != null) {
                    callBack_meals.dataReady(meals);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });
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

    public static void getUsers(CallBack_Users callBack_users) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        User user = child.getValue(User.class);
                        users.add(user);
                    } catch (Exception ex) {}
                }

                if (callBack_users != null)
                    callBack_users.dataReady(users);
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

    public static void setMealAvailability(Meal meal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weshare-70609-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("meals");
        myRef.child("meal_" + meal.getMealId()).child("available").setValue(false);
    }



}
