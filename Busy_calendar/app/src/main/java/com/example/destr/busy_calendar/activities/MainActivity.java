package com.example.destr.busy_calendar.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.destr.busy_calendar.InternetConnection;
import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.SoundChangeSettings;
import com.example.destr.busy_calendar.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socialsJob.FacebookLoginActivity;
import com.example.destr.busy_calendar.socialsJob.FacebookNewPost;
import com.example.destr.busy_calendar.socialsJob.TokenJob;
import com.example.destr.busy_calendar.socialsJob.VkLoginActivity;
import com.example.destr.busy_calendar.socialsJob.VkSetStatus;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView currentMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private ImageButton addButton;
    private ImageView nextMonth;
    private ImageView prevMonth;
    private ImageView vkimage;
    private ImageView facebook;
    private Button facebookLoginButton;
    private Button vkLoginButton;
    private Button vkSetSilenceButton;
    private FacebookNewPost facebookNewPost;
    private VkSetStatus vkSetStatus;
    private InternetConnection internetConnection;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetConnection = new InternetConnection();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int[] month = {calendar.get(Calendar.MONTH) + 1};
        final int[] year = {calendar.get(Calendar.YEAR)};

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        drawerSet();
        turnWifi(notificationManager);
        vkSetStatus = new VkSetStatus();
        facebookNewPost = new FacebookNewPost();
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        initItems();
        vkLoginButton.setVisibility(View.VISIBLE);
        facebookLoginButton.setVisibility(View.VISIBLE);
        if (internetConnection.isNetworkConnected(getApplicationContext())) {
            new TokenJob(getApplicationContext(), facebookLoginButton, vkLoginButton, facebook, vkimage);
        } else {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
        clickListeners(event);
        adapter = new GridCellAdapter(getApplicationContext(), month[0], year[0]);
        currentMonth.setText(adapter.getMonthAsString(month[0]) + Constants.OtherConstants.SPACE + year[0]);
        calendarView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void turnWifi(NotificationManager pNotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !pNotificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            getApplicationContext().startActivity(intent);
        }
    }

    private void drawerSet() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void clickListeners(final Intent pEvent) {
        vkSetSilenceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View pView) {
                if (internetConnection.isNetworkConnected(getApplicationContext())) {
                    new SoundChangeSettings(getApplicationContext());
                    vkSetStatus.getResponse(getApplicationContext());
                    facebookNewPost.getResponse(getApplicationContext());
                }
            }
        });
        vkLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VkLoginActivity.class));
            }
        });
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FacebookLoginActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(pEvent);
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
    }

    private void initItems() {
        vkSetSilenceButton = (Button) findViewById(R.id.mute_vk);
        vkLoginButton = (Button) findViewById(R.id.main_btn_vk);
        facebookLoginButton = (Button) findViewById(R.id.main_btn_facebook);
        facebook = (ImageView) findViewById(R.id.facebookimage);
        vkimage = (ImageView) findViewById(R.id.vkimage);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        addButton = (ImageButton) findViewById(R.id.newevent);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
    }

}
