package com.example.destr.busy_calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String tag = "SimpleCalendarViewActivity";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private ImageView calendarToJournalButton;
    private Button currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private static final String dateTemplate = "MMMM yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _calendar = Calendar.getInstance(Locale.getDefault());
        int month = _calendar.get(Calendar.MONTH) + 1;
        int year = _calendar.get(Calendar.YEAR);
        Log.d("", "Calendar Instance:= " + "Month: " + month + " " + "Year: " + year);
        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        currentMonth = (Button) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        calendarView = (GridView) this.findViewById(R.id.calendar);
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }




    @Override
    public void onDestroy()
    {
        Log.d(String.valueOf(tag), "Destroying View ...");
        super.onDestroy();
    }
    private void setGridCellAdapterToDate(int month, int year)
    {
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }
}
