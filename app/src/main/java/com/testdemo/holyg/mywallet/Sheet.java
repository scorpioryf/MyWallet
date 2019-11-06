package com.testdemo.holyg.mywallet;

import java.io.Serializable;


public class Sheet implements Serializable {
    private int iType;
    private double dValue;
    private int iYear;
    private int iMonth;
    private int iDay;
    private int iHour;
    private int iMinute;
    private String sComment;
    private int iWay;


    public Sheet(int Type,double Value,int Year,int Month,int Day,int Hour,int Minute,int Way,String string){
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
    public int getType(){
        return iType;
    }

    public void setType(int type){
        iType = type;
    }

    public String getValue(){
        String string = String.valueOf(dValue);
        return string;
    }

    public String getComment(){
        return sComment;
    }

    public int getWay(){
        return iWay;
    }

    public String getDate(){
        String string = iYear + "-" + iMonth + "-" + iDay;
        return string;
    }

    public String getYear() {
        return String.valueOf(iYear);
    }

    public String getMonth() {
        return String.valueOf(iMonth);
    }

    public String getDay() {
        return String.valueOf(iDay);
    }

    public String getHour(){
        String sHour;
        if(iHour<10){
            sHour = "0" + iHour;
        }
        else {
            sHour = String.valueOf(iHour);
        }
        return sHour;
    }

    public String getMinute(){
        String sMinute;
        if(iMinute<10){
            sMinute = "0"+ iMinute;
        }
        else {
            sMinute=  String.valueOf(iMinute);
        }
        return sMinute;
    }

    public String getTime(){
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
        String string = sHour + ":" + sMinute;
        return string;
    }
    public void setComment(String string){
        sComment = string;
    }

    public void setWay(int way){
        iWay = way;
    }

    public void setAmount(double amount){
        dValue = amount;
    }

}
