package com.example.destr.busy_calendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String tag = "SimpleCalendarViewActivity";
    private TextView currentMonth;
    private TextView checkedDate;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        _calendar = Calendar.getInstance(Locale.getDefault());
        int month = _calendar.get(Calendar.MONTH) + 1;
        int year = _calendar.get(Calendar.YEAR);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year) {
            @Override
            public void onClick(View v) {
                checkedDate.setText(v.getTag().toString());
            }
        };
        currentMonth.setText(months[month-1]);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
        checkedDate=(TextView)findViewById(R.id.selectedDayMonthYear);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }




    @Override
    public void onDestroy()
    {
        Log.d(String.valueOf(tag), "Destroying View ...");
        super.onDestroy();
    }

}
