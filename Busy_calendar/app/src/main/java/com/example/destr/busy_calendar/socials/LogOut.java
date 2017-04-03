package com.example.destr.busy_calendar.socials;

import android.content.Context;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.http.MyHttpClient;


public class LogOut {
    private MyHttpClient mMyHttpClient=new MyHttpClient();
    public void vk(Context mContext){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(Constants.UrlConstants.VK_LOGOUT,Constants.OtherConstants.GET_METHOD);
            }
        }).start();
    }

    public void facebook(Context mContext){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMyHttpClient.method(Constants.UrlConstants.FACEBOOK_LOGOUT,Constants.OtherConstants.GET_METHOD);
            }
        }).start();
    }
}
