package com.rk.sync_toggle.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rk.sync_toggle.Constants;
import com.rk.sync_toggle.R;
import com.rk.sync_toggle.service.model.MessageDto;
import com.rk.sync_toggle.view_model.MainViewModel;

public class OwnerActivity extends AppCompatActivity {

    private Button btnOn;
    private Button btnOff;
    private MainViewModel viewModel;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        btnOn = (Button) findViewById(R.id.button_on);
        btnOff = (Button) findViewById(R.id.button_off);
        infoText = (TextView) findViewById(R.id.info_text);
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.getInstance(OwnerActivity.this).isNetworkConnected())
                    sendNotification("Hotspot_on");
                else
                    sendSms("wifi_on");
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.getInstance(OwnerActivity.this).isNetworkConnected())
                    sendNotification("Hotspot_off");
                else
                    sendSms("wifi_off");

            }
        });
    }

    private void sendSms(String msg) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("smsto:9360031887"));
//        sendIntent.putExtra("address", "9360031887");
        sendIntent.putExtra("sms_body", msg);
        startActivity(sendIntent);
    }

    private void sendNotification(final String message) {
        viewModel.getNotificationLiveData(message).observe(this, new Observer<MessageDto>() {
            @Override
            public void onChanged(MessageDto messageDto) {
                if (messageDto.getSuccess().equals("1"))
                    infoText.setText("Notification sent successfully");
                else
                    infoText.setText("Notification failed");
            }
        });
    }
}