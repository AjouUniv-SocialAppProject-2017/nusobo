package com.example.taewoonglim.nusobo;

import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-07.
 */

//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

//user에 대한 Model description입니다.
public class User {

    //public String year;
   // public String month;
  //  public String day;
    public String total_Money;
    public String date;
  //  public UserEachMoney eachMoney;

    public User(){

    }

    public User(String _date, String _money){

    //    this.date = _date;
     //   this.money = _money;
    }

    public User(String _year, String _month, String _day, UserEachMoney _eachM){


        int year = Integer.parseInt(_year);
        int month = Integer.parseInt(_month);
        int day = Integer.parseInt(_day);
    //    eachMoney =_eachM;
        //this.money = money;

        int _total = 0;
        /*
        for(int i = 0; i < eachMoney.size(); i++){

            _total = _total + Integer.parseInt(eachMoney.get(i)._money);
        }
*/
        this.total_Money = String.valueOf(_total);
        this.date = year + "_" + String.format("%02d", month) + "_" + String.format("%02d", day);
    }



    public User(String _year, String _month, String _day, String _money){


        int year = Integer.parseInt(_year);
        int month = Integer.parseInt(_month);
        int day = Integer.parseInt(_day);
      //  eachMoney =_eachM;
        this.total_Money = _money;

      //  int _total = 0;
        /*
        for(int i = 0; i < eachMoney.size(); i++){

            _total = _total + Integer.parseInt(eachMoney.get(i)._money);
        }
*/
       // this.total_Money = String.valueOf(_total);
        this.date = year + "_" + String.format("%02d", month) + "_" + String.format("%02d", day);
    }


}
