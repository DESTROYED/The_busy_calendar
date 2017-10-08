package com.example.destr.busy_calendar.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.dbase.DBEditor;
import com.example.destr.busy_calendar.utils.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventInfoActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextView eventName;

    @BindView(R.id.from_time)
    TextView fromTime;

    @BindView(R.id.to_time)
    TextView toTime;

    @BindView(R.id.alert)
    TextView alert;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.checkbox_social)
    CheckBox socials;

    @BindView(R.id.socials_vk)
    CheckBox socialsVk;

    @BindView(R.id.socials_facebook)
    CheckBox socialsFacebook;

    @BindView(R.id.delete_event)
    ImageButton deleteEvent;

    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.toolbar)
    View toolbar;

    @BindView(R.id.update_database)
    Button updateButton;

    private String eventNameString;
    private String fromTimeString;
    private String toTimeString;
    private String alertNameString;
    private String changeStatusString;
    private String eventDescriptionString;
    private int vkVariable;
    private int facebookVariable;
    private Intent intent;

    @Override
    public void onBackPressed() {
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_event_info);
        ButterKnife.bind(this);
        new ThemeManager(getApplicationContext()).setToolbar(toolbar);
        final String[] strings =getIntent().getStringArrayExtra(Constants.OtherConstants.STRING_ARRAY_EXTRA);
        eventName.setText(strings[0]);
        status.setText(strings[1]);
        description.setText(strings[2]);
        fromTime.setText(strings[3]);
        toTime.setText(strings[4]);
        intent = new Intent(EventInfoActivity.this,EventActivity.class);
        intent.putExtra("date",strings[9]);
        if(strings[5].equals("0")&&strings[6].equals("0")){
            socials.setSelected(false);
        }
        else {
            socials.setSelected(true);
        }
        if(socials.isSelected()) {
            visibilitySocials(true);
            if(strings[5].equals("1")){
                socialsVk.setChecked(true);
            }
            if(strings[6].equals("1")){
                socialsFacebook.setChecked(true);
            }
        }
        else {
            visibilitySocials(false);

        }
        socials.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilitySocials(isChecked);
            }
        });
        alert.setText(strings[7]);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(intent);
            }
        });

        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBEditor dbEditor = new DBEditor();
                dbEditor.delete(getApplicationContext(), Integer.parseInt(strings[8]));
                startActivity(new Intent(EventInfoActivity.this,MainActivity.class));
                finish();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBEditor dbEditor = new DBEditor();
                getValues();
                dbEditor.update(getApplicationContext(),Integer.parseInt(strings[8]),eventNameString,fromTimeString,toTimeString,alertNameString,changeStatusString,eventDescriptionString,String.valueOf(vkVariable),String.valueOf(facebookVariable));
            }
        });
    }
        private void visibilitySocials(boolean isChecked) {
            if (isChecked) {
                socialsVk.setVisibility(View.VISIBLE);
                socialsFacebook.setVisibility(View.VISIBLE);
                findViewById(R.id.label_facebook_checkbox).setVisibility(View.VISIBLE);
                findViewById(R.id.label_vk_checkbox).setVisibility(View.VISIBLE);
            } else {
                socialsVk.setChecked(false);
                socialsFacebook.setChecked(false);
                socialsVk.setVisibility(View.GONE);
                socialsFacebook.setVisibility(View.GONE);
                findViewById(R.id.label_facebook_checkbox).setVisibility(View.GONE);
                findViewById(R.id.label_vk_checkbox).setVisibility(View.GONE);
            }
        }
    private void getValues() {
        eventNameString = eventName.getText().toString();
        fromTimeString = fromTime.getText().toString();
        toTimeString = toTime.getText().toString();
        alertNameString = alert.getText().toString();
        changeStatusString = status.getText().toString();
        eventDescriptionString = description.getText().toString();
        if (socialsVk.isChecked()) {
            vkVariable = 1;
        }
        if (socialsFacebook.isChecked()) {
            facebookVariable = 1;
        }
    }
    }

