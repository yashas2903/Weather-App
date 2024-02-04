package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DailyRCViewHolder extends RecyclerView.ViewHolder {


    TextView day, time,temp,desc;
    ImageView im;

    DailyRCViewHolder(View view){
        super(view);
        day =view.findViewById(R.id.day);
        time = view.findViewById(R.id.dailytime);
        temp = view.findViewById(R.id.dailytemp);
        desc = view.findViewById(R.id.dailydesc);
        im = view.findViewById(R.id.dailyicon);

    }
}
