package com.example.weatherapp;

import java.io.Serializable;

public class DailyWeatherData implements Serializable {

    private final String day;
    private final String time;
    private final  String temp;
    private final String desc;

    private final String icon;


    DailyWeatherData(String day, String time, String temp, String desc, String icon){
        this.day = day;
        this.time = time;
        this.temp = temp;
        this.desc = desc;
        this.icon = icon;
    }

    public String getTemp(){
        return this.temp;
    }

    public String getIcon(){
        return this.icon;
    }

    public String getDay(){
        return this.day;
    }


    public String getTime(){
        return this.time;
    }

    public String getDesc(){
        return this.desc;
    }
}
