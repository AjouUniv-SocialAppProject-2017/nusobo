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

}
