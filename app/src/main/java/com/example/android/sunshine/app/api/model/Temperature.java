package com.example.android.sunshine.app.api.model;

/**
 * Created by Ekrem on 13/06/16.
 */
public class Temperature {

    private long min_temp;
    private long max_temp;
    private String description;

    public Temperature(double min_temp, double max_temp, String description) {
        this.min_temp = formatTemperature(min_temp);
        this.max_temp = formatTemperature(max_temp);
        this.description = description;
    }

    private long formatTemperature(double temp) {
        return Math.round(temp);
    }

    public long getMinTemp() {
        return min_temp;
    }

    public long getMaxTemp() {
        return max_temp;
    }

    public String getDescription() {
        return description;
    }

    public String getHighLowTemp() {
        return getMaxTemp() + "/" + getMinTemp();
    }
}
