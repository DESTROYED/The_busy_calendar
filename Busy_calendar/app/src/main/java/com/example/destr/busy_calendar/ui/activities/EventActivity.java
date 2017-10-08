package com.example.destr.busy_calendar.ui.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.dbase.DBEditor;
import com.example.destr.busy_calendar.ui.adapters.EventAdapter;
import com.example.destr.busy_calendar.ui.popups.EndTimePickerPopup;
import com.example.destr.busy_calendar.ui.popups.StartTimePickerPopup;
import com.example.destr.busy_calendar.utils.AlarmUtility;
import com.example.destr.busy_calendar.utils.DataBaseUniqIdGenerator;
import com.example.destr.busy_calendar.utils.EndEventReciever;
import com.example.destr.busy_calendar.utils.ThemeManager;
import com.example.destr.busy_calendar.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    View toolbar;

    @BindView(R.id.choose_start_time)
    TextView chooseStartTime;

    @BindView(R.id.choose_end_time)
    TextView chooseEndTime;

    @BindView(R.id.combotext_alert)
    EditText enterAlertName;

    @BindView(R.id.checkbox_vk)
    CheckBox vkCheckBox;

    @BindView(R.id.checkbox_facebook)
    CheckBox facebookCheckBox;

    @BindView(R.id.checkbox_status)
    EditText editTextStatus;

    @BindView(R.id.event_name)
    EditText eventName;

    @BindView(R.id.description_string)
    EditText descriptionString;

    @BindView(R.id.alert_checkbox)
    CheckBox alarmCheckBox;

    @BindView(R.id.checkbox_time)
    CheckBox allDayCheckBox;

    @BindView(R.id.status_checkbox)
    CheckBox statusCheckBox;

    @BindView(R.id.close_event)
    ImageButton closeButton;

    @BindView(R.id.save_event)
    ImageButton saveButton;

    @BindView(R.id.checkbox_social)
    CheckBox socials;

    @BindView(R.id.selectedDayMonthYear)
    TextView setDate;

    @BindView(R.id.event_list)
    ListView listView;

    @BindView(R.id.how_many_events)
    TextView howManyEvents;

    private String dataBusyCalendar;
    private String eventNameString;
    private String fromTimeString;
    private String toTimeString;
    private String alertNameString;
    private String changeStatusString;
    private String eventDescriptionString;
    private int vkVariable = 0;
    private int facebookVariable = 0;
    private AlarmUtility alarm;
    private EndEventReciever endAlarm;
    private DataBaseUniqIdGenerator dataBaseUniqIdGenerator;
    private int id;
    private TimeUtils timeUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.event);
        ButterKnife.bind(this);
        dataBaseUniqIdGenerator = new DataBaseUniqIdGenerator(this);
        DBEditor mDBEditor=new DBEditor();
        alarm = new AlarmUtility();
        endAlarm = new EndEventReciever();
        timeUtils = new TimeUtils();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new ThemeManager(getApplicationContext()).setToolbar(toolbar);
        clickOrCheckListeners();
        getDataBaseEvent(mDBEditor);
    }

    private void getDataBaseEvent(DBEditor mDBEditor) {
        dataBusyCalendar = getIntent().getExtras().getString("date");
        setDate.setText(dataBusyCalendar);
        if(!mDBEditor.getNumberOfEvents(this,dataBusyCalendar).equals("There are not any Events")){
            howManyEvents.setText(mDBEditor.getNumberOfEvents(this,dataBusyCalendar));
            Cursor cursor = mDBEditor.getFromDB(this, dataBusyCalendar);
            String[] from=new String[]{Constants.DBConstants.EVENTNAME, Constants.DBConstants.START_TIME,Constants.DBConstants.END_TIME};
            int[] to= new int[]{R.id.event_name,R.id.from,R.id.to};
            EventAdapter eventAdapter = new EventAdapter(this,R.layout.inside_listview_event, cursor,from,to,0);
            listView.setAdapter(eventAdapter);
        }
        else{
            Log.d("mdbEditor",mDBEditor.getNumberOfEvents(this,dataBusyCalendar));
        }
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
                id = dataBaseUniqIdGenerator.generateNewId();
                oneStartTimer();
                oneEndTimer();
                mDBEditor.setDB(getApplicationContext(),eventNameString,dataBusyCalendar,fromTimeString,toTimeString,alertNameString,changeStatusString,eventDescriptionString,String.valueOf(vkVariable),String.valueOf(facebookVariable), String.valueOf(id));
                startActivity(new Intent(EventActivity.this,MainActivity.class));
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventActivity.this,MainActivity.class));
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

    private void startTimePickerFragment() {
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new StartTimePickerPopup();
        newFragment.show(fm, Constants.OtherConstants.TIMEPICKER_NAME);
    }
    private void endTimePickerFragment() {
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new EndTimePickerPopup();
        newFragment.show(fm, Constants.OtherConstants.TIMEPICKER_NAME);
    }

    public void oneStartTimer(){
        Context context= this.getApplicationContext();
        if(alarm!=null&&enterAlertName!=null){
            Toast.makeText(context,"Alarm at "+ String.valueOf(id),Toast.LENGTH_LONG).show();
            alarm.setAlarm(context,id,timeUtils.getMillisFromDate(dataBusyCalendar)+timeUtils.getMillis(String.valueOf(chooseStartTime.getText())),changeStatusString);
        }else{
            Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void oneEndTimer(){
        Context context= this.getApplicationContext();
        if(alarm!=null&&enterAlertName!=null){
            endAlarm.setAlarm(context,id,timeUtils.getMillisFromDate(dataBusyCalendar)+timeUtils.getMillis(String.valueOf(chooseEndTime.getText())));
        }else{
            Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }
}
