package com.example.taewoonglim.nusobo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taewoong Lim on 2017-11-13.
 */
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/
public class ImageDTO {

    public String imageUrl;
    public String title;
    public String description;
    public String uid;
    public String userId;

    public String timeStamp;


    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

}
