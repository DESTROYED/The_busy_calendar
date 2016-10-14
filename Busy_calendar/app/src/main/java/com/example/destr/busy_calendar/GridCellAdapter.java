package com.example.destr.busy_calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

abstract class GridCellAdapter extends BaseAdapter implements View.OnClickListener
{
    private static final String tag = "GridCellAdapter";
    private final Context _context;
    private final List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int month, year;
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private Button gridcell;

    public int getMonth() {
        return month;
    }

    public GridCellAdapter(Context context, int textViewResourceId, int month, int year)
    {
        super();
        this._context = context;
        this.list = new ArrayList<>();
        this.month = month;
        this.year = year;

        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

        printMonth(month, year);


    }
    private String getMonthAsString(int i)
    {
        return months[i];
    }


    private int getNumberOfDaysOfMonth(int i)
    {
        return daysOfMonth[i];
    }

    public String getItem(int position)
    {
        return list.get(position-1);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }


    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);


        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

        if (currentMonth == 1)
        {
            if(yy%4==0&&yy%100!=0){
                daysInMonth=29;
            }
            else daysInMonth=28;
        }
        if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        }
        else if (currentMonth == 0)
        {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        }
        else
        {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK)+5;
        if(currentWeekDay>=7)
        {
            currentWeekDay-=7;
        }
        trailingSpaces = currentWeekDay;


        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1)
        {
            daysInMonth=32;
            --daysInMonth;
        }

        for (int i = 0; i < trailingSpaces; i++)
        {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
        }

        for (int i = 1; i <= daysInMonth; i++)
        {
            if (i == getCurrentDayOfMonth())
            {
                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }
            else
            {
                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }
        }

        for (int i = 0; i < list.size() % 7; i++)
        {
            list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_cell, parent, false);
        }

        gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(this);

        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];
        

        gridcell.setText(theday);
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);
        if (day_color[1].equals("GREY"))
        {
            gridcell.setTextColor(Color.GRAY);
        }
        if (day_color[1].equals("WHITE"))
        {
            gridcell.setTextColor(Color.WHITE);
        }

        return row;
    }


    public int getCurrentDayOfMonth()
    {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth)
    {
        this.currentDayOfMonth = currentDayOfMonth;
    }
    public void setCurrentWeekDay(int currentWeekDay)
    {
        this.currentWeekDay = currentWeekDay;
    }

}

