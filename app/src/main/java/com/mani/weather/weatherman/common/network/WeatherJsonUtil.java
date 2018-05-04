package com.mani.weather.weatherman.common.network;

import android.content.ContentValues;

import com.mani.weather.weatherman.common.data.WeatherContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherJsonUtil {


    public static ContentValues[] getWeatherDetailsFromJson(String weatherJsonStr) throws JSONException {

        JSONObject weatherJson = new JSONObject(weatherJsonStr);
        ContentValues[] weatherContentValues = new ContentValues[weatherJson.length()];
        for (int i = 0; i < weatherContentValues.length; i++) {
            int id = weatherJson.getInt("id");

            //For Weather Wind Details
            JSONObject windJson = weatherJson.getJSONObject("wind");
            double windSpeed = windJson.getDouble("speed");
            int windDirection = windJson.getInt("deg");

            //For Weather All
            JSONObject mainJson = weatherJson.getJSONObject("main");
            double temperature = mainJson.getDouble("temp");
            double pressure = mainJson.getDouble("pressure");
            double humidity = mainJson.getDouble("humidity");
            double tempMin = mainJson.getDouble("temp_min");
            double tempMax = mainJson.getDouble("temp_max");

            ContentValues weatherValues = new ContentValues();
            // weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateTimeMillis);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, humidity);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, pressure);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, tempMax);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, tempMin);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, id);

            weatherContentValues[i] = weatherValues;

        }
        return weatherContentValues;
    }
}
