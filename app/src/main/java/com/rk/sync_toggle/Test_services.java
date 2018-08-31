package com.rk.sync_toggle;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class Test_services extends Service {
    IBinder binder = new MyBinder();

    public Test_services() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service","oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service","onStartCommand");
//        return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service","onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

//        throw new UnsupportedOperationException("Not yet implemented");
        Log.e("onBind","onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("onUnbind","onUnbind");
        return super.onUnbind(intent);
//        return true;
    }

    void make_sound() {
//        String what=null;
//        if(what.equals(""))
//            what="1";
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
    }

    class MyBinder extends Binder {
        Test_services getServices() {
            return Test_services.this;
        }
    }
}
