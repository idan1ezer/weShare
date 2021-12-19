package com.example.weshare.objects;

public class Meal {
    private String name;
    private String location;
    private int amount;
    private String dates;
    private String image;
    private double lat;
    private double lon;

    public Meal() { }

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
}
