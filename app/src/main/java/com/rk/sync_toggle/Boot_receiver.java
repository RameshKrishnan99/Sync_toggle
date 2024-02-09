package com.rk.sync_toggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

public class Boot_receiver extends BroadcastReceiver {
    public Boot_receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Boot_receiver", "started");
        Alarmactivater.scheduleAlarm(context, Constants._interval_time);
        Log.e("Boot_receiver", "done");
    }
}
