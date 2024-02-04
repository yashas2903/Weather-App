package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String symbol = "°F";
    String symbolWind = "mph";
    String symbolVis = "mi";

    boolean unitF = true;

    WeatherData weatherData;
    List<DailyWeatherData> dwd = new ArrayList<>();
    String currHour;
    private RecyclerView dailyRCView;
    private SharedPreferences storeData;

    String multidaysData;

    private static final String weatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String iconUrl = "https://openweathermap.org/img/w/";

    private static String city="Chicago";

    private static final String yourAPIKey ="98RRCNDMVJVVGN6MMENXNMN2D" ;
    //"4RHVD8YNYXLL8US6JFW3LEDW6";
    private long start;

    private RequestQueue request;

    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        refresh = findViewById(R.id.refreshNew);
        storeData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        request = Volley.newRequestQueue(this);
        dailyRCView = findViewById(R.id.hourlyRCView);

        refresh.setOnRefreshListener(()->{
            if(hasNetworkConnection()){
                city = storeData.getString("city",city);
                setTitle(city);

                downloadData();
                for(int i = 0;i< ((ConstraintLayout)findViewById(R.id.main_activity)).getChildCount(); i++){

                    View child = ((ConstraintLayout)findViewById(R.id.main_activity)).getChildAt(i);
                    if(child.getId()!=R.id.datetime ){
                        child.setVisibility(View.VISIBLE);
                    }
                }
                refresh.setRefreshing(false);

            }
            else{
                Toast.makeText(this, "Please Check your network", Toast.LENGTH_LONG).show();
                refresh.setRefreshing(false);
            }
        });


        if(!hasNetworkConnection()){
            if(savedInstanceState==null) {
                setTitle("Weather App");
                ((TextView) findViewById(R.id.datetime)).setText("No Internet Connection");
                for (int i = 0; i < ((ConstraintLayout) findViewById(R.id.main_activity)).getChildCount(); i++) {

                    View child = ((ConstraintLayout) findViewById(R.id.main_activity)).getChildAt(i);
                    if (child.getId() != R.id.datetime) {
                        child.setVisibility(View.GONE);
                    }
                }
            }
            else{
                city = savedInstanceState.getString("city");
                unitF = savedInstanceState.getBoolean("uniF");
                weatherData = (WeatherData) savedInstanceState.getSerializable("weatherData");
                dwd = (ArrayList<DailyWeatherData>) savedInstanceState.getSerializable("dwd");
                setTitle(city);
                displayData();
                setDailyRCView();
            }
        }

        if(hasNetworkConnection()) {
            city = storeData.getString("city",city);
            unitF = storeData.getBoolean("unitF",unitF);
            downloadData();
            setTitle(city);
        }

    }




    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public void setDailyRCView(){
        DailyRCAdapter adapter = new DailyRCAdapter(this,dwd);
        dailyRCView.setAdapter(adapter);
        dailyRCView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

    }
    public void changeUnits(MenuItem item){
            if(!unitF){
                unitF = true;
                symbol = "°F";
                item.setIcon(R.drawable.units_f);

            }
            else {
                unitF = false;
                item.setIcon(R.drawable.units_c);

            }

            storeData.edit().putBoolean("unitF",unitF).apply();
            return;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem tempunits = menu.findItem(R.id.units);

        if(!unitF)
            tempunits.setIcon(R.drawable.units_c);




        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.units){
            if(!hasNetworkConnection())
            {
                Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_LONG).show();

            }
            else {
                changeUnits(item);
                dwd.clear();
                downloadData();
            }
            return true;
        }

        if(item.getItemId() == R.id.calendar){
            if(!hasNetworkConnection()){
                Toast.makeText(this,"Please check your internet connection.",Toast.LENGTH_LONG).show();
            }
            else{
            Intent i = new Intent(this, DaysWeather.class);
            i.putExtra("jsonData", multidaysData);
            i.putExtra("unitF",unitF);
            i.putExtra("city",city);
            startActivity(i);}
            return true;

        }
        if(item.getItemId() == R.id.location){
            if(!hasNetworkConnection()){
                Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_LONG).show();
            }
            else{
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            alertBox.setTitle("Enter a Location" );
            alertBox.setMessage("\nFor US locations, enter as 'City' or 'City,State' " + "\nFor International locations, enter as 'City,Country'");
            final EditText location = new EditText(MainActivity.this);
            alertBox.setView(location);
            alertBox.setPositiveButton("ok",(dialog,which)->{

                dialog.dismiss();
                city = location.getText().toString();




                downloadData();
                Log.w("Location", this.city);
            });

            alertBox.setNegativeButton("Cancel",(dialog,which)->{
                dialog.dismiss();
            });

            AlertDialog alert = alertBox.create();
            alert.show();}
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void downloadData(){

        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();
        buildURL.appendPath(city);
        if(unitF)
           buildURL.appendQueryParameter("unitGroup","us");
        else
            buildURL.appendQueryParameter("unitGroup","metric");
        buildURL.appendQueryParameter("key", yourAPIKey);

        String urlToUse = buildURL.build().toString();
        Log.w("Volley Request",urlToUse);

        Response.Listener<JSONObject> listener =
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dwd.clear();
                            setTitle(city);
                            storeData.edit().putString("city",city).apply();
                            Log.w("Volley Response", response.toString());
                            multidaysData = response.toString();
                            parseJson(response.toString());
                            parseDailyData(response.toString());

                           } catch (Exception e) {
//                            textView.setText(MessageFormat.format("Response: {0}", e.getMessage()));

                            Log.w("Volley Response", "catch of response");
                        }
                    }
                };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    city = storeData.getString("city","");
                    Toast.makeText(MainActivity.this,"You have given a wrong input location",Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);
        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }


    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }


    public void parseDailyData(String data){
        try{

            JSONObject jObjMain = new JSONObject(data);

            JSONArray day = jObjMain.getJSONArray("days");

            String date,time,icon,temp,desc;


            int h = Integer.parseInt(currHour.substring(0,2));
            Log.w("CurrHour",currHour);
            Log.w("CurrHour",String.valueOf(h));

            for(int i = h+1 ; i<24; i++){
                 date = "Today";
                 temp = day.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("temp")+symbol;
                 time = String.valueOf(i<=12? i:i-12)+":00" + ( i < 12 ? "AM":"PM");
                 desc = day.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("conditions");
                 icon =  day.getJSONObject(0).getJSONArray("hours").getJSONObject(i).getString("icon");
                 icon = icon.replace("-","_");
                 dwd.add(new DailyWeatherData(date,time,temp,desc,icon));
                setDailyRCView();

            }



            for(int i = 1 ; i<4; i++){
                for(int j =0;j<24;j++){
                    long datetimeepcoh = day.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getLong("datetimeEpoch");
                    SimpleDateFormat dayDate = new SimpleDateFormat("EEEE", Locale.getDefault());
                    Date dateTime = new Date(datetimeepcoh * 1000);
                    date = dayDate.format(dateTime);

                    temp = day.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("temp")+symbol;
                    time = String.valueOf(j<=12? j:j-12)+":00" + ( j < 12 ? "AM":"PM");
                    desc = day.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("conditions");
                    icon =  day.getJSONObject(i).getJSONArray("hours").getJSONObject(j).getString("icon");
                    icon = icon.replace("-","_");
                    dwd.add(new DailyWeatherData(date,time,temp,desc,icon));
                    setDailyRCView();
                }



            }

            int lmn = dwd.size();
            Log.w("dwdSize",String.valueOf(lmn));



        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    public void parseJson(String data){
        try {

            if(!unitF){
                symbolWind = "kph";
                symbolVis = "km";

            }else{
                symbolVis="mi";
                symbolWind="mph";
            }

            JSONObject jObjMain = new JSONObject(data);

            String city = jObjMain.getString("address");

            JSONObject currentCondition = jObjMain.getJSONObject("currentConditions");

            JSONObject day = jObjMain.getJSONArray("days").getJSONObject(0);
            JSONArray hours = day.getJSONArray("hours");

            String morning =  hours.getJSONObject(8).getString("temp");
            String afternoon = hours.getJSONObject(13).getString("temp");
            String evening = hours.getJSONObject(17).getString("temp");;
            String night = hours.getJSONObject(23).getString("temp");;



            String temp = currentCondition.getString("temp");
            String humidity = currentCondition.getString("humidity")+"%";
            String feelsLike = currentCondition.getString("feelslike");
            String uvi = currentCondition.getString("uvindex");
            String vsb = currentCondition.getString("visibility")+" "+symbolVis;
            String desc = currentCondition.getString("conditions") + " ("+currentCondition.getString("cloudcover")+")" ;
            String curtd = currentCondition.getString("datetimeEpoch");


            String icon = currentCondition.getString("icon");
            icon = icon.replace("-","_");

            String sunrise;
            long sunriseepoch = currentCondition.getLong("sunriseEpoch");
            String sunset;
            long sunsetepoch = currentCondition.getLong("sunsetEpoch");

            Double wdir = currentCondition.getDouble("winddir");
            String wspeed = currentCondition.getString("windspeed");
            String wgust = currentCondition.getString("windgust");
            //Winds: NE at 11 mph to 10 mph
            String wData;
            if(wgust.equals("null"))
                wData = "Winds: "+ getDirection(wdir) +" at " + wspeed + " " + symbolWind;
            else
                wData = "Winds: "+ getDirection(wdir) +" at "+wspeed+" " + symbolWind +" gusting to "+wgust+" "+symbolWind;

            long datetimeEpoch = currentCondition.getLong("datetimeEpoch");

            Date dateTime = new Date(datetimeEpoch * 1000); // Java time values need milliseconds
            SimpleDateFormat fullDate = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            String fullDateStr = fullDate.format(dateTime); // Thu Sep 29 12:00 AM, 2022
            SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm ", Locale.getDefault());
            SimpleDateFormat suntimeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
            currHour = timeOnly.format(dateTime);

            dateTime = new Date(sunriseepoch*1000);
            sunrise = "Sunrise:"+ suntimeOnly.format(dateTime);

            dateTime = new Date(sunsetepoch*1000);
            sunset = "Sunset:"+ suntimeOnly.format(dateTime);






           weatherData = new WeatherData(city,temp,fullDateStr,feelsLike,desc,wData,humidity,uvi,vsb,morning,afternoon,evening,night,sunrise,sunset,icon);

           displayData();


            Log.w("CurrentCondition", currentCondition.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("city",city);
        outState.putBoolean("unitF",unitF);
        outState.putSerializable("weatherData",weatherData);
        outState.putSerializable("dwd",(Serializable) dwd);
        Log.w("weatherData",String.valueOf((WeatherData)outState.getSerializable("weatherData")));
        super.onSaveInstanceState(outState);
    }

    public void displayData(){

        if(!unitF) {
            symbol = "°C";

        }


       TextView dateTime = findViewById(R.id.datetime);
       dateTime.setText(weatherData.getDate());

       TextView temp = findViewById(R.id.temp);
       temp.setText(weatherData.getTemp()+symbol);

        ((ImageView)findViewById(R.id.imageView)).setImageResource(getResources().getIdentifier(weatherData.getIcon(),"drawable",this.getPackageName()));

       TextView desc = findViewById(R.id.wDesc);
       desc.setText(weatherData.getWeatherDesc());

       TextView fltemp = findViewById(R.id.fltemp);
       fltemp.setText("Feels like "+weatherData.getFeelsLike()+symbol);


       TextView wdesc = findViewById(R.id.windDetails);
       wdesc.setText(weatherData.getWindData());

       TextView hum = findViewById(R.id.humidity);
       hum.setText("Humidity: "+weatherData.getHmdty());

       TextView vsb = findViewById(R.id.visibility);
       vsb.setText("Visibility: "+ weatherData.getVsb());

       TextView uv = findViewById(R.id.uvIndex);
       uv.setText("UV Index: "+weatherData.getUvi());
       TextView mtp = findViewById(R.id.morningTemp);
       mtp.setText(weatherData.getMorning()+symbol);

       TextView atp = findViewById(R.id.afternoonTemp);
       atp.setText(weatherData.getAfternoon()+symbol);

       TextView etp = findViewById(R.id.eveningTemp);
       etp.setText(weatherData.getEvening()+symbol);
       TextView ntp = findViewById(R.id.nightTemp);
       ntp.setText(weatherData.getNight()+symbol);

       TextView sr = findViewById(R.id.sunrise); sr.setText(weatherData.getSunrise());
       ((TextView)findViewById(R.id.sunset)).setText(weatherData.getSunset());

    }


}



