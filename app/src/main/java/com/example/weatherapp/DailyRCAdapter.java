package com.example.weatherapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyRCAdapter extends RecyclerView.Adapter<DailyRCViewHolder> {

       private final MainActivity mainActivity;
       private final List<DailyWeatherData> dwd;


       public DailyRCAdapter(MainActivity mActivity, List<DailyWeatherData> nData ){
              this.mainActivity = mActivity;
              this.dwd = nData;

       }

       @NonNull
       @Override
       public DailyRCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_view,parent, false);

              return new DailyRCViewHolder(view);
       }

       @Override
       public void onBindViewHolder(@NonNull DailyRCViewHolder holder, int position) {

              DailyWeatherData singleData = dwd.get(position);

              holder.temp.setText(singleData.getTemp());
              holder.day.setText(singleData.getDay());
              holder.desc.setText(singleData.getDesc());
              holder.time.setText(singleData.getTime());


              holder.im.setImageResource(mainActivity.getResources().getIdentifier(singleData.getIcon(),"drawable",mainActivity.getPackageName()));


       }

       @Override
       public int getItemCount() {
              return dwd.size();
       }
}
