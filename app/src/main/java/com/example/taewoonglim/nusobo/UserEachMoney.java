package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-11.
 */

public class UserEachMoney {

    public String store;
    public String _money;


    public UserEachMoney(){
        //디폴트
    }

    public UserEachMoney(String _s, String _m){

        this.store = _s;
        this._money = _m;
    }
}
