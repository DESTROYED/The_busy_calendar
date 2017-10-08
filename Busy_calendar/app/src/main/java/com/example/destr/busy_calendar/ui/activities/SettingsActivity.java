package com.example.destr.busy_calendar.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.utils.SoundChangeSettings;
import com.example.destr.busy_calendar.utils.ThemeManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.ringer_silent_mode)
    Switch ringerSilentMode;

    @BindView(R.id.system_sounds)
    Switch systemSounds;

    @BindView(R.id.notifications)
    Switch notifications;

    @BindView(R.id.alarm_sound)
    Switch alarmSoundSwitch;

    @BindView(R.id.phone_ring)
    Switch phoneRing;

    @BindView(R.id.music_sound)
    Switch musicSound;

    @BindView(R.id.save_settings)
    ImageButton acceptButton;

    @BindView(R.id.set_gray_theme)
    Button grayTheme;

    @BindView(R.id.set_green_theme)
    Button greenTheme;

    @BindView(R.id.toolbar)
    View toolbar;

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
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor logTestEditor = logTest.edit();
        boolean ringerSM=logTest.getBoolean(Constants.Settings.RINGER_SILENT_MOD,false);
        boolean systemS=logTest.getBoolean(Constants.Settings.SYSTEM_SOUNDS,false);
        boolean notificationS=logTest.getBoolean(Constants.Settings.NOTIFICATIONS,false);
        boolean alarmS=logTest.getBoolean(Constants.Settings.ALARM_SOUND,false);
        boolean phoneS=logTest.getBoolean(Constants.Settings.PHONE_RING,false);
        boolean musicS=logTest.getBoolean(Constants.Settings.MUSICK_SOUND,false);

        setContentView(R.layout.activity_silence);
        ButterKnife.bind(this);
        new ThemeManager(getApplicationContext()).setToolbar(toolbar);

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
        grayTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logTestEditor.putString(Constants.Settings.THEME_CHECK,Constants.Settings.THEME_GRAY).apply();
                recreate();
            }
        });
        greenTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logTestEditor.putString(Constants.Settings.THEME_CHECK,Constants.Settings.THEME_GREEN).apply();
                recreate();
            }
        });
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
                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}
