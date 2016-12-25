package com.example.destr.busy_calendar.socialsJob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.json.JsonFromUrl;
import com.example.destr.busy_calendar.json.VkJsonParse;

public class VkSetStatus {

    public void getResponse(Context mContext){
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String vkToken = logTest.getString(Constants.TokenJob.VK_TOKEN, Constants.OtherConstants.NULL_STRING);
        final JsonFromUrl mJsonFromUrl = new JsonFromUrl();
        final VkJsonParse vkJsonParse= new VkJsonParse();

        new Thread(new Runnable() {
                @Override
                public void run() {
                    /*try {
                        //final String setStatus = vkJsonParse.parseResponse(mJsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.VK_STATUS_SET,vkToken)));
                        //Log.d("RESPONSE",setStatus);
                    } catch (JSONException pE) {
                        pE.printStackTrace();
                    }*/
                }
            }).start();
        }
    }


