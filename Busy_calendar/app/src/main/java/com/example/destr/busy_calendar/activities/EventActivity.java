package com.example.destr.busy_calendar.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.dbase.DBEditor;
import com.example.destr.busy_calendar.fragments.EndTimePicker;
import com.example.destr.busy_calendar.fragments.StartTimePicker;

public class EventActivity extends AppCompatActivity {
    //TODO sonar test NOT
    //TODO accounmanager for tokens NOT
    private String dataBusyCalendar;
    private String eventNameString;
    private String fromTimeString;
    private String toTimeString;
    private String alertNameString;
    private String changeStatusString;
    private String eventDescriptionString;
    private int vkVariable = 0;
    private int facebookVariable = 0;
    private TextView chooseStartTime;
    private TextView chooseEndTime;
    private EditText enterAlertName;
    private CheckBox vkCheckBox;
    private CheckBox facebookCheckBox;
    private EditText editTextStatus;
    private EditText eventName;
    private EditText descriptionString;
    private CheckBox alarmCheckBox;
    private CheckBox allDayCheckBox;
    private CheckBox statusCheckBox;
    private ImageButton closeButton;
    private ImageButton saveButton;
    private CheckBox socials;
//TODO all exceptions fix
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initItems();
        clickOrCheckListeners();

    }

    private void clickOrCheckListeners() {
        chooseStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTimePickerFragment();
            }
        });
        chooseEndTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                endTimePickerFragment();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBEditor mDBEditor=new DBEditor();
                getValues();
                mDBEditor.setDB(getApplicationContext(),eventNameString,dataBusyCalendar,fromTimeString,toTimeString,alertNameString,changeStatusString,eventDescriptionString,String.valueOf(vkVariable),String.valueOf(facebookVariable));
                finish();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alarmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enterAlertName.setVisibility(View.VISIBLE);
                } else {
                    enterAlertName.setVisibility(View.GONE);
                }
            }
        });
        allDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilityAllDayCheckBox(isChecked);
            }
        });
        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilityStatusCheckBox(isChecked);
            }
        });
        socials.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibilitySocials(isChecked);
            }
        });
    }

    private void visibilitySocials(boolean isChecked) {
        if (isChecked) {
            vkCheckBox.setVisibility(View.VISIBLE);
            facebookCheckBox.setVisibility(View.VISIBLE);
            findViewById(R.id.label_facebook_checkbox).setVisibility(View.VISIBLE);
            findViewById(R.id.label_vk_checkbox).setVisibility(View.VISIBLE);
        } else {
            vkCheckBox.setChecked(false);
            facebookCheckBox.setChecked(false);
            vkCheckBox.setVisibility(View.GONE);
            facebookCheckBox.setVisibility(View.GONE);
            findViewById(R.id.label_facebook_checkbox).setVisibility(View.GONE);
            findViewById(R.id.label_vk_checkbox).setVisibility(View.GONE);
        }
    }

    private void visibilityStatusCheckBox(boolean isChecked) {
        if (isChecked) {
            editTextStatus.setVisibility(View.VISIBLE);
        } else {
            editTextStatus.setText(null);
            editTextStatus.setVisibility(View.GONE);
        }
    }

    private void visibilityAllDayCheckBox(boolean isChecked) {
        if (isChecked) {
            chooseEndTime.setVisibility(View.GONE);
            chooseStartTime.setVisibility(View.GONE);
            findViewById(R.id.to_tw).setVisibility(View.GONE);
            findViewById(R.id.from_tw).setVisibility(View.GONE);
        } else {
            chooseEndTime.setVisibility(View.VISIBLE);
            chooseStartTime.setText(R.string.choose_from);
            chooseEndTime.setText(R.string.choose_from);
            chooseStartTime.setVisibility(View.VISIBLE);
            findViewById(R.id.to_tw).setVisibility(View.VISIBLE);
            findViewById(R.id.from_tw).setVisibility(View.VISIBLE);
        }
    }

    private void getValues() {
        dataBusyCalendar = "MAGIC DATA";
        eventNameString = eventName.getText().toString();
        fromTimeString = chooseStartTime.getText().toString();
        toTimeString = chooseEndTime.getText().toString();
        alertNameString = enterAlertName.getText().toString();
        changeStatusString = editTextStatus.getText().toString();
        eventDescriptionString = descriptionString.getText().toString();
        if (vkCheckBox.isChecked()) {
            vkVariable = 1;
        }
        if (facebookCheckBox.isChecked()) {
            facebookVariable = 1;
        }
    }

    private void initItems() {
        alarmCheckBox = (CheckBox) findViewById(R.id.alert_checkbox);
        allDayCheckBox = (CheckBox) findViewById(R.id.checkbox_time);
        statusCheckBox = (CheckBox) findViewById(R.id.status_checkbox);
        closeButton = (ImageButton) findViewById(R.id.close_event);
        saveButton = (ImageButton) findViewById(R.id.save_event);
        socials = (CheckBox) findViewById(R.id.checkbox_social);
        descriptionString = (EditText) findViewById(R.id.description_string);
        editTextStatus = (EditText) findViewById(R.id.checkbox_status);
        eventName = (EditText) findViewById(R.id.event_name);
        enterAlertName = (EditText) findViewById(R.id.combotext_alert);
        chooseStartTime = (TextView) findViewById(R.id.choose_start_time);
        chooseEndTime = (TextView) findViewById(R.id.choose_end_time);
        vkCheckBox = (CheckBox) findViewById(R.id.checkbox_vk);
        facebookCheckBox = (CheckBox) findViewById(R.id.checkbox_facebook);
    }

    private void startTimePickerFragment() {
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new StartTimePicker();
        newFragment.show(fm, Constants.OtherConstants.TIMEPICKER_NAME);
    }
    private void endTimePickerFragment() {
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new EndTimePicker();
        newFragment.show(fm, Constants.OtherConstants.TIMEPICKER_NAME);
    }

}
