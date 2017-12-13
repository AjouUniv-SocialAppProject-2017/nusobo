package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-06.
 */

public class AccountDTO {

    public String date;
    public String money;

    public AccountDTO(){
        //디폴트
    }

    public AccountDTO(String _d, String _m){

        this.date = _d;
        this.money = _m;

    }


}
