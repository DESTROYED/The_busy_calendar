package com.example.destr.busy_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;


public class EventActivity extends AppCompatActivity{
    private ImageButton closebutton;
    private Button startTime;
    private Button endTime;
    private PopupWindow startTimePick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        getSupportActionBar().hide();
        final Intent startTimePick = new Intent(EventActivity.this, StartTimePicker.class);
        closebutton= (ImageButton) findViewById(R.id.close_event);
        startTime = (Button) findViewById(R.id.btn_start_time);
        endTime=(Button) findViewById(R.id.btn_end_name);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(startTimePick);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
