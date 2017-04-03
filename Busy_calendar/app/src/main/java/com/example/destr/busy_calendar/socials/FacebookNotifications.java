package com.example.destr.busy_calendar.socials;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.http.MyHttpClient;

public class FacebookNotifications {

    private MyHttpClient mMyHttpClient=new MyHttpClient();
    public void block(Context mContext){
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String facebookToken = logTest.getString(Constants.TokenJob.FACEBOOK_TOKEN, Constants.OtherConstants.NULL_STRING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(Constants.UrlConstants.FACEBOOK_NOTIFICATIONS_BLOCK,Constants.OtherConstants.POST_METHOD);
        }
    }).start();
    }
    public void unLock(Context mContext){
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String facebookToken = logTest.getString(Constants.TokenJob.FACEBOOK_TOKEN, Constants.OtherConstants.NULL_STRING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(Constants.UrlConstants.FACEBOOK_NOTIFICATIONS_UNLOCK,Constants.OtherConstants.POST_METHOD);
        }
    }).start();
}
}
