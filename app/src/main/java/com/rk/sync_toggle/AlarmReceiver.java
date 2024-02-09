package com.rk.sync_toggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 3;
    private Context context;
    private String my_phoneId = "a654034904db8133";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Receiver", "AlarmReceiver");
        this.context = context;
        sync_toggle();
    }

    private void sync_toggle() {
        if (!Constants.getInstance(context).isMyServiceRunning(Network_service.class))
            context.startService(new Intent(context, Network_service.class));
    }


    private void create_sound() {
        try {
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean is_hotspot_enabled() {
       /*int AP_STATE_DISABLING = 10;
       int AP_STATE_DISABLED = 11;
       int AP_STATE_ENABLING = 12;
       int AP_STATE_FAILED = 14;*/
        int AP_STATE_ENABLED = 13;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Method method = null;
        try {
            method = wifiManager.getClass().getDeclaredMethod("getWifiApState");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        int actualState = 0;
        try {
            actualState = (Integer) method.invoke(wifiManager, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (actualState == AP_STATE_ENABLED)
            return true;
        return false;

    }
}