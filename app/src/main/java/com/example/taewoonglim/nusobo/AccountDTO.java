package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-06.
 */
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/
//가계부의 date와 money를 받는 model입니다.
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
