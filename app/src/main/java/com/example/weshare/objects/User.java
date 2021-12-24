package com.example.weshare.objects;

import java.util.ArrayList;

public class User {
    private static int counter = 0;
    private final int userID;

    private String email;
    private String password;
    private String name;
    private String phone;
    private double[] address;
    private int donations;
    private int received;

    private double lat, lon;

    public User() {
        this.userID = counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        User.counter = counter;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public double[] getAddress() {
        return address;
    }

    public User setAddress(double[] address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getDonations() {
        return donations;
    }

    public User setDonations(int donations) {
        this.donations = donations;
        return this;
    }

    public int getReceived() {
        return received;
    }

    public User setReceived(int received) {
        this.received = received;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public User setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public User setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
