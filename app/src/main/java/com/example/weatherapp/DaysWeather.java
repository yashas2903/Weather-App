package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaysWeather extends AppCompatActivity {

    String response;
    List<MultipleDaysWeatherData> wData = new ArrayList<>();
    boolean unitF=true;
    String symbol = "°F";

    private RecyclerView rcView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_weather);

        Intent intent = getIntent();

        unitF = intent.getBooleanExtra("unitF",true);

        if(!unitF)
            symbol = "°C";
        rcView = findViewById(R.id.daysRCView);

        parseResponseData();
        setTitle(intent.getStringExtra("city"));
    }

    public void setRCView(){
        DaysRCAdapter adapter = new DaysRCAdapter(this,wData);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));

    }

    void parseResponseData(){

        Intent i = getIntent();

        if(i.hasExtra("jsonData")){
            response = i.getStringExtra("jsonData");
            Log.w("DaysWeather",response);
        }
        else
            return;

        try {
            JSONObject jObjMain = new JSONObject(response);

            JSONArray day = jObjMain.getJSONArray("days");

            String dateTime, temp,desc,pop,uvi,icon,morning,afternoon,evening,night;

            for(int j = 0;j<15;j++){

                long epoch = day.getJSONObject(j).getLong("datetimeEpoch");

                Date date = new Date(epoch * 1000);
                SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());


                dateTime = dayDate.format(date);

                temp = day.getJSONObject(j).getString("tempmax") +symbol+"/"+ day.getJSONObject(j).getString("tempmin")+symbol;
                desc = day.getJSONObject(j).getString("description");
                pop = "("+day.getJSONObject(j).getString("precipprob")+"% precip.)";
                uvi = "UV Index: "+ day.getJSONObject(j).getString("uvindex");
                icon = day.getJSONObject(j).getString("icon");
                icon = icon.replace("-","_");
                morning = day.getJSONObject(j).getJSONArray("hours").getJSONObject(8).getString("temp") +symbol;
                afternoon = day.getJSONObject(j).getJSONArray("hours").getJSONObject(13).getString("temp")+symbol;
                evening = day.getJSONObject(j).getJSONArray("hours").getJSONObject(17).getString("temp")+symbol;
                night = day.getJSONObject(j).getJSONArray("hours").getJSONObject(day.getJSONObject(j).getJSONArray("hours").length()-1).getString("temp")+symbol;



                wData.add(new MultipleDaysWeatherData(temp,dateTime,desc,pop,uvi,icon,morning,afternoon,evening,night));
                setRCView();




            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }






    }


}