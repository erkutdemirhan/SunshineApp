package com.example.android.sunshine.app.mvp.weatherForecastList.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.api.HttpApi;
import com.example.android.sunshine.app.api.HttpImplementation;
import com.example.android.sunshine.app.api.model.Temperature;
import com.example.android.sunshine.app.api.parser.WeatherDataParser;
import com.example.android.sunshine.app.base.BaseFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ekrem on 11/06/16.
 */
public class ForecastFragment extends BaseFragment {

    public ForecastFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        List<String> dummyWeatherForecastData  = createDummyWeatherForecastData();
        ArrayAdapter<String> weatherForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, R.id.list_item_forecast_textview, dummyWeatherForecastData);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(weatherForecastAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh) {
            FetchWeatherDataTask fetchWeatherDataTask = new FetchWeatherDataTask();
            fetchWeatherDataTask.execute("34490");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> createDummyWeatherForecastData() {
        List<String> dataList = new ArrayList<>(
                Arrays.asList(new String[] {
                        "Today - Sunny - 88/63",
                        "Tomorrow - Foggy - 70/46",
                        "Weds - Cloudy - 72/63",
                        "Thurs - Rainy - 64/51",
                        "Fri - Foggy - 70/46",
                        "Sat - Sunny - 76/68"
                })
        );
        return dataList;
    }

    private class FetchWeatherDataTask extends AsyncTask<String, Void, String> {

        private HttpApi httpApi;

        public FetchWeatherDataTask() {
            this.httpApi = new HttpImplementation();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String postalCode = params[0];
            String resultJsonString = httpApi.getOneWeekDailyWeatherForecastByPostalCode(postalCode);
            return resultJsonString;
        }

        @Override
        protected void onPostExecute(String resultJsonString) {
            if(resultJsonString != null) {
                Log.d("ForecastFragment", resultJsonString);
                try {
                    List<Temperature> temperatureList = WeatherDataParser.jsonToTemperatureList(resultJsonString);
                    for(Temperature temperature:temperatureList) {
                        Log.d("ForecastFragment", "min temp="
                                +temperature.getMinTemp()
                                +", max temp="
                                +temperature.getMaxTemp()
                                +", description="+temperature.getDescription());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
