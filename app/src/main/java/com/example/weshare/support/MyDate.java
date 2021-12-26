package com.example.weshare.support;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class MyDate {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean compareDate(String date) {
        String[] newDate = date.replace(",", "").split("\\s+");
        int day = Integer.valueOf(newDate[1]);
        int month = monthConverter(newDate[0]);
        int year = Integer.valueOf(newDate[2]);

        LocalDate today = LocalDate.now();
        if (year > today.getYear())
            return true;
        else if (year == today.getYear()) {
            if (month > today.getMonthValue())
                return true;
            else if (month == today.getMonthValue()) {
                if (day >= today.getDayOfMonth())
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }

    private static int monthConverter(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return -1;

        }
    }
}
