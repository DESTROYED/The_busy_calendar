package com.example.destr.busy_calendar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;

import com.example.destr.busy_calendar.constants.Constants;

import static android.content.Context.AUDIO_SERVICE;

public class SoundChangeSettings {

    public SoundChangeSettings(Context pContext, int ringerMode, boolean streamSystem, boolean streamNotification, boolean streamAlarm, boolean streamRing, boolean streamMusic) {
        useSetings(pContext, ringerMode, streamSystem,streamNotification,streamAlarm,streamRing,streamMusic);
    }
    public SoundChangeSettings(Context pContext) {
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(pContext);
        boolean ringerSM = logTest.getBoolean(Constants.Settings.RINGER_SILENT_MOD, false);
        int ringerS;
        if (ringerSM) {
            ringerS = AudioManager.RINGER_MODE_SILENT;
        } else {
            ringerS = AudioManager.RINGER_MODE_NORMAL;
        }
        boolean systemS = logTest.getBoolean(Constants.Settings.SYSTEM_SOUNDS, false);
        boolean notificationS = logTest.getBoolean(Constants.Settings.NOTIFICATIONS, false);
        boolean alarmS = logTest.getBoolean(Constants.Settings.ALARM_SOUND, false);
        boolean phoneS = logTest.getBoolean(Constants.Settings.PHONE_RING, false);
        boolean musicS = logTest.getBoolean(Constants.Settings.MUSICK_SOUND, false);
        useSetings(pContext, ringerS, systemS, notificationS, alarmS, phoneS, musicS);
    }
    private void useSetings(Context pContext, int ringerMode, boolean streamSystem, boolean streamNotification, boolean streamAlarm, boolean streamRing, boolean streamMusic) {

        AudioManager audioManager = (AudioManager) pContext.getSystemService(AUDIO_SERVICE);

        audioManager.setRingerMode(ringerMode);

        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, streamSystem);

        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, streamNotification);

        audioManager.setStreamMute(AudioManager.STREAM_ALARM, streamAlarm);

        audioManager.setStreamMute(AudioManager.STREAM_RING, streamRing);

        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, streamMusic);

    }

}
