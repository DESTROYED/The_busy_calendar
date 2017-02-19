package com.example.destr.busy_calendar.ui.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.ui.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socials.FacebookNewPost;
import com.example.destr.busy_calendar.ui.popups.InternetConnectionErrorPopup;
import com.example.destr.busy_calendar.ui.popups.LoginPopup;
import com.example.destr.busy_calendar.socials.VkSetStatus;
import com.example.destr.busy_calendar.utils.InternetConnection;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView currentMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private ImageView nextMonth;
    private ImageView prevMonth;
    private ImageView vkimage;
    private ImageView facebook;
    private Button facebookLoginButton;
    private Button vkLoginButton;
    private Button vkSetSilenceButton;
    private FacebookNewPost facebookNewPost;
    private VkSetStatus vkSetStatus;
    private ImageButton hamburger;
    private InternetConnection internetConnection;
    private DrawerLayout drawer;
    private Button settingsButton;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetConnection=new InternetConnection();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int[] month = {calendar.get(Calendar.MONTH) + 1};
        final int[] year = {calendar.get(Calendar.YEAR)};
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        drawerSet();
        vkSetStatus = new VkSetStatus();
        facebookNewPost = new FacebookNewPost();
        final Intent event = new Intent(MainActivity.this, EventActivity.class);
        initItems();
        vkLoginButton.setVisibility(View.VISIBLE);
        facebookLoginButton.setVisibility(View.VISIBLE);
        clickListeners(event);
        adapter = new GridCellAdapter(getApplicationContext(), month[0], year[0]);
        currentMonth.setText(adapter.getMonthAsString(month[0]) + Constants.OtherConstants.SPACE + year[0]);
        calendarView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void drawerSet() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        hamburger.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initItems() {
        settingsButton=(Button) findViewById(R.id.settings_button);
        hamburger=(ImageButton) findViewById(R.id.hamburger);
        vkLoginButton = (Button) findViewById(R.id.main_btn_vk);
        facebookLoginButton = (Button) findViewById(R.id.main_btn_facebook);
        facebook = (ImageView) findViewById(R.id.facebookimage);
        vkimage = (ImageView) findViewById(R.id.vkimage);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
    }

}
