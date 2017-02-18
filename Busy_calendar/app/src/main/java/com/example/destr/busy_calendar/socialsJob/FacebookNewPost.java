package com.example.destr.busy_calendar.socialsJob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.destr.busy_calendar.http.MyHttpClient;
import com.example.destr.busy_calendar.constants.Constants;
public class FacebookNewPost {

    private MyHttpClient mMyHttpClient=new MyHttpClient();
    public void getResponse(Context mContext){
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String facebookToken = logTest.getString(Constants.TokenJob.FACEBOOK_TOKEN, Constants.OtherConstants.NULL_STRING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(String.format(Constants.UrlConstants.FACEBOOK_POST_SET,facebookToken),Constants.OtherConstants.POST_METHOD);
        }
    }).start();
}
}
