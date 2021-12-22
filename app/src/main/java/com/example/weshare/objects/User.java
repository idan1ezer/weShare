package com.example.weshare.objects;

public class User {
    private String username;
    private String name;
    private double[] address;
    private String phone;
    private String email;
    private String password;
    private int donations;
    private int received;

    public User() { }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
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
}
