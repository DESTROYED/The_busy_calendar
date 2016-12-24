package com.example.destr.busy_calendar.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.destr.busy_calendar.constants.Constants;

public class DBEditor {

    private ContentValues contentValues = new ContentValues();

    public void setDB(
            final Context context,
            final String eventNameString,
            final String dataBusyCalendar,
            final String fromTimeString,
            final String toTimeString,
            final String alertNameString,
            final String changeStatusString,
            final String eventDescriptionString,
            final String vkVariable,
            final String facebookVariable) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                contentValues.put(Constants.DBConstants.EVENTNAME, eventNameString);
                contentValues.put(Constants.DBConstants.DATE, dataBusyCalendar);
                contentValues.put(Constants.DBConstants.START_TIME, fromTimeString);
                contentValues.put(Constants.DBConstants.END_TIME, toTimeString);
                contentValues.put(Constants.DBConstants.ALARM_TIME, alertNameString);
                contentValues.put(Constants.DBConstants.STATUS, changeStatusString);
                contentValues.put(Constants.DBConstants.DESCRIPTION, eventDescriptionString);
                contentValues.put(Constants.DBConstants.VK_INTEGER, vkVariable);
                contentValues.put(Constants.DBConstants.FACEBOOK_INTEGER, facebookVariable);
                database.insert(Constants.DBConstants.TABLE_NAME, null, contentValues);
                database.close();

            }
        });
    }
}
