package com.example.destr.busy_calendar.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.socials.VkSetStatus;

public class EndEventReciever extends BroadcastReceiver {
    private VkSetStatus vkSetStatus;

    @Override
    public void onReceive(Context context, Intent intent){
        DataBaseUniqIdGenerator getnerator = new DataBaseUniqIdGenerator(context);
        vkSetStatus=new VkSetStatus();
        vkSetStatus.getResponse(context," ");
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
        synchronized (notificationCompat){
            notificationCompat.setSmallIcon(R.mipmap.ikonka);
                notificationCompat.setContentTitle("Cобытие Закончилось!");
            NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(getnerator.notificationNewId(),notificationCompat.build());
        }
    }


    public void setAlarm(Context context,int id,long time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(context,EndEventReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time,pendingIntent);
    }

}

