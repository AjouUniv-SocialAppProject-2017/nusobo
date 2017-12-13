package com.example.taewoonglim.nusobo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Created by woojin on 2017-11-10.
 */

public class sms extends BroadcastReceiver {

    private boolean flag;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;



    public void onReceive(Context context, Intent intent) {

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String body="";
        String sender;
        Date curDate;
        flag = false;

        if (bundle != null) {
            Object[] smsExtra = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[smsExtra.length];

            for (int i = 0; i < msgs.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                body = sms.getMessageBody().toString();
                sender = sms.getOriginatingAddress().toString();
                curDate =new Date(sms.getTimestampMillis());
                Toast.makeText(context, "From123 :" + sender + "\n" + "body:" + body, Toast.LENGTH_LONG).show();
                if(sender.equals("01076255866")){
                    flag = true;
                }else if(sender.equals("01099760781")){
                    flag = true;
                }
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



                /*
                [Web발신]
                KB국민체크(0*3*)
                박*진님
                11/11 13:20
                4,600원
                투썸플레이스 이 사용
                 */
                Toast.makeText(context, "store" + store2, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "month" + month, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "day" + day, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "전화번호 ㅇㅋ", Toast.LENGTH_SHORT).show();

                uploadFireBase("2017", month, day, amount, store2);


            }}
    }

    public void uploadFireBase(String _year, String _month, String _day, String _money, String _content){

        AccountContentDescriptionDTO temp_accountContentDescriptionDTO = new AccountContentDescriptionDTO();
        User user = new User(_year, _month, _day, _money);


        //타임스탬프에 적용하기 위해
        //시간을 받아올 때는 유니스 시간이므로 사람이 알아볼 수 있도록 변환해주어야한다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        //타임스탬프
        long nowUnixtime = System.currentTimeMillis();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date date = new Date(nowUnixtime);

        temp_accountContentDescriptionDTO.money = _money;
        temp_accountContentDescriptionDTO.store = _content;
        temp_accountContentDescriptionDTO.date = user.date;
        temp_accountContentDescriptionDTO.timeStamp = simpleDateFormat.format(date);



        String myEmail = mAuth.getCurrentUser().getEmail();
        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");
        mDatabase.getReference().child("users").child(myEmail).push().setValue(temp_accountContentDescriptionDTO);




    }
}