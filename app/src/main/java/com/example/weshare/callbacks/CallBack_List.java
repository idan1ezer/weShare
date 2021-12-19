package com.example.weshare.callbacks;

import com.example.weshare.objects.Meal;

import java.util.ArrayList;

public interface CallBack_List {
    ArrayList<Meal> getMeals();
    void getMealLocation(double lat, double lon);
}
