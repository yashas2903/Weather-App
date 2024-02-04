package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DaysRCAdapter extends RecyclerView.Adapter<DaysRCViewHolder>{


    private final DaysWeather mainActivity;
    private final List<MultipleDaysWeatherData> wData;

    public DaysRCAdapter(DaysWeather mainActivity, List<MultipleDaysWeatherData> data){
        this.mainActivity = mainActivity;
        this.wData = data;
    }


    @NonNull
    @Override
    public DaysRCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_rc_item,parent,false);

        return new DaysRCViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DaysRCViewHolder holder, int position) {

        MultipleDaysWeatherData data = wData.get(position);

        holder.desc.setText(data.getDesc());
        holder.dateTime.setText(data.getDateTime());
        holder.mantemp.setText(data.getMnmTemp());
        holder.uviIndex.setText(data.getUvIndex());
        holder.pop.setText(data.getPop());
        holder.morning.setText(data.getMorning());
        holder.afternoon.setText(data.getAfternoon());
        holder.evening.setText(data.getEvening());
        holder.night.setText(data.getNight());
        holder.im.setImageResource(mainActivity.getResources().getIdentifier(data.getIcon(),"drawable",mainActivity.getPackageName()));





    }

    @Override
    public int getItemCount() {
        return wData.size();
    }
}
