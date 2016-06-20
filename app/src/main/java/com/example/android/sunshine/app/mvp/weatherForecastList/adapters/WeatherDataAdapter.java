package com.example.android.sunshine.app.mvp.weatherForecastList.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.api.model.Temperature;
import com.example.android.sunshine.app.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekrem on 20/06/16.
 */
public class WeatherDataAdapter extends BaseAdapter {

    private List<Temperature> temperatureDataList;
    private Context context;

    public WeatherDataAdapter(Context cntext) {
        this.temperatureDataList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return temperatureDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return temperatureDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.list_item_forecast_textview);
        Temperature temperatureData = temperatureDataList.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.getDateAfterNumberOfDays(position));
        sb.append(" - ");
        sb.append(temperatureData.getDescription());
        sb.append(" - ");
        sb.append(temperatureData.getHighLowTemp());
        textView.setText(sb.toString());
        return v;
    }

    public void updateTemperatureData(List<Temperature> temperatureList) {
        temperatureDataList.clear();
        temperatureDataList = temperatureList;
        notifyDataSetChanged();
    }
}
