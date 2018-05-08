package com.mani.weather.weatherman.core.application;


import com.mani.weather.weatherman.common.data.WeatherContract;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class AppConstant {

    public static final String LOG_TAG = "WeatherMan";

    //WeatherMan Base URL
   // public static final String WEATHER_MAN_BASE_URL = "http://samples.openweathermap.org";
    public static final String WEATHER_MAN_BASE_URL = "http://api.openweathermap.org";


    public static final String[] MAIN_FORECAST_PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_DATE,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_DESCRIPTION,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_CITY,
            WeatherContract.WeatherEntry.COLUMN_TEMPERATURE,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP

    };

}
