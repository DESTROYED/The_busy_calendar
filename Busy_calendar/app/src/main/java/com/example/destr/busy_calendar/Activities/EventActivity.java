package com.example.destr.busy_calendar.Activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.Fragments.EndTimePicker;
import com.example.destr.busy_calendar.Fragments.StartTimePicker;


public class EventActivity extends AppCompatActivity{
    private ImageButton closeButton;
    private TextView chooseStartTime;
    private ImageButton saveButton;
    private TextView chooseEndTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        getSupportActionBar().hide();
        closeButton= (ImageButton) findViewById(R.id.close_event);
        chooseStartTime =(TextView) findViewById(R.id.choose_start_time);
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new StartTimePicker();
                newFragment.show(fm , "timePicker");
            }
        });
        chooseEndTime=(TextView) findViewById(R.id.choose_end_time);
        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment newFragment = new EndTimePicker();
                newFragment.show(fm , "timePicker");
            }
        });
        saveButton=(ImageButton) findViewById(R.id.save_event);
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
    }
}
