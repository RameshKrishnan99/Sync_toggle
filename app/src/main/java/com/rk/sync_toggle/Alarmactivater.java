package com.rk.sync_toggle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by user1 on 10/11/16.
 */
public class Alarmactivater {
    public static void scheduleAlarm(Context context, int interval_time) {
        Log.e("alarm", "started");
        Intent intent = new Intent(context, AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long start_time = System.currentTimeMillis() + 4000;
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, start_time, interval_time, pIntent);
    }

    public static void cancelAlarm(Context context) {
        Log.e("alarm", "stopped");
        Intent intent = new Intent(context, AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
