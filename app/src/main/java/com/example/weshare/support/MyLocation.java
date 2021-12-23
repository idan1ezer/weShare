package com.example.weshare.support;

public class MyLocation {

    public interface CallBack_Location {
        void dataReady(double lat, double lon);
    }

    public static void getMyLocation(MyLocation.CallBack_Location callBack_location) {

    }
}
