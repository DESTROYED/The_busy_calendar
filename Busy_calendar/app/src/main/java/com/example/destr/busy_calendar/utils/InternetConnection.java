package com.example.destr.busy_calendar.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
