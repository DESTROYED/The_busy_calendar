package com.example.destr.busy_calendar.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.example.destr.busy_calendar.activities.MainActivity;
import com.example.destr.busy_calendar.constants.Constants;

public class AlarmUtility extends BroadcastReceiver {
    //TODO add database view
    //TODO FIX!

    @Override
    public void onReceive(Context context, Intent intent){
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl= pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"YOUR TAG");
        wl.acquire();
        wl.release();
    }

    public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, MainActivity.class);
        intent.putExtra(Constants.OtherConstants.ONE_TIME, Boolean.TRUE);
        PendingIntent pi= PendingIntent.getBroadcast(context,0, intent,0);
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pi);
    }
}

