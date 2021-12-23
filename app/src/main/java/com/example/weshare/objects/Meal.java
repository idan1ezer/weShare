package com.example.weshare.objects;

import android.net.Uri;

public class Meal {
    private static int counter = 0;
    private final int mealId;

    private String name;
    private String location;
    private int amount;
    private String dates;
    private String image;
    private double lat;
    private double lon;
    private boolean isAvailable;


    public Meal() {
        this.mealId = counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Meal.counter = counter;
    }

    public int getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public Meal setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Meal setLocation(String location) {
        this.location = location;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Meal setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getDates() {
        return dates;
    }

    public Meal setDates(String dates) {
        this.dates = dates;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Meal setImage(String image) {
        this.image = image;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Meal setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Meal setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Meal setAvailable(boolean available) {
        isAvailable = available;
        return this;
    }

    @Override
    public String toString() {
        return "Meal num " + mealId + "is " + name + ", amount of " + amount;
    }
}
