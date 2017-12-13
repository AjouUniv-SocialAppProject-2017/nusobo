package com.example.taewoonglim.nusobo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taewoong Lim on 2017-11-13.
 */

public class ImageDTO {

    public String imageUrl;
    public String title;
    public String description;
    public String uid;
    public String userId;

    public String timeStamp;


    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();


    public ImageDTO(){

        //디폴트
    }


    public ImageDTO(String _imageurl, String _title, String _description, String _uid, String _userid, String _timestamp, int _starcount, Map<String, Boolean> _s){


        this.imageUrl = _imageurl;
        this.title = _title;
        this.description = _description;
        this.uid = _uid;
        this.userId = _userid;
        this.timeStamp = _timestamp;
        this.starCount = _starcount;
        this.stars = _s;


    }

}
