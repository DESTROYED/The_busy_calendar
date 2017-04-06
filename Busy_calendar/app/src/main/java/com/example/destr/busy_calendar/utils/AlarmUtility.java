package com.example.destr.busy_calendar.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.socials.FacebookSdkHelper;
import com.example.destr.busy_calendar.socials.VkSdkHelper;
import com.example.destr.busy_calendar.ui.popups.InternetConnectionErrorPopup;

public class AlarmUtility extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        if(new InternetConnection().isNetworkConnected(context)) {
            new VkSdkHelper(context).notificationBlock();
            new VkSdkHelper(context).setStatus(intent.getStringExtra("event"));
            new FacebookSdkHelper(context).notificationBlock();
            new FacebookSdkHelper(context).setStatus(intent.getStringExtra("event"));
            DataBaseUniqIdGenerator getnerator = new DataBaseUniqIdGenerator(context);
            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
            synchronized (notificationCompat) {
                notificationCompat.setSmallIcon(R.mipmap.ikonka);
                notificationCompat.setContentTitle("Cобытие началось!");
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(getnerator.notificationNewId(), notificationCompat.build());
            }
        }
        else {
            context.startActivity(new Intent(context, InternetConnectionErrorPopup.class));
        }
    }


    public void setAlarm(Context context,int id,long time,String status){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(context,AlarmUtility.class);
        intent.putExtra("event",status);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time,pendingIntent);
    }

    public void dropAlarm(Context context,int id){
        Intent intent = new Intent(context,AlarmUtility.class);
        PendingIntent canleler = PendingIntent.getBroadcast(context,id,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(canleler);
    }
}

