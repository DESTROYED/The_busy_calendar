package com.example.destr.busy_calendar.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.destr.busy_calendar.R;

public class AlarmUtility extends BroadcastReceiver {
    //TODO add database view

    @Override
    public void onReceive(Context context, Intent intent){
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        synchronized (notificationCompat){
            notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
            // TODO: 17.03.2017 Make notification about status with message from DBase
            notificationCompat.setContentTitle("Важное событие");
            notificationCompat.setContentText("Busy Calendar Оповещение");
            notificationCompat.setStyle(inboxStyle);
            NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notificationCompat.build());
        }
    }

    public void setAlarm(Context context,int id,long time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(context,AlarmUtility.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }
    public void dropAlarm(Context context,int id){
        Intent intent = new Intent(context,AlarmUtility.class);
        PendingIntent canleler = PendingIntent.getBroadcast(context,id,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(canleler);
    }
}

