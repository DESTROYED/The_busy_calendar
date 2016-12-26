package com.example.destr.busy_calendar;

public class AlarmUtility {
    //TODO add database view
    //TODO FIX!
        /*public static void setAlarm(int id, String typeReminderText, long timeInMillis, int typeReminder) {
            AlarmManager alarmManager = (AlarmManager) App.getInstance().getSystemService(Context.ALARM_SERVICE);

            PendingIntent pendingIntent = PendingIntent
                    .getBroadcast(
                            App.getInstance(),
                            id,
                            new Intent(App.getInstance(), ReminderReceiver.class)
                                    .putExtra(EXTRA_ID_REMINDER_KEY, id)
                                    .putExtra(EXTRA_TEXT_TYPE_REMINDER_KEY, typeReminderText)
                                    .putExtra(TYPE_REMINDER_KEY, typeReminder),
                            PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            }

            LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcastSync(new Intent(BROADCAST_ADD_NEW_REMINDER));
        }*/
    }

