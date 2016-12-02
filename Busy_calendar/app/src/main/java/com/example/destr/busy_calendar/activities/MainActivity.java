package com.example.destr.busy_calendar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.destr.busy_calendar.Adapters.GridCellAdapter;
import com.example.destr.busy_calendar.Http.HttpGetOperation;
import com.example.destr.busy_calendar.Http.HttpRequest;
import com.example.destr.busy_calendar.Json.FacebookJsonParseImages;
import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.ImageLoad.ImageLoader;
import com.example.destr.busy_calendar.Threads.OnResultCallback;
import com.example.destr.busy_calendar.Threads.ThreadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //TODO rename to normal tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView currentMonth;
    //TODO what about access modifier
    HashMap<String ,Boolean> checkedList = new HashMap<>();
    private TextView checkedDate;
    private GridView calendarView;
    private GridCellAdapter adapter;
    //TODO it's not java style
    private Calendar _calendar;
    private ImageView facebook;
    //TODO remove it
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO method onCreate too big.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        final View vk_loginButton = findViewById(R.id.main_btn_vk);
        final View facebook_loginButton = findViewById(R.id.main_btn_facebook);
        final SharedPreferences logTest= PreferenceManager.getDefaultSharedPreferences(this);
        //TODO move to constans
        if(!logTest.getString("vk_token","").isEmpty()){
            vk_loginButton.setVisibility(View.GONE);
            //TODO not used
            HttpRequest mRequest=new HttpRequest();
            ImageView vkimage = (ImageView) findViewById(R.id.vkimage);
            mRequest.setUrl(logTest.getString("vk_token",""));
        }
        if(!logTest.getString("facebook_token","").isEmpty()){
            facebook_loginButton.setVisibility(View.GONE);
            final HttpRequest mRequest= new HttpRequest();
            final ThreadManager threadManager = new ThreadManager();
            facebook=(ImageView) findViewById(R.id.facebookimage);
            //TODO create class for work with API
            //TODO get with/height from view. Remove hardcode.
            mRequest.setUrl("https://graph.facebook.com/me?fields=picture.width(800).height(800)&access_token="+logTest.getString("facebook_token",""));
            threadManager.execute(new HttpGetOperation(), mRequest, new OnResultCallback<String, Void>() {
                @Override
                public void onSuccess(String s) {
                    try {
                        //TODO you parse json in UI thread!!
                        //TODO return model as response
                        JSONObject jsonObject= new JSONObject(s);
                        FacebookJsonParseImages imageUrl= new FacebookJsonParseImages(jsonObject);
                        new ImageLoader(facebook).execute(imageUrl.getUrl());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onSuccess = " + e.getMessage());
                }

                @Override
                public void onProgressChanged(Object o) {
                }
            });
        }
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        _calendar = Calendar.getInstance(Locale.getDefault());
        ImageButton addButton = (ImageButton) findViewById(R.id.newevent);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(event);
            }
        });
        final int[] month = {_calendar.get(Calendar.MONTH) + 1};
        final int[] year = {_calendar.get(Calendar.YEAR)};
        ImageView prevMonth = (ImageView) findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO refactor that
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

        currentMonth.setText(months[month[0] -1]+" "+year[0]);
        ImageView nextMonth = (ImageView) findViewById(R.id.nextMonth);
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

        currentMonth = (TextView) findViewById(R.id.currentMonth);
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month[0], year[0]) {
            @Override
            public void onClick(View v) {
                if (checkedList.containsKey(v.getTag().toString())) {
                    if (checkedList.get(v.getTag().toString())) {
                        v.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        checkedList.put(v.getTag().toString(), false);

                    } else {
                        checkedList.put(v.getTag().toString(), true);
                        checkedDate.setText(v.getTag().toString());
                        v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryBar));
                    }
                } else {
                    checkedList.put(v.getTag().toString(), true);
                    checkedDate.setText(v.getTag().toString());
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryBar));
                }

            }
        };

        calendarView = (GridView) findViewById(R.id.calendar);
        checkedDate=(TextView)findViewById(R.id.selectedDayMonthYear);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }
    private void setGridCellAdapterToDate(int month, int year)
    {
//        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year) {
//            @Override
//            public void onClick(View v) {
//                checkedDate.setText(v.getTag().toString());
//            }
//        };
        //TODO update items in adapter
        adapter.updateView(month, year);
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
