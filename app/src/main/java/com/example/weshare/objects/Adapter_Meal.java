package com.example.weshare.objects;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weshare.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;

public class Adapter_Meal extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Meal> meals = new ArrayList<>();
    private MealMapClickListener mealMapClickListener;

    public Adapter_Meal(FragmentActivity activity, ArrayList<Meal> meals) {
        this.activity = activity;
        this.meals = meals;
        Log.d("AdapterM",""+meals.size());
    }

    public Adapter_Meal setMealMapClickListener(MealMapClickListener mealMapClickListener) {
        this.mealMapClickListener = mealMapClickListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_meals, viewGroup, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MealViewHolder mealViewHolder = (MealViewHolder) holder;
        Meal meal = getItem(position);

        mealViewHolder.list_LBL_meal.setText(""+meal.getName());
        mealViewHolder.list_LBL_amount.setText(""+meal.getAmount());
        mealViewHolder.list_LBL_location.setText(""+meal.getLocation());
        mealViewHolder.list_LBL_dates.setText(""+meal.getDates());
        Glide
                .with(activity)
                .load(meal.getImage())
                .into(mealViewHolder.list_IMG_image);
    }

    @Override
    public int getItemCount() { return meals.size(); }

    private Meal getItem(int position) {
        return meals.get(position);
    }



    public interface MealMapClickListener {
        void mealMapClicked(Meal meal, int pos);
    }



    public class MealViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView list_LBL_meal;
        public MaterialTextView list_LBL_location;
        public MaterialTextView list_LBL_amount;
        public MaterialTextView list_LBL_dates;
        public ShapeableImageView list_IMG_image;


        public MealViewHolder(final View itemview) {
            super(itemview);
            this.list_LBL_meal = itemview.findViewById(R.id.list_LBL_meal);
            this.list_LBL_location = itemview.findViewById(R.id.list_LBL_location);
            this.list_LBL_amount = itemview.findViewById(R.id.list_LBL_amount);
            this.list_LBL_dates = itemview.findViewById(R.id.list_LBL_dates);
            this.list_IMG_image = itemview.findViewById(R.id.list_IMG_image);


            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mealMapClickListener.mealMapClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }



    }

}
