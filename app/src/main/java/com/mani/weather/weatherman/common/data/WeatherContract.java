package com.mani.weather.weatherman.common.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.mani.weather.weatherman";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weatherMan";

    public static final class WeatherEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();

        public static final String TABLE_NAME = "weatherMan";
        public static final String COLUMN_FORECAST_ID = "forecast_Id";
        public static final String COLUMN_DEGREES = "degress";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_WEATHER_ID = "weather_Id";
        public static final String COLUMN_WEATHER_DESCRIPTION = "weather_description";

        public static final String COLUMN_WEATHER_CITY = "city";


        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }
    }
}
