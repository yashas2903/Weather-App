package com.example.weatherapp;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private final String city;
    private final String temp;
    private final String date;
    private final String feelsLike;
    private final String icon;
    private final String weatherDesc;
    private final String windData;
    private final String hmdty;
    private final String uvi;
    private final String vsb;
    private final String morning;
    private final String afternoon;
    private final String evening;
    private final String night;
    private final String sunrise;
    private final String sunset;

WeatherData(String city, String temp,String date,String feelsLike, String weatherDesc, String windData, String hmdty,
            String uvi, String vsb, String morning, String afternoon, String evening, String night, String sunrise, String sunset,String icon){


    this.city = city;
    this.temp = temp;
    this.date = date;
    this.icon = icon;
    this.feelsLike = feelsLike;
    this.windData = windData;
    this.weatherDesc = weatherDesc;
    this.hmdty = hmdty;
    this.uvi = uvi;
    this.vsb = vsb;
    this.morning = morning;
    this.afternoon = afternoon;
    this.evening = evening;
    this.night = night;
    this.sunrise = sunrise;
    this.sunset = sunset;
}


String getCity(){
    return  city;
}

String getTemp(){
    return temp;

}

    public String getIcon() {
        return icon;
    }

    String getDate(){
    return date;
}

String getFeelsLike(){
    return  feelsLike;
}

String getWeatherDesc(){
    return weatherDesc;
}
String getWindData(){
    return windData;
}

String getHmdty(){
    return hmdty;
}
String getUvi(){
    return uvi;
}

String getVsb(){
    return vsb;

}

String getMorning(){
    return  morning;
}

String getAfternoon(){
    return afternoon;
}
String getEvening(){
    return evening;
}
String getNight(){
    return night;
}
String getSunrise(){
    return sunrise;
}



String getSunset(){
    return sunset;
}


}
