package com.example.destr.busy_calendar.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;

import java.util.Calendar;

public class EndTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView outTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        outTime = (TextView) getActivity().findViewById(R.id.choose_end_time);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        outTime.setText(String.valueOf(hourOfDay) + Constants.OtherConstants.COLON + String.valueOf(minute));
    }
}