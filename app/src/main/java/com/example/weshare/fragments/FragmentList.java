package com.example.weshare.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weshare.R;
import com.example.weshare.callbacks.CallBack_List;
import com.example.weshare.objects.Adapter_Meal;
import com.example.weshare.objects.Meal;
import com.example.weshare.support.MyFirebaseDB;

import java.util.ArrayList;


public class FragmentList extends Fragment {

    private ArrayList<Meal> meals = new ArrayList<>();
    private Adapter_Meal adapter_meal;
    private RecyclerView board_LST_meals;

    private AppCompatActivity activity;
    private CallBack_List callBack_list;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews(view);

        return view;
    }


    private void initViews(View view) {
        MyFirebaseDB.CallBack_Meals callBack_meals = new MyFirebaseDB.CallBack_Meals() {
            @Override
            public void dataReady(ArrayList<Meal> mealsArr) {
                adapter_meal = new Adapter_Meal(getActivity(), mealsArr);
            }
        };
        MyFirebaseDB.getAllMeals(callBack_meals);



        // Grid
        board_LST_meals.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        board_LST_meals.setHasFixedSize(true);
        board_LST_meals.setItemAnimator(new DefaultItemAnimator());
        board_LST_meals.setAdapter(adapter_meal);

        if (adapter_meal != null) {
            adapter_meal.setMealMapClickListener(new Adapter_Meal.MealMapClickListener() {
                @Override
                public void mealMapClicked(Meal meal, int pos) {
                    double lat = meal.getLat();
                    double lon = meal.getLon();
                    callBack_list.getMealLocation(lat, lon);
                }
            });
        }
    }

    private void findViews(View view) {
        board_LST_meals = view.findViewById(R.id.board_LST_meals);
    }
}