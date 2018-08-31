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
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e("Boot_receiver", "started");
//        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
//        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
//        context.startService(new Intent(context,Network_service.class));
        Alarmactivater.scheduleAlarm(context, Constants._interval_time);
        Log.e("Boot_receiver", "done");
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
