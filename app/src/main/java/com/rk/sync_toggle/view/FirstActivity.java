package com.rk.sync_toggle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rk.sync_toggle.Constant;
import com.rk.sync_toggle.MainActivity;
import com.rk.sync_toggle.R;

import java.security.acl.Owner;

public class FirstActivity extends AppCompatActivity {

    private Button btnOwner,btnSlave;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(
                "com.rk.sync_toggle", Context.MODE_PRIVATE);
//        prefs.edit().clear().apply();
        if(prefs.getString(Constant.user,"").equals(Constant.owner)){
            startActivity(new Intent(this, OwnerActivity.class));
            finish();
            return;
        } else if(prefs.getString(Constant.user,"").equals(Constant.slave)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_first);
        btnOwner = (Button) findViewById(R.id.button_owner);
        btnSlave = (Button) findViewById(R.id.button_slave);
        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreference("owner");
            }
        });

        btnSlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreference("slave");
            }
        });
    }

    private void savePreference(String user) {
        prefs.edit().putString(Constant.user,user).apply();
        if(prefs.getString(Constant.user,"").equals(Constant.owner)){
            startActivity(new Intent(this, OwnerActivity.class));
        } else if(prefs.getString(Constant.user,"").equals(Constant.slave)){
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}