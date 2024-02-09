package com.rk.sync_toggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.fitc.wifihotspot.HotSpotIntentService;

public class Incomingsms_receiver extends BroadcastReceiver {
    public Incomingsms_receiver() {
    }

    private static final String TAG = "Incomingsms_receiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e(TAG, "Incomingsms_receiver");
        // Retrieves a map of extended data from the intent.
        Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Log.d(TAG, "onReceive: "+message);
                    String msg = "Could not get location";
//                    if (phoneNumber.equals(Constants.getInstance(context).phoneNo)) {
                        if (message.equalsIgnoreCase("il")) {
//                            Constants.getInstance(context).deleteSMS(phoneNumber);
                            Location dto = Constants.getInstance(context).getMylocation();
                            if (dto != null)
                                msg = "http://maps.google.com/maps?q=loc: " + dto.getLatitude() + "," + dto.getLongitude() + " \n Address :  " + Constants.getInstance(context).getAddress(dto.getLatitude(), dto.getLongitude());
                            Constants.getInstance(context).sendSMS(context, phoneNumber, msg);
                        } else if (message.equalsIgnoreCase("el")) {
//                            Constants.getInstance(context).phoneNumber = phoneNumber;
                            Constants.getInstance(context).request_location_update(context);
                        } else if (message.equalsIgnoreCase("Bright")) {
//                            Constants.getInstance(context).increase_brightness(150, mode);
                        } else if (message.equalsIgnoreCase("wifi_on")) {
//                            Intent onIntent = new Intent(context.getString(com.fitc.wifihotspot.R.string.intent_action_turnon));
//                            com.fitc.wifihotspot.MainActivity.sendImplicitBroadcast(context,onIntent);
                            Uri uri = new Uri.Builder().scheme(context.getString(com.fitc.wifihotspot.R.string.intent_data_scheme)).authority(context.getString(com.fitc.wifihotspot.R.string.intent_data_host_turnon)).build();
                            Intent onIntent = new Intent(Intent.ACTION_VIEW);
                            onIntent.setData(uri);
                            onIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            HotSpotIntentService.start(context, onIntent);
                        } else if (message.equalsIgnoreCase("wifi_off")) {
//                            Intent offIntent = new Intent(context.getString(com.fitc.wifihotspot.R.string.intent_action_turnoff));
//                            com.fitc.wifihotspot.MainActivity.sendImplicitBroadcast(context,offIntent);
                            Uri uri = new Uri.Builder().scheme(context.getString(com.fitc.wifihotspot.R.string.intent_data_scheme)).authority(context.getString(com.fitc.wifihotspot.R.string.intent_data_host_turnoff)).build();
                            Intent onIntent = new Intent(Intent.ACTION_VIEW);
                            onIntent.setData(uri);
                            onIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            HotSpotIntentService.start(context, onIntent);
                        }

//                    if (phoneNumber.equals("+919941191299") && message.equals("Get_Location")) {
//
//                    }
//                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                        // Show alert
                    /*int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();*/

//                    } // end for loop
                }
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }


}
