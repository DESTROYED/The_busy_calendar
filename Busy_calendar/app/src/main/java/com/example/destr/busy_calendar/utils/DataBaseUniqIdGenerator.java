package com.example.destr.busy_calendar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.destr.busy_calendar.constants.Constants;

/**
 * Created by Sinyukovich on 01.03.2017.
 */
public class DataBaseUniqIdGenerator {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public DataBaseUniqIdGenerator(Context context){
         sharedPreferences= context.getSharedPreferences(Constants.OtherConstants.LAST_ID,Context.MODE_PRIVATE);
         editor= sharedPreferences.edit();
    }

    public int generateNewId(){
        if(sharedPreferences.contains(Constants.OtherConstants.LAST_ID)){
            int returner= sharedPreferences.getInt(Constants.OtherConstants.LAST_ID,0);
            editor.putInt(Constants.OtherConstants.LAST_ID,returner+1).apply();
            return returner;
        }else {
            editor.putInt(Constants.OtherConstants.LAST_ID,0).apply();
            return 0;
        }
    }

    public int notificationNewId(){
        if(sharedPreferences.contains(Constants.OtherConstants.NOTIFICATION_ID)){
            int returner= sharedPreferences.getInt(Constants.OtherConstants.NOTIFICATION_ID,0);
            if(returner<20000){
            editor.putInt(Constants.OtherConstants.NOTIFICATION_ID,returner+1).apply();
            }
            else {
                editor.putInt(Constants.OtherConstants.NOTIFICATION_ID,0).apply();
            }
            return returner;
        }else {
            editor.putInt(Constants.OtherConstants.NOTIFICATION_ID,0).apply();
            return 0;
        }
    }
}
