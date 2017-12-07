package com.example.taewoonglim.nusobo;

/**
 * Created by taewoong Lim on 2017-12-07.
 */

public class User {

    //public String year;
   // public String month;
  //  public String day;
    public String money;
    public String date;

    public User(){

    }

    public User(String _date, String _money){

        this.date = _date;
        this.money = _money;
    }

    public User(String _year, String _month, String _day, String money){

       // this.year = year;
      //  this.month = month;
     //   this.day = day;
        int year = Integer.parseInt(_year);
        int month = Integer.parseInt(_month);
        int day = Integer.parseInt(_day);

        this.money = money;

        this.date = year + "_" + String.format("%02d", month) + "_" + String.format("%02d", day);
    }



}
