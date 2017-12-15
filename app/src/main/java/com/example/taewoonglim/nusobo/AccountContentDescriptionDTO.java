package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-11.
 */

public class AccountContentDescriptionDTO {

    public String store;
    public String money;
    public String date;
    public String timeStamp;


    public AccountContentDescriptionDTO(){

        //디폴트

    }

    public AccountContentDescriptionDTO(String s, String m){

        this.store = s;
        this.money = m;
    }


    public AccountContentDescriptionDTO(String s, String m, String d, String t){

        this.store = s;
        this.money = m;
        this.date = d;
        this.timeStamp = t;

    }


}
