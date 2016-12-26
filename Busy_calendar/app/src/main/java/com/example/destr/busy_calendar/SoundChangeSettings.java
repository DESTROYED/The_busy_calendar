package com.example.destr.busy_calendar;

import android.content.Context;
import android.media.AudioManager;

import static android.content.Context.AUDIO_SERVICE;

public class SoundChangeSettings {

        public SoundChangeSettings(Context pContext){


            //useSetings();
        }

    private void useSetings(Context pContext,int ringerMode , boolean streamSystem, boolean streamNotification,boolean streamAlarm, boolean streamRing, boolean streamMusic) {

        AudioManager audioManager = (AudioManager) pContext.getSystemService(AUDIO_SERVICE);

        audioManager.setRingerMode(ringerMode/*AudioManager.RINGER_MODE_SILENT*//*RINGER_MODE_NORMAL*/);

        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, streamSystem);

        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, streamNotification);

        audioManager.setStreamMute(AudioManager.STREAM_ALARM, streamAlarm);

        audioManager.setStreamMute(AudioManager.STREAM_RING, streamRing);

        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, streamMusic);

    }

}
