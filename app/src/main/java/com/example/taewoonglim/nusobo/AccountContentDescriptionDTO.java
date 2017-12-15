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
//account description model 입니다.
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
