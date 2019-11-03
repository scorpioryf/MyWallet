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
    public String getValue(){
        String string =  "￥ " + dValue;
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

    public String getTime(){
        String string = iHour + ":" + iMinute;
        return string;
    }
    public void setComment(String string){
        sComment = string;
    }

}
