package com.example.taewoonglim.nusobo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by woojin on 2017-11-10.
 */

public class sms extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null) {
            Object[] smsExtra = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[smsExtra.length];

            for (int i = 0; i < msgs.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                String body = sms.getMessageBody().toString();
                String sender = sms.getOriginatingAddress().toString();
                Toast.makeText(context, "From123 :" + sender + "\n" + "body:" + body, Toast.LENGTH_LONG).show();
            }
        }
    }
}