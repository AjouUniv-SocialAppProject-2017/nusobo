package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-11.
 */

//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

//user 각각의 돈에 대한 model description입니다.
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
