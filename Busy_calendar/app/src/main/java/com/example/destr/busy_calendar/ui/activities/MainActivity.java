package com.example.destr.busy_calendar.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socials.FacebookSdkHelper;
import com.example.destr.busy_calendar.socials.VkSdkHelper;
import com.example.destr.busy_calendar.ui.adapters.GridCellAdapter;
import com.example.destr.busy_calendar.utils.InternetConnection;
import com.example.destr.busy_calendar.utils.ThemeManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.vk.sdk.VKSdk;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.currentMonth)
    TextView currentMonth;

    @BindView(R.id.calendar)
    GridView calendarView;

    @BindView(R.id.nextMonth)
    ImageView nextMonth;

    @BindView(R.id.prevMonth)
    ImageView prevMonth;

    @BindView(R.id.vkimage)
    ImageView vkimage;

    @BindView(R.id.facebookimage)
    ImageView facebookImage;

    @BindView(R.id.main_btn_facebook)
    LoginButton facebookLoginButton;

    @BindView(R.id.main_btn_vk)
    Button vkLoginButton;

    @BindView(R.id.settings_button)
    ImageButton settingsButton;

    @BindView(R.id.toolbar)
    View toolBarView;

    private GridCellAdapter adapter;
    private InternetConnection internetConnection;
    private CallbackManager callbackManager;


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.contains(Constants.Settings.THEME_CHECK)){
            if(sharedPreferences.getString(Constants.Settings.THEME_CHECK,"").equals(Constants.Settings.THEME_GRAY)){
                this.setTheme(R.style.GrayTheme);

            }
            if(sharedPreferences.getString(Constants.Settings.THEME_CHECK,"").equals(Constants.Settings.THEME_GREEN)){
                this.setTheme(R.style.GreenTheme);
            }
        }
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        internetConnection = new InternetConnection();
        new ThemeManager(getApplicationContext()).setToolbar(toolBarView);
        new ThemeManager(getApplicationContext()).setCalendarCells(vkLoginButton);
        new ThemeManager(getApplicationContext()).setCalendarCells(facebookLoginButton);

        if(internetConnection.isNetworkConnected(getApplicationContext())){
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
        }
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
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
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
        if(internetConnection.isNetworkConnected(getApplicationContext())){
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
        }}
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
