package com.example.android.sunshine.app.api.parser;

import com.example.android.sunshine.app.api.model.Temperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekrem on 13/06/16.
 */
public class WeatherDataParser {

    public static List<Temperature> jsonToTemperatureList(String jsonString) throws JSONException {
        List<Temperature> tempList = new ArrayList<>();
        JSONObject rootObject = new JSONObject(jsonString);
        JSONArray listArray = rootObject.getJSONArray("list");
        for(int i=0; i<listArray.length(); i++) {
            JSONObject listObject = listArray.getJSONObject(i);
            JSONObject tempObject = listObject.getJSONObject("temp");
            JSONArray weatherArray = listObject.getJSONArray("weather");
            String description = weatherArray.getJSONObject(0).getString("main");
            double minTemp = tempObject.getDouble("min");
            double maxTemp = tempObject.getDouble("max");
            tempList.add(new Temperature(minTemp, maxTemp, description));
        }
        return tempList;
    }
}
