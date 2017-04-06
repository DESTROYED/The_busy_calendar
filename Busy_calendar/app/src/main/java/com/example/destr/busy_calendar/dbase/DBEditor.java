package com.example.destr.busy_calendar.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            final String facebookVariable,
            final String id) {
                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                contentValues.put(Constants.DBConstants.ITEM_ID,id);
                contentValues.put(Constants.DBConstants.EVENTNAME, eventNameString);
                contentValues.put(Constants.DBConstants.DATE, dataBusyCalendar);
                contentValues.put(Constants.DBConstants.START_TIME, fromTimeString);
                contentValues.put(Constants.DBConstants.END_TIME, toTimeString);
                contentValues.put(Constants.DBConstants.ALARM_NAME, alertNameString);
                contentValues.put(Constants.DBConstants.STATUS, changeStatusString);
                contentValues.put(Constants.DBConstants.DESCRIPTION, eventDescriptionString);
                contentValues.put(Constants.DBConstants.VK_INTEGER, vkVariable);
                contentValues.put(Constants.DBConstants.FACEBOOK_INTEGER, facebookVariable);
                database.insert(Constants.DBConstants.TABLE_NAME, null, contentValues);
                database.close();
    }

    public String getNumberOfEvents(final Context context, final String date){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.isOpen();
        Cursor cursor = database.rawQuery("SELECT "+Constants.DBConstants.EVENTNAME+","+Constants.DBConstants.STATUS+","+Constants.DBConstants.DESCRIPTION+" FROM "+Constants.DBConstants.TABLE_NAME+" WHERE date=?",new String[]{date});
        cursor.moveToFirst();
        if (cursor.getCount()==0){
            return "There are not any Events";
        }else
        return "You have "+cursor.getCount()+" events at this day :";
    }

    public Cursor getFromDB(final Context context,final String date){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.isOpen();
        Cursor cursor = database.rawQuery("SELECT "+Constants.DBConstants.EVENTNAME+","+Constants.DBConstants.STATUS+","+Constants.DBConstants.DESCRIPTION+","+Constants.DBConstants.START_TIME+","+Constants.DBConstants.END_TIME+","+Constants.DBConstants.FACEBOOK_INTEGER+","+Constants.DBConstants.VK_INTEGER+","+ Constants.DBConstants.ALARM_NAME+"," +Constants.DBConstants.ITEM_ID+"," +Constants.DBConstants.DATE+" as _id FROM "+Constants.DBConstants.TABLE_NAME+" WHERE date=?",new String[]{date});
        cursor.moveToFirst();
            return cursor;
    }

    public void delete(final Context context,final int id){

                DBHelper dbHelper=new DBHelper(context);
                SQLiteDatabase database =dbHelper.getWritableDatabase();
                database.delete(Constants.DBConstants.TABLE_NAME,"item_id = " + id,null);
    }

    public void update(final Context context,
                       final int id,
                       String eventNameString,
                       String fromTimeString,
                       String toTimeString,
                       String alertNameString,
                       String changeStatusString,
                       String eventDescriptionString,
                       String vkVariable,
                       String facebookVariable
                       ){
                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                contentValues.put(Constants.DBConstants.ITEM_ID,id);
                contentValues.put(Constants.DBConstants.EVENTNAME, eventNameString);
                contentValues.put(Constants.DBConstants.START_TIME, fromTimeString);
                contentValues.put(Constants.DBConstants.END_TIME, toTimeString);
                contentValues.put(Constants.DBConstants.ALARM_NAME, alertNameString);
                contentValues.put(Constants.DBConstants.STATUS, changeStatusString);
                contentValues.put(Constants.DBConstants.DESCRIPTION, eventDescriptionString);
                contentValues.put(Constants.DBConstants.VK_INTEGER, vkVariable);
                contentValues.put(Constants.DBConstants.FACEBOOK_INTEGER, facebookVariable);
                database.update(Constants.DBConstants.TABLE_NAME,contentValues,"item_id = ?",new String[]{String.valueOf(id)});
            }
}
