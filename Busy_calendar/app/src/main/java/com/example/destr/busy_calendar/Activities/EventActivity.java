package com.example.destr.busy_calendar.Activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.destr.busy_calendar.Adapters.GridCellAdapter;
import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.Fragments.EndTimePicker;
import com.example.destr.busy_calendar.Fragments.StartTimePicker;

import java.util.List;


public class EventActivity extends AppCompatActivity{
    private ImageButton closeButton;
    private TextView chooseStartTime;
    private ImageButton saveButton;
    private TextView chooseEndTime;
    private CheckBox allDayCheckBox;
    private CheckBox alarmCheckBox;
    private CheckBox statusCheckBox;
    private EditText eventName;
    private EditText editTextStatus;
    private android.support.v7.widget.AppCompatAutoCompleteTextView mCombotext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        getSupportActionBar().hide();
        alarmCheckBox=(CheckBox) findViewById(R.id.alert_checkbox);
        allDayCheckBox = (CheckBox) findViewById(R.id.checkbox_time);
        statusCheckBox= (CheckBox) findViewById(R.id.status_checkbox);
        editTextStatus= (EditText) findViewById(R.id.checkbox_status);
        eventName=(EditText) findViewById(R.id.event_name);
        mCombotext=(android.support.v7.widget.AppCompatAutoCompleteTextView)findViewById(R.id.combotext_alert); //TODO adapter
        closeButton= (ImageButton) findViewById(R.id.close_event);
        chooseStartTime =(TextView) findViewById(R.id.choose_start_time);
        saveButton=(ImageButton) findViewById(R.id.save_event);
        chooseEndTime=(TextView) findViewById(R.id.choose_end_time);
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new StartTimePicker();
                newFragment.show(fm , "timePicker");
            }
        });
        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new EndTimePicker();
                newFragment.show(fm , "timePicker");
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO bd save
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
                if(isChecked)mCombotext.setVisibility(View.VISIBLE);
                else mCombotext.setVisibility(View.INVISIBLE);
            }
        });
        allDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    chooseEndTime.setVisibility(View.INVISIBLE);
                    chooseStartTime.setVisibility(View.INVISIBLE);
                    findViewById(R.id.to_tw).setVisibility(View.INVISIBLE);
                    findViewById(R.id.from_tw).setVisibility(View.INVISIBLE);
                }
                else{
                    chooseEndTime.setVisibility(View.VISIBLE);
                    chooseStartTime.setVisibility(View.VISIBLE);
                    findViewById(R.id.to_tw).setVisibility(View.VISIBLE);
                    findViewById(R.id.from_tw).setVisibility(View.VISIBLE);
                }
            }
        });
        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editTextStatus.setVisibility(View.VISIBLE);
                else editTextStatus.setVisibility(View.INVISIBLE);
            }
        });


    }
}
