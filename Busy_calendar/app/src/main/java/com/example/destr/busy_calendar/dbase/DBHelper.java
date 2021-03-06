package com.example.destr.busy_calendar.dbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

class DBHelper extends SQLiteOpenHelper {

    private StringBuilderForDb mStringBuilderForDb = new StringBuilderForDb();

    DBHelper(Context pContext) {
        super(pContext, Constants.DBConstants.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mStringBuilderForDb.buildRequest());
        Log.d("dbVersion", String.valueOf(db.getVersion()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
