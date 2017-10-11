package com.example.destr.busy_calendar.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.ui.activities.EventActivity;
import com.example.destr.busy_calendar.utils.ThemeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class GridCellAdapter extends BaseAdapter {
    private SimpleDateFormat mSimpleDateFormat;
    private static final int DAY_OFFSET = 1;
    private final Context mContext;
    private final List<String> list;
    private int thisday;
    private int thismonth;
    private int thisyear;
    private int month;
    private Calendar calendar = Calendar.getInstance();
    private GregorianCalendar mGregorianCalendar = new GregorianCalendar();
    private int pMonth = calendar.get(Calendar.MONTH) + 1;
    private int pYear = calendar.get(Calendar.YEAR);

    public GridCellAdapter(Context context, int month, int year) {
        this.mContext = context;
        this.list = new ArrayList<>();
        calendar = Calendar.getInstance();
        mSimpleDateFormat= new SimpleDateFormat("MMMM", context.getResources().getConfiguration().locale);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        thisyear = c.get(Calendar.YEAR);
        thismonth = c.get(Calendar.MONTH);
        thisday = c.get(Calendar.DAY_OF_MONTH);
        printMonth(month,year);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_cell, parent, false);
        }
        final Button gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        final TextView eventCounter = (TextView) row.findViewById(R.id.event_counter);
        // TODO: 21.09.2017 set text from DB

        String[] day_color = list.get(position).split(Constants.GridCellAdapterConstants.MINUS);
        final String theday = day_color[0];
        final String themonth = day_color[2];
        final String theyear = day_color[3];
        gridcell.setText(theday);
        gridcell.setTag(theday + Constants.GridCellAdapterConstants.MINUS + themonth + Constants.GridCellAdapterConstants.MINUS + theyear);
        new ThemeManager(mContext).setCalendarCells(gridcell);

        if (day_color[1].equals(Constants.GridCellAdapterConstants.GRAY_COLOR)) {
            gridcell.setTextColor(Color.GRAY);
            gridcell.setTag(theday + Constants.GridCellAdapterConstants.MINUS + themonth + Constants.GridCellAdapterConstants.MINUS + theyear);
        }

        if (day_color[1].equals(Constants.GridCellAdapterConstants.WHITE_COLOR)) {
            gridcell.setTextColor(Color.WHITE);
        }

        if(thisyear==Integer.parseInt(theyear)&&(thismonth+1)==month&&thisday==Integer.parseInt(theday)){
            gridcell.setTextColor(Color.RED);
        }

        //setColor here!
        gridcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent intent =new Intent(mContext, EventActivity.class);
                intent.putExtra("date",gridcell.getTag().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                //This to mark cells by another color
                /*if (checkedList.containsKey(pView.getTag().toString())) {
                    if (checkedList.get(pView.getTag().toString())) {
                        pView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.blue_cell));
                        checkedList.put(pView.getTag().toString(), false);

                    } else {
                        checkedList.put(pView.getTag().toString(), true);
                        pView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.white_cell));

                    }
                } else {
                    checkedList.put(pView.getTag().toString(), true);
                    pView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.white_cell));
                }
                */
            }
        });

        final Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        final Animation bounce = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        Handler mHandler = new Handler();
        final View finalRow = row;
        mHandler.postDelayed(new Runnable() {
            public void run() {
                gridcell.startAnimation(shake);
                gridcell.setVisibility(View.VISIBLE);
            }
        }, 25*position);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if(position%2==0)
                {
                    eventCounter.setText("1");
                    eventCounter.startAnimation(bounce);
                    eventCounter.setVisibility(View.VISIBLE);
                }
            }
        }, 35*position);
        eventCounter.setVisibility(View.GONE);
        gridcell.setVisibility(View.GONE);
        return finalRow;

    }

    public void setNextMonth(Context pContext, GridView pCalendarView, TextView pCheckedDate) {
        if (pMonth > 11) {
            pMonth = 1;
            pYear++;
        } else {
            pMonth++;
        }
        setGridCellAdapterToDate(pContext, pMonth, pYear, pCalendarView, pCheckedDate);

    }

    public void setPrevMonth(Context pContext, GridView pCalendarView, TextView pCheckedDate) {
        if (pMonth <= 1) {
            pMonth = 12;
            pYear--;
        } else {
            pMonth--;
        }
        setGridCellAdapterToDate(pContext, pMonth, pYear, pCalendarView, pCheckedDate);

    }

    private void setGridCellAdapter(final Context context, final int month, final int year, GridView pCalendarView) {
        GridCellAdapter adapter = new GridCellAdapter(context, month, year);
        pCalendarView.setAdapter(adapter);
    }



    public String getMonthAsString(int month) {
        calendar.set(Calendar.MONTH,month-1);
        return mSimpleDateFormat.format(calendar.getTime());
    }

    private int getNumberOfDaysOfMonth(int i, int pPYear) {
        calendar.set(Calendar.MONTH, i);
        if (i == 1) {
            if (mGregorianCalendar.isLeapYear(pPYear)) {
                return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1;
            } else {
                return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        } else {
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    private int daysInPrevMonth(int mm, int pPYear) {
        calendar.set(Calendar.MONTH, mm - 2);
        if (mm == 3) {
            if (mGregorianCalendar.isLeapYear(pPYear)) {
                return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1;
            } else {
                return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        } else {
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces;
        int prevYear;
        int nextYear;
        int currentMonth = mm - 1;
        if (currentMonth==0){
         prevYear = yy-1;
         nextYear=yy;
        }else if(currentMonth==11) {
            prevYear = yy;
            nextYear = yy+1;
        }else{
            nextYear = yy;
            prevYear = yy;
        }
        int daysInMonth = getNumberOfDaysOfMonth(currentMonth, yy);

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
            list.add(String.valueOf((daysInPrevMonth(mm, yy) - trailingSpaces + DAY_OFFSET) + i) + Constants.GridCellAdapterConstants.MINUS + Constants.GridCellAdapterConstants.GRAY_COLOR + Constants.GridCellAdapterConstants.MINUS + getMonthAsString(mm-1) + Constants.GridCellAdapterConstants.MINUS + prevYear);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            list.add(String.valueOf(i) + Constants.GridCellAdapterConstants.MINUS + Constants.GridCellAdapterConstants.WHITE_COLOR + Constants.GridCellAdapterConstants.MINUS + getMonthAsString(mm) + Constants.GridCellAdapterConstants.MINUS + yy);
        }

        for (int i = 0; i < list.size() % 7; i++) {
            list.add(String.valueOf(i + 1) + Constants.GridCellAdapterConstants.MINUS + Constants.GridCellAdapterConstants.GRAY_COLOR + Constants.GridCellAdapterConstants.MINUS + getMonthAsString(mm+1) + Constants.GridCellAdapterConstants.MINUS + nextYear);
        }
        month = mm;
    }

    private void setGridCellAdapterToDate(final Context context, int month, int year, GridView pCalendarView, TextView pCheckedDate) {

        calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
        pCheckedDate.setText(getMonthAsString(month) + Constants.OtherConstants.SPACE + year);
        notifyDataSetChanged();
        setGridCellAdapter(context, month, year, pCalendarView);
    }

}

