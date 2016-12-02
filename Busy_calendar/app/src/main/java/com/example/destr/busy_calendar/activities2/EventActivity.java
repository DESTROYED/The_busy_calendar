package com.example.destr.busy_calendar.activities2;

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
import com.example.destr.busy_calendar.fragments.EndTimePicker;
import com.example.destr.busy_calendar.fragments.StartTimePicker;


public class EventActivity extends AppCompatActivity {
    private TextView chooseStartTime;
    private TextView chooseEndTime;
    private CheckBox vkCheckBox;
    private CheckBox facebookCheckBox;
    private EditText editTextStatus;
    private android.support.v7.widget.AppCompatAutoCompleteTextView mCombotext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        getSupportActionBar().hide();
        CheckBox alarmCheckBox = (CheckBox) findViewById(R.id.alert_checkbox);
        CheckBox allDayCheckBox = (CheckBox) findViewById(R.id.checkbox_time);
        CheckBox statusCheckBox = (CheckBox) findViewById(R.id.status_checkbox);
        editTextStatus = (EditText) findViewById(R.id.checkbox_status);
        EditText eventName = (EditText) findViewById(R.id.event_name);
        mCombotext = (android.support.v7.widget.AppCompatAutoCompleteTextView) findViewById(R.id.combotext_alert);

        ImageButton closeButton = (ImageButton) findViewById(R.id.close_event);
        chooseStartTime = (TextView) findViewById(R.id.choose_start_time);
        ImageButton saveButton = (ImageButton) findViewById(R.id.save_event);
        chooseEndTime = (TextView) findViewById(R.id.choose_end_time);
        CheckBox socials = (CheckBox) findViewById(R.id.checkbox_social);
        vkCheckBox = (CheckBox) findViewById(R.id.checkbox_vk);
        facebookCheckBox = (CheckBox) findViewById(R.id.checkbox_facebook);
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new StartTimePicker();
                newFragment.show(fm, "timePicker");
            }
        });
        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new EndTimePicker();
                newFragment.show(fm, "timePicker");
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (isChecked) mCombotext.setVisibility(View.VISIBLE);
                else mCombotext.setVisibility(View.GONE);
            }
        });
        allDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
        });
        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextStatus.setVisibility(View.VISIBLE);
                } else {
                    editTextStatus.setText(null);
                    editTextStatus.setVisibility(View.GONE);
                }
            }
        });
        socials.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
        });

    }


}
