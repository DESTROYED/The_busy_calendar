package com.example.destr.busy_calendar.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.destr.busy_calendar.constants.Constants;
import com.vk.sdk.VKSdk;

public class BusyCalendar extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!sharedPreferences.contains(Constants.Settings.THEME_CHECK)){
            sharedPreferences.edit().putString(Constants.Settings.THEME_CHECK,Constants.Settings.THEME_GREEN).apply();
        }
        VKSdk.initialize(getApplicationContext());
    }
}
