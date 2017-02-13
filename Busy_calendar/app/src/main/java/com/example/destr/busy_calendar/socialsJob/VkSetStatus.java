package com.example.destr.busy_calendar.socialsJob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.http.MyHttpClient;

public class VkSetStatus {

    private MyHttpClient mMyHttpClient=new MyHttpClient();
    public void getResponse(Context mContext){
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String vkToken = logTest.getString(Constants.TokenJob.VK_TOKEN, Constants.OtherConstants.NULL_STRING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(String.format(Constants.UrlConstants.VK_STATUS_SET,vkToken),Constants.OtherConstants.GET_METHOD);
                }
            }).start();
        }
    }


