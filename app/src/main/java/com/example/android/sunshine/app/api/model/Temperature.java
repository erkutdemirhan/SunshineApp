package com.example.android.sunshine.app.api.model;

/**
 * Created by Ekrem on 13/06/16.
 */
public class Temperature {

    private double min_temp;
    private double max_temp;
    private String description;

    public Temperature(double min_temp, double max_temp, String description) {
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.description = description;
    }

    public double getMinTemp() {
        return min_temp;
    }

    public double getMaxTemp() {
        return max_temp;
    }

    public String getDescription() {
        return description;
    }
}
