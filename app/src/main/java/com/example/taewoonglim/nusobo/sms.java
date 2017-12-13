package com.example.taewoonglim.nusobo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by woojin on 2017-11-10.
 */

public class sms extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String body="";
        String sender;
        Date curDate;
        boolean flag=false;
        if (bundle != null) {
            Object[] smsExtra = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[smsExtra.length];

            for (int i = 0; i < msgs.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                body = sms.getMessageBody().toString();
                sender = sms.getOriginatingAddress().toString();
                curDate =new Date(sms.getTimestampMillis());
                Toast.makeText(context, "From123 :" + sender + "\n" + "body:" + body, Toast.LENGTH_LONG).show();
                if(sender.equals("01076255866")){flag=true;}
                //String year2=Integer.toString(curDate.getYear());
                //Toast.makeText(context, "year    " + year2, Toast.LENGTH_SHORT).show();
            }
            if(flag) {

                StringTokenizer tokenizer = new StringTokenizer(body, "\n");
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                String cal = tokenizer.nextToken();
                StringTokenizer tokenizer3 = new StringTokenizer(cal, "/");
                String month = tokenizer3.nextToken();
                String month2=tokenizer3.nextToken();
                StringTokenizer tokenizer4 = new StringTokenizer(month2, " ");
                String day = tokenizer4.nextToken();
                String amount = tokenizer.nextToken();
                amount = amount.trim();
                amount = amount.replace(",", "");
                //
                amount = amount.replace("원", "");

                Toast.makeText(context, "amount" + amount, Toast.LENGTH_SHORT).show();

                String store = tokenizer.nextToken();
                StringTokenizer tokenizer2 = new StringTokenizer(store, " ");
                //아래꺼가 돈어디서 썻는지

                String store2 = tokenizer2.nextToken();
                Toast.makeText(context, "store" + store2, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "month" + month, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "day" + day, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "전화번호 ㅇㅋ", Toast.LENGTH_SHORT).show();
            }}
    }
}