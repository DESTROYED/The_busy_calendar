package com.example.destr.busy_calendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;

public class ThemeManager {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ThemeManager(Context context){
        this.context=context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharedPreferences.edit();
    }


    public void setToolbar(View toolbar){
            if(sharedPreferences.getString(Constants.Settings.THEME_CHECK,"").equals(Constants.Settings.THEME_GRAY)){
            toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.com_facebook_button_background_color_disabled));
        }
        if(sharedPreferences.getString(Constants.Settings.THEME_CHECK,"").equals(Constants.Settings.THEME_GREEN)){
            toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.blueMonthBar));
        }
    }
    public void setCalendarCells(Button gridCell) {
            if (sharedPreferences.getString(Constants.Settings.THEME_CHECK, "").equals(Constants.Settings.THEME_GRAY)) {
                gridCell.setBackgroundColor(context.getResources().getColor(R.color.com_facebook_button_background_color_disabled));
                gridCell.setTextColor(context.getResources().getColor(R.color.vk_black));
            }
        if (sharedPreferences.getString(Constants.Settings.THEME_CHECK, "").equals(Constants.Settings.THEME_GREEN)) {
            gridCell.setBackgroundColor(context.getResources().getColor(R.color.blueMonthBar));
            gridCell.setTextColor(context.getResources().getColor(R.color.white));
        }
    }
    public void setEventItems(View items) {
            if (sharedPreferences.getString(Constants.Settings.THEME_CHECK, "").equals(Constants.Settings.THEME_GRAY)) {
                items.setBackgroundColor(context.getResources().getColor(R.color.com_facebook_button_background_color_disabled));
            }
        if (sharedPreferences.getString(Constants.Settings.THEME_CHECK, "").equals(Constants.Settings.THEME_GREEN)) {
            items.setBackgroundColor(context.getResources().getColor(R.color.blueMonthBar));
        }
    }
}
