package com.example.destr.busy_calendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import java.math.BigInteger;
import java.security.SecureRandom;

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
            Log.d("RETURNER", String.valueOf(returner));
            editor.putInt(Constants.OtherConstants.LAST_ID,returner+1).apply();
            return returner;
        }else {
            editor.putInt(Constants.OtherConstants.LAST_ID,0).apply();
            return 0;
        }
    }
}
