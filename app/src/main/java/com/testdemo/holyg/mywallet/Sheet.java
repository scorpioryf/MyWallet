package com.testdemo.holyg.mywallet;

import java.io.Serializable;


class Sheet implements Serializable {
    private int iType;
    private double dValue;
    private int iYear;
    private int iMonth;
    private int iDay;
    private int iHour;
    private int iMinute;
    private String sComment;
    private int iWay;


    Sheet(int Type,double Value,int Year,int Month,int Day,int Hour,int Minute,int Way,String string){
        iType = Type;
        dValue = Value;
        iYear = Year;
        iMonth = Month;
        iDay = Day;
        sComment = string;
        iWay = Way;
        iHour = Hour;
        iMinute = Minute;
    }
    int getType(){
        return iType;
    }

    void setType(int type){
        iType = type;
    }

    String getValue(){
        return String.valueOf(dValue);
    }

    String getComment(){
        return sComment;
    }

    int getWay(){
        return iWay;
    }

    String getDate(){
        return iYear + "-" + iMonth + "-" + iDay;
    }

    String getYear() {
        return String.valueOf(iYear);
    }

    String getMonth() {
        return String.valueOf(iMonth);
    }

    String getDay() {
        return String.valueOf(iDay);
    }

    String getHour(){
        String sHour;
        if(iHour<10){
            sHour = "0" + iHour;
        }
        else {
            sHour = String.valueOf(iHour);
        }
        return sHour;
    }

    String getMinute(){
        String sMinute;
        if(iMinute<10){
            sMinute = "0"+ iMinute;
        }
        else {
            sMinute=  String.valueOf(iMinute);
        }
        return sMinute;
    }

    String getTime(){
        String sHour;
        String sMinute;
        if(iHour<10){
            sHour = "0" + iHour;
        }
        else {
            sHour = String.valueOf(iHour);
        }
        if(iMinute<10){
            sMinute = "0"+ iMinute;
        }
        else {
            sMinute=  String.valueOf(iMinute);
        }
        return sHour + ":" + sMinute;
    }
    void setComment(String string){
        sComment = string;
    }

    void setWay(int way){
        iWay = way;
    }

    void setAmount(double amount){
        dValue = amount;
    }

    void setHour(int hour){
        iHour = hour;
    }

    void setMinute(int minute){
        iMinute = minute;
    }

    void setYear (int year){
        iYear = year;
    }

    void setMonth (int month){
        iMonth = month;
    }

    void setDay (int day){
        iDay = day;
    }
}
