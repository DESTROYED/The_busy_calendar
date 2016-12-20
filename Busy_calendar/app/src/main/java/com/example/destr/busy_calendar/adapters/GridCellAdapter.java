package com.example.destr.busy_calendar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public abstract class GridCellAdapter extends BaseAdapter implements View.OnClickListener {

    private static final int DAY_OFFSET = 1;
    private Formatter mFormatter = new Formatter();
    private final Context _context;
    private HashMap<String, Boolean> checkedList = new HashMap<>();
    private final List<String> list;
    private int currentDayOfMonth;
    private TextView checkedDate;
    private GridCellAdapter mAdapter;
    private Calendar calendar=Calendar.getInstance();
    private int pMonth =calendar.get(Calendar.MONTH)+1;
    private int pYear = calendar.get(Calendar.YEAR);

    protected GridCellAdapter(Context context,int month ,int year) {
        this._context = context;
        this.list = new ArrayList<>();
        calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        printMonth(month,year);
    }

    public int getpMonth() {
        return pMonth;
    }

    public int getpYear() {
        return pYear;
    }

    public String getItem(int position) {
        return list.get(position - 1);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_cell, parent, false);
        }

        Button gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(this);
        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        gridcell.setText(theday);
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);
        if (day_color[1].equals("GREY")) {
            gridcell.setTextColor(Color.GRAY);
        }
        if (day_color[1].equals("WHITE")) {
            gridcell.setTextColor(Color.WHITE);
        }

        return row;
    }

    private int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }


    private String getMonthAsString(int i) {
        return String.valueOf(mFormatter.format("%tB",calendar));
    }

    private int getNumberOfDaysOfMonth(int i) {
        calendar.set(Calendar.MONTH, i);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces ;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        int daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);



        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) + 5;
        if (currentWeekDay >= 7) {
            currentWeekDay -= 7;
        }
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
            daysInMonth = 32;
            --daysInMonth;
        }

        for (int i = 0; i < trailingSpaces; i++) {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            if (i == getCurrentDayOfMonth()) {
                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            } else {
                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }
        }

        for (int i = 0; i < list.size() % 7; i++) {
            list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }
    public void setNextMonth(Context pContext,GridView pCalendarView,TextView pCheckedDate) {
        if (pMonth > 11) {
            pMonth = 1;
            pYear++;
        } else {
            pMonth++;
        }
        setGridCellAdapterToDate(pContext,pMonth, pYear,pCalendarView,pCheckedDate);

    }
    public void setPrevMonth(Context pContext,GridView pCalendarView,TextView pCheckedDate) {
        if (pMonth<= 1) {
            pMonth = 12;
            pYear--;
        } else {
            pMonth--;
        }
        setGridCellAdapterToDate(pContext,pMonth, pYear,pCalendarView,pCheckedDate);

    }
    private void setGridCellAdapterToDate(final Context context, int month, int year, GridView pCalendarView, TextView pCheckedDate) {

        calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
        pCheckedDate.setText(month + " " + year);
        setCurrentDayOfMonth(calendar.get(month));
        notifyDataSetChanged();
        setGridCellAdapter(context, month, year, pCalendarView);

    }

    private void setGridCellAdapter(final Context context, final int month, final int year, GridView pCalendarView) {
        mAdapter = new GridCellAdapter(context,month,year) {

            @Override
            public void onClick(View pView) {
                checkedDate=(TextView) pView.findViewById(R.id.selectedDayMonthYear);

                if (checkedList.containsKey(pView.getTag().toString())) {
                    if (checkedList.get(pView.getTag().toString())) {
                        pView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
                        checkedList.put(pView.getTag().toString(), false);

                    } else {
                        checkedList.put(pView.getTag().toString(), true);
                        pView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryBar));
                    }
                } else {
                    checkedList.put(pView.getTag().toString(), true);
                    pView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryBar));
                }
            }
        };
        pCalendarView.setAdapter(mAdapter);
    }

}

