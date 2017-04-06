package com.example.destr.busy_calendar.utils;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class BusyCalendar extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
