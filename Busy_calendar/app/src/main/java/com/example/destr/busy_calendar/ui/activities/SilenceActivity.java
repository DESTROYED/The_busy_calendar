package com.example.destr.busy_calendar.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.utils.SoundChangeSettings;

public class SilenceActivity extends AppCompatActivity {

    private Switch ringerSilentMode;
    private Switch systemSounds;
    private Switch notifications;
    private Switch alarmSoundSwitch;
    private Switch phoneRing;
    private Switch musicSound;
    private ImageButton acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor logTestEditor = logTest.edit();
        boolean ringerSM=logTest.getBoolean(Constants.Settings.RINGER_SILENT_MOD,false);
        boolean systemS=logTest.getBoolean(Constants.Settings.SYSTEM_SOUNDS,false);
        boolean notificationS=logTest.getBoolean(Constants.Settings.NOTIFICATIONS,false);
        boolean alarmS=logTest.getBoolean(Constants.Settings.ALARM_SOUND,false);
        boolean phoneS=logTest.getBoolean(Constants.Settings.PHONE_RING,false);
        boolean musicS=logTest.getBoolean(Constants.Settings.MUSICK_SOUND,false);

        setContentView(R.layout.activity_silence);
        initItems();
        try {
            ringerSilentMode.setChecked(ringerSM);
            systemSounds.setChecked(systemS);
            notifications.setChecked(notificationS);
            alarmSoundSwitch.setChecked(alarmS);
            phoneRing.setChecked(phoneS);
            musicSound.setChecked(musicS);
        }catch (NullPointerException e){
            ringerSilentMode.setChecked(false);
            systemSounds.setChecked(false);
            notifications.setChecked(false);
            alarmSoundSwitch.setChecked(false);
            phoneRing.setChecked(false);
            musicSound.setChecked(false);
        }
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logTestEditor.putBoolean(Constants.Settings.RINGER_SILENT_MOD,ringerSilentMode.isChecked() );
                logTestEditor.putBoolean(Constants.Settings.SYSTEM_SOUNDS,systemSounds.isChecked() );
                logTestEditor.putBoolean(Constants.Settings.NOTIFICATIONS,notifications.isChecked() );
                logTestEditor.putBoolean(Constants.Settings.ALARM_SOUND,alarmSoundSwitch.isChecked() );
                logTestEditor.putBoolean(Constants.Settings.PHONE_RING,phoneRing.isChecked() );
                logTestEditor.putBoolean(Constants.Settings.MUSICK_SOUND,musicSound.isChecked() );
                logTestEditor.apply();
                new SoundChangeSettings(getApplicationContext());
                finish();
            }
        });
    }

    private void initItems() {
        ringerSilentMode=(Switch) findViewById(R.id.ringer_silent_mode);
        systemSounds=(Switch) findViewById(R.id.system_sounds);
        notifications=(Switch) findViewById(R.id.notifications);
        alarmSoundSwitch=(Switch) findViewById(R.id.alarm_sound);
        phoneRing=(Switch) findViewById(R.id.phone_ring);
        musicSound=(Switch) findViewById(R.id.music_sound);
        acceptButton=(ImageButton) findViewById(R.id.save_settings);
    }
}
