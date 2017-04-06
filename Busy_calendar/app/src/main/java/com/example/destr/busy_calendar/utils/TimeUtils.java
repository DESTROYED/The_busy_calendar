package com.example.destr.busy_calendar.utils;

import java.util.Date;

public class TimeUtils {
    public long getMillis(String time){
        String partStringTimes[]=time.split(":");
        int hours = Integer.parseInt(partStringTimes[0]);
        int min= Integer.parseInt(partStringTimes[1]);
        return hours*60*60*1000+min*60*1000;
    }
    public long getMillisFromDate(String date){
        Date notStringDate =new Date(date);
        return notStringDate.getTime();
    }
}
