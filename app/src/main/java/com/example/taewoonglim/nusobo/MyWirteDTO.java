package com.example.taewoonglim.nusobo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taewoong Lim on 2017-11-28.
 */
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

public class MyWirteDTO {


    public String description;
    public String userNick;
    public String myprofile_imageUrl;
    public String timeStamp;
    public String uid;

    public MyWirteDTO(){
        //디폴트

    }


    public MyWirteDTO(String _d, String _uN, String _myp, String _ts, String _uid){


        this.description = _d;
        this.userNick = _uN;
        this.myprofile_imageUrl = _myp;
        this.timeStamp = _ts;
        this.uid = _uid;
    }
}
