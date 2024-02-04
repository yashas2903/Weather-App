package com.example.weatherapp;

public class MultipleDaysWeatherData {

    private  final  String mnmTemp;
    private  final String dateTime;
    private final String desc;
    private final String pop;
    private final String uvIndex;
    private final String icon;
    private final String morning;
    private final String afternoon;
    private final String evening;
    private final String night;


    MultipleDaysWeatherData(String mnmTemp, String dateTime, String desc, String pop, String uvIndex, String icon, String morning,
                            String afternoon, String evening, String night){
        this.mnmTemp = mnmTemp;
        this.dateTime= dateTime;
        this.desc = desc;
        this.pop=pop;
        this.uvIndex= uvIndex;
        this.icon = icon;
        this.morning = morning;
        this.afternoon =afternoon;
        this.evening = evening;
        this.night = night;

    }


    String getDesc(){
        return desc;
    }

    String getMnmTemp(){
        return mnmTemp;
    }

    String getDateTime(){
        return dateTime;
    }
    String getPop(){
        return pop;
    }
    String getUvIndex(){
        return uvIndex;
    }

    String  getMorning(){
        return morning;
    }

    String getAfternoon(){
        return afternoon;
    }

    String getEvening(){
        return evening;
    }
    String getIcon(){
        return icon;
    }
    String getNight(){
        return night;
    }
}
