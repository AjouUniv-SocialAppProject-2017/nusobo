package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-12.
 */

public class UserModelAuth {

    public String userName;
    public String profileImageUrl;

    public UserModelAuth(){


    }

    public UserModelAuth(String _username, String _profileimageurl){

        this.userName = _username;
        this.profileImageUrl =_profileimageurl;
    }


}
