package com.example.destr.busy_calendar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.destr.busy_calendar.db.anotations.Table;

import java.lang.reflect.AnnotatedElement;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Nullable
    public static String getTableName(final AnnotatedElement clazz){
        final Table table = clazz.getAnnotation(Table.class);
        if (table.name()!=null){
            return table.name();
        }
        else {
            return null;
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}