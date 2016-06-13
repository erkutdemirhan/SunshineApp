package com.example.android.sunshine.app.api;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ekrem on 11/06/16.
 */
public class HttpImplementation implements HttpApi {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String API_KEY = "a4c6a341be13ea337949e5e6ad2d02f0";

    private static final String PARAM_KEY_ZIPCODE = "q";
    private static final String PARAM_KEY_DATAFORMAT = "mode";
    private static final String PARAM_KEY_UNITS = "units";
    private static final String PARAM_KEY_DAYCOUNT = "cnt";
    private static final String PARAM_KEY_APIKEY = "appid";

    @Override
    public String getOneWeekDailyWeatherForecastByPostalCode(String postalCode) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY_ZIPCODE, postalCode)
                .appendQueryParameter(PARAM_KEY_DATAFORMAT, "json")
                .appendQueryParameter(PARAM_KEY_UNITS, "metric")
                .appendQueryParameter(PARAM_KEY_DAYCOUNT, "7")
                .appendQueryParameter(PARAM_KEY_APIKEY, API_KEY)
                .build();
        return httpGetRequest(builtUri);
    }

    private String httpGetRequest(final Uri uri) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            forecastJsonStr = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return buffer.toString();
    }
}
