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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socials.FacebookSdkHelper;
import com.example.destr.busy_calendar.socials.VkSdkHelper;
import com.example.destr.busy_calendar.ui.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.ui.popups.ExitPopup;
import com.example.destr.busy_calendar.utils.InternetConnection;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vk.sdk.VKSdk;

import java.util.Arrays;
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
    private LoginButton facebookLoginButton;
    private Button vkLoginButton;
    private ImageButton settingsButton;
    private InternetConnection internetConnection;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, ExitPopup.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        initItems();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        if (AccessToken.getCurrentAccessToken() != null) {
            new FacebookSdkHelper(getApplicationContext()).setPhoto(facebookImage,facebookLoginButton);
        }
        else {
            facebookImage.setVisibility(View.GONE);
            facebookLoginButton.setVisibility(View.VISIBLE);
        }
        if (VKSdk.isLoggedIn()) {
            new VkSdkHelper(getApplicationContext()).setPhoto(vkimage, vkLoginButton);
        }
        else{
            vkimage.setVisibility(View.GONE);
            vkLoginButton.setVisibility(View.VISIBLE);
        }
        internetConnection = new InternetConnection();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int[] month = {calendar.get(Calendar.MONTH) + 1};
        final int[] year = {calendar.get(Calendar.YEAR)};

        facebookLoginButton.setReadPermissions("public_profile");
        clickListeners();
        adapter = new GridCellAdapter(getApplicationContext(), month[0], year[0]);
        currentMonth.setText(adapter.getMonthAsString(month[0]) + Constants.OtherConstants.SPACE + year[0]);
        calendarView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void clickListeners() {
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SilenceActivity.class);
                startActivity(intent);
            }
        });
        vkLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                VKSdk.login(MainActivity.this, "status ", "notifications","messages");
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
                VKSdk.logout();
                vkimage.setVisibility(View.GONE);
                vkLoginButton.setVisibility(View.VISIBLE);
            }
        });
        facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                facebookImage.setVisibility(View.GONE);
                facebookLoginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        if (VKSdk.isLoggedIn()) {
            new VkSdkHelper(getApplicationContext()).setPhoto(vkimage, vkLoginButton);
        }
        else {
            vkimage.setVisibility(View.GONE);
            vkLoginButton.setVisibility(View.VISIBLE);
        }
        if (AccessToken.getCurrentAccessToken() != null) {
            new FacebookSdkHelper(getApplicationContext()).setPhoto(facebookImage,facebookLoginButton);
        }
        else {
            facebookImage.setVisibility(View.GONE);
            facebookLoginButton.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initItems() {
        settingsButton = (ImageButton) findViewById(R.id.settings_button);
        vkLoginButton = (Button) findViewById(R.id.main_btn_vk);
        facebookLoginButton = (LoginButton) findViewById(R.id.main_btn_facebook);
        facebookImage = (ImageView) findViewById(R.id.facebookimage);
        vkimage = (ImageView) findViewById(R.id.vkimage);
        prevMonth = (ImageView) findViewById(R.id.prevMonth);
        nextMonth = (ImageView) findViewById(R.id.nextMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        calendarView = (GridView) findViewById(R.id.calendar);
    }

}
