package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DaysRCViewHolder extends RecyclerView.ViewHolder {


    TextView dateTime,mantemp,desc,pop,uviIndex,morning,afternoon,evening,night;
    ImageView im;

    DaysRCViewHolder(View view){
        super(view);

        dateTime = view.findViewById(R.id.daysRcidatetime);
        mantemp = view.findViewById(R.id.daysRcitemp);
        desc = view.findViewById(R.id.daysRciDesc);
        im = view.findViewById(R.id.daysRciIcon);
        pop = view.findViewById(R.id.pop);
        uviIndex = view.findViewById(R.id.daysRciUv);
        morning = view.findViewById(R.id.daysRciMorningTemp);

        afternoon = view.findViewById(R.id.daysRciAfternoonTemp);
        evening = view.findViewById(R.id.daysRciEveningTemp);
        night = view.findViewById(R.id.daysRciNightTemp);

    }

}
