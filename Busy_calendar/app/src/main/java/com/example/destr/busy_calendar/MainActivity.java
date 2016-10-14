package com.example.destr.busy_calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextView currentMonth;
    private TextView checkedDate;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private ImageButton addButton;
    private Calendar _calendar;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        _calendar = Calendar.getInstance(Locale.getDefault());
        addButton=(ImageButton) findViewById(R.id.newevent);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(event);
            }
        });
        final int[] month = {_calendar.get(Calendar.MONTH) + 1};
        final int[] year = {_calendar.get(Calendar.YEAR)};
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (month[0] <= 1)
                {
                    month[0] = 12;
                    year[0]--;
                }
                else
                {
                    month[0]--;
                }
                setGridCellAdapterToDate(month[0], year[0]);
            }
        });
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month[0], year[0]) {
            @Override
            public void onClick(View v) {
                v.getTag();
                checkedDate.setText(v.getTag().toString());
            }
        };
        currentMonth.setText(months[month[0] -1]+" "+year[0]);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month[0] > 11)
                {
                    month[0] = 1;
                    year[0]++;
                }
                else
                {
                    month[0]++;
                }
                setGridCellAdapterToDate(month[0], year[0]);
            }
        });
        calendarView = (GridView) findViewById(R.id.calendar);
        checkedDate=(TextView)findViewById(R.id.selectedDayMonthYear);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }
    private void setGridCellAdapterToDate(int month, int year)
    {
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year) {
            @Override
            public void onClick(View v) {
                checkedDate.setText(v.getTag().toString());
            }
        };
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(months[month -1]+" "+year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }




    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}
