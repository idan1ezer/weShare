package com.example.weshare.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weshare.R;
import com.example.weshare.callbacks.CallBack_List;
import com.example.weshare.objects.Adapter_Meal;
import com.example.weshare.objects.Meal;
import com.example.weshare.support.MyDate;
import com.example.weshare.support.MyFirebaseDB;

import java.util.ArrayList;


public class FragmentList extends Fragment {

    private ArrayList<Meal> myMeals = new ArrayList<>();
    private RecyclerView board_LST_meals;
    //private Adapter_Meal adapter_meal;

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void dataReady(ArrayList<Meal> mealsArr) {
                updateMealsAvailability(mealsArr);
                mealsArr.removeIf(meal -> (meal.getAvailable() == false));
                Adapter_Meal adapter_meal = new Adapter_Meal(getActivity(), mealsArr);

                // Grid
                board_LST_meals.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                board_LST_meals.setHasFixedSize(true);
                board_LST_meals.setItemAnimator(new DefaultItemAnimator());
                board_LST_meals.setAdapter(adapter_meal);

                adapter_meal.setMealItemClickListener(new Adapter_Meal.MealItemClickListener() {
                    @Override
                    public void mealItemClicked(Meal meal, int pos) {
                        double lat = meal.getLat();
                        double lon = meal.getLon();
                        callBack_list.getMealLocation(lat, lon);
                    }

                    @Override
                    public void takeawayClicked(Meal meal, int pos) {
                        Log.d("takeaway1", ""+meal.getAvailable());
                        meal.setAvailable(!meal.getAvailable());
                        board_LST_meals.getAdapter().notifyItemChanged(pos);
                        MyFirebaseDB.setMealAvailability(meal);
                        Log.d("takeaway1", ""+meal.getAvailable());
                        initViews(view);
                    }
                });
            }
        };
        MyFirebaseDB.getAllMeals(callBack_meals);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateMealsAvailability(ArrayList<Meal> mealsArr) {
        for (Meal meal : mealsArr) {
            if (!MyDate.compareDate(meal.getDateString())){
                meal.setAvailable(false);
                MyFirebaseDB.setMealAvailability(meal);
            }
        }
    }

    public FragmentList setMyMeals(ArrayList<Meal> myMeals) {
        this.myMeals = myMeals;
        return this;
    }

    private void findViews(View view) {
        board_LST_meals = view.findViewById(R.id.board_LST_meals);
    }
}