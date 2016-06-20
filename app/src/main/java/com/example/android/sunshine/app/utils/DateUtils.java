package com.example.android.sunshine.app.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ekrem on 20/06/16.
 */
public class DateUtils {

    public static String getDateAfterNumberOfDays(int numberOfDays) {
        if(numberOfDays < 0) return "";
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        if(numberOfDays == 0) {
            return getReadableDateString(calendar.getTimeInMillis());
        }
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
        return getReadableDateString(calendar.getTimeInMillis());
    }

    private static String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }
}
