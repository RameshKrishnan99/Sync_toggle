package com.rk.sync_toggle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rk.sync_toggle.service.model.UserDto;
import com.rk.sync_toggle.view_model.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView infoText;
    private NetworkChangeReceiver net;
    public Test_services testService;
    public boolean isBound = false;
    private MainViewModel viewModel;
    private Button btnStop;
    public void setMobileDataState(boolean mobileDataEnabled)
    {
        try{
            Settings.Global.putInt(getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON,  1);
           /* boolean isEnabled = android.provider.Settings.System.getInt(
                    getContentResolver(),
                    android.provider.Settings.System.AIRPLANE_MODE_ON, 0) == 1;

            // toggle airplane mode
            android.provider.Settings.System.putInt(
                    getContentResolver(),
                    android.provider.Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

            // Post an intent to reload
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !isEnabled);
            sendBroadcast(intent);*/

//            ConnectivityManager dataManager;
//            dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//            Method dataMtd = dataManager.getClass().getDeclaredMethod("setMobileDataEnabled", boolean.class);
//            dataMtd.setAccessible(true);
//            dataMtd.invoke(dataManager, mobileDataEnabled);
        }catch(Exception ex){
            //Error Code Write Here
            Log.e("setMobileDataState",ex.toString());
        }
    }
    public String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        btnStart = (Button) findViewById(R.id.btn_hide);
        infoText = (TextView) findViewById(R.id.info_text);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (checkANDgetpermission() == 0) {
                    hideStartButton();
                    Alarmactivater.scheduleAlarm(MainActivity.this, Constants._interval_time);
                }
//                setMobileDataState(true);
            }
        });
        btnStop = (Button) findViewById(R.id.btn_cancel);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hideStopButton();
                Alarmactivater.cancelAlarm(MainActivity.this);
                Intent intent = new Intent(MainActivity.this,Network_service.class);
                stopService(intent);
            }
        });
        if (Constants.getInstance(this).isMyServiceRunning(Network_service.class)){
            hideStartButton();
        } else {
            hideStopButton();
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    token = task.getException().getMessage();
                    Log.w("FCM TOKEN Failed", task.getException());
                } else {
                    token = task.getResult().getToken();
                    Log.i("FCM TOKEN", token);
                    viewModel.getCreateUserLiveData(token).observe(MainActivity.this, new Observer<UserDto>() {
                        @Override
                        public void onChanged(UserDto userDto) {
                            if(userDto!=null){
                                infoText.setText(userDto.getResponseData());
                            }
                        }
                    });
                }
            }
        });
    }

    private void hideStopButton() {
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
    }

    private void hideStartButton() {
        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
    }


    private int checkANDgetpermission() {
        String[] network = {Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS};
        ArrayList<String> list = new ArrayList();
        int j = 0;
        for (int i = 0; i < network.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, network[i]) != PackageManager.PERMISSION_GRANTED) {
                list.add(network[i]);
                j++;
            }
        }

        if (list.size() > 0) {
            String[] get = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                get[i] = list.get(i);
            }
            ActivityCompat.requestPermissions(MainActivity.this, get, 0);
        }
        return j;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean permission_granted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    permission_granted = false;
                }
            }
            if (!permission_granted)
                checkANDgetpermission();
            else {
                Constants.getInstance(MainActivity.this).get_WriteSettingPermission(this);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Write permission", "" + resultCode);
        Alarmactivater.scheduleAlarm(MainActivity.this, Constants._interval_time);
    }

}
