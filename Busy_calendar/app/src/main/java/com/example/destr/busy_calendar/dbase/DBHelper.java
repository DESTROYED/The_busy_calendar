package com.example.destr.busy_calendar.dbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context pContext){
        super(pContext,"BusyCalendar",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table events ("
                +"id integer primary key autoincrement,"
                +"eventname text,"
                +"date text,"
                +"sttime text"
                +"endtime text"
                +"alarmname text"
                +"status text"
                +"description text"
                +"vk integer"
                +"facebook integer"
                +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
