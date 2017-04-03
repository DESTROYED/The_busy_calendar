package com.example.destr.busy_calendar.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socials.LogOut;
import com.example.destr.busy_calendar.socials.TokenJob;
import com.example.destr.busy_calendar.ui.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.ui.popups.ExitPopup;
import com.example.destr.busy_calendar.ui.popups.InternetConnectionErrorPopup;
import com.example.destr.busy_calendar.ui.popups.LoginPopup;
import com.example.destr.busy_calendar.utils.InternetConnection;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView currentMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private ImageView nextMonth;
    private ImageView prevMonth;
    private ImageView vkimage;
    private ImageView facebookImage;
    private Button facebookLoginButton;
    private Button vkLoginButton;
    private ImageButton settingsButton;
    private InternetConnection internetConnection;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item)
    {
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, ExitPopup.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        internetConnection=new InternetConnection();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int[] month = {calendar.get(Calendar.MONTH) + 1};
        final int[] year = {calendar.get(Calendar.YEAR)};
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        initItems();
        vkLoginButton.setVisibility(View.VISIBLE);
        facebookLoginButton.setVisibility(View.VISIBLE);
        clickListeners(event);
        if(internetConnection.isNetworkConnected(getApplicationContext())){
            TokenJob tokenJob = new TokenJob(getApplicationContext(),facebookLoginButton,vkLoginButton,vkimage, facebookImage);
        }
        adapter = new GridCellAdapter(getApplicationContext(), month[0], year[0]);
        currentMonth.setText(adapter.getMonthAsString(month[0]) + Constants.OtherConstants.SPACE + year[0]);
        calendarView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void clickListeners(final Intent pEvent) {
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),SilenceActivity.class);
                    startActivity(intent);
            }
        });
        vkLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(internetConnection.isNetworkConnected(MainActivity.this)){
                Intent intent= new Intent(MainActivity.this, LoginPopup.class);
                intent.putExtra("Social","vk");
                startActivity(intent);
                }else{
                    startActivity(new Intent(MainActivity.this, InternetConnectionErrorPopup.class));
                }
            }
        });
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             if(   internetConnection.isNetworkConnected(MainActivity.this)){
                Intent intent = new Intent(MainActivity.this, LoginPopup.class);
                intent.putExtra("Social", "facebook");
                startActivity(intent);
            }else {
                 startActivity(new Intent(MainActivity.this, InternetConnectionErrorPopup.class));
             }
            }
        });
        prevMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.setPrevMonth(getApplicationContext(), calendarView, currentMonth);
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.setNextMonth(getApplicationContext(), calendarView, currentMonth);
            }
        });
        vkimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"VKGONE",Toast.LENGTH_LONG).show();
                editor.remove(Constants.TokenJob.VK_TOKEN).apply();
                new LogOut().vk(getApplicationContext());

            }
        });
        facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"FACEBOOKGONE",Toast.LENGTH_LONG).show();
               editor.remove(Constants.TokenJob.FACEBOOK_TOKEN).apply();
                new LogOut().facebook(getApplicationContext());
            }
        });

    }

    private void initItems() {
        settingsButton =(ImageButton) findViewById(R.id.settings_button);
        vkLoginButton = (Button) findViewById(R.id.main_btn_vk);
        facebookLoginButton = (Button) findViewById(R.id.main_btn_facebook);
        facebookImage = (ImageView) findViewById(R.id.facebookimage);
        vkimage = (ImageView) findViewById(R.id.vkimage);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
    }

}
