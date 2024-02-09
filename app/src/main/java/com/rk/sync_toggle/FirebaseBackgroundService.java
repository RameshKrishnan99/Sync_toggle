package com.rk.sync_toggle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.fitc.wifihotspot.HotSpotIntentService;

public class FirebaseBackgroundService  extends WakefulBroadcastReceiver {

    private static final String TAG = "FirebaseService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");

        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                Log.e("FirebaseDataReceiver", "Key: " + key + " Value: " + value);
                if(key.equalsIgnoreCase("gcm.notification.body") && value != null) {
                    String message = value.toString();
                    if (message.equalsIgnoreCase("hotspot_on")) {
//                            Intent onIntent = new Intent(context.getString(com.fitc.wifihotspot.R.string.intent_action_turnon));
//                            com.fitc.wifihotspot.MainActivity.sendImplicitBroadcast(context,onIntent);
                        Uri uri = new Uri.Builder().scheme(context.getString(com.fitc.wifihotspot.R.string.intent_data_scheme)).authority(context.getString(com.fitc.wifihotspot.R.string.intent_data_host_turnon)).build();
                        Intent onIntent = new Intent(Intent.ACTION_VIEW);
                        onIntent.setData(uri);
                        onIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        HotSpotIntentService.start(context, onIntent);
                    } else if (message.equalsIgnoreCase("hotspot_off")) {
//                            Intent offIntent = new Intent(context.getString(com.fitc.wifihotspot.R.string.intent_action_turnoff));
//                            com.fitc.wifihotspot.MainActivity.sendImplicitBroadcast(context,offIntent);
                        Uri uri = new Uri.Builder().scheme(context.getString(com.fitc.wifihotspot.R.string.intent_data_scheme)).authority(context.getString(com.fitc.wifihotspot.R.string.intent_data_host_turnoff)).build();
                        Intent onIntent = new Intent(Intent.ACTION_VIEW);
                        onIntent.setData(uri);
                        onIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        HotSpotIntentService.start(context, onIntent);
                    }
                }
            }
        }
    }
}
