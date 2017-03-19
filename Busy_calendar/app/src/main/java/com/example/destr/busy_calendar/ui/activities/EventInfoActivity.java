package com.example.destr.busy_calendar.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;


public class EventInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        getSupportActionBar().hide();
        TextView eventName =(TextView) findViewById(R.id.name);
        TextView fromTime =(TextView) findViewById(R.id.from_time);
        TextView toTime =(TextView) findViewById(R.id.to_time);
        TextView alert =(TextView) findViewById(R.id.alert);
        TextView status =(TextView) findViewById(R.id.status);
        TextView description =(TextView) findViewById(R.id.description);
        TextView socialsVk =(TextView) findViewById(R.id.socials_vk);
        TextView socialsFacebook =(TextView) findViewById(R.id.socials_facebook);
        String[] strings =getIntent().getStringArrayExtra(Constants.OtherConstants.STRING_ARRAY_EXTRA);
        eventName.setText(strings[0]);
        status.setText(strings[1]);
        description.setText(strings[2]);
        fromTime.setText(strings[3]);
        toTime.setText(strings[4]);
        socialsVk.setText(strings[5]);
        socialsFacebook.setText(strings[6]);
        alert.setText(strings[7]);
    }
}
