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

        JSONObject forecastJson = new JSONObject(weatherJsonStr);
        ContentValues[] weatherContentValues = new ContentValues[forecastJson.length()];
        for (int i = 0; i < 1; i++) {
            int id = forecastJson.getInt("id");
            String dateStr = forecastJson.getString("dt_txt");

            //For Weather Wind Details
            JSONObject windJson = forecastJson.getJSONObject("wind");
            double windSpeed = windJson.getDouble("speed");
            int windDirection = windJson.getInt("deg");

            //For Main Weatehr All
            JSONObject mainJson = forecastJson.getJSONObject("main");
            double temperature = mainJson.getDouble("temp");
            double pressure = mainJson.getDouble("pressure");
            double humidity = mainJson.getDouble("humidity");
            double tempMin = mainJson.getDouble("temp_min");
            double tempMax = mainJson.getDouble("temp_max");

            //For Weather All
            JSONObject weatherJson = forecastJson.getJSONObject("weather");
            double weatherId = weatherJson.getDouble("id");
            double weatherDescription = weatherJson.getDouble("description");

            //CITY
            JSONObject cityJson = forecastJson.getJSONObject("city");
            String city = cityJson.getString("name");

            ContentValues weatherValues = new ContentValues();
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateStr);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, humidity);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, pressure);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_TEMPERATURE, temperature);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, tempMax);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, tempMin);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_FORECAST_ID, id);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weatherId);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_DESCRIPTION, weatherDescription);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_CITY, city);

            weatherContentValues[i] = weatherValues;

        }
        return weatherContentValues;
    }
}
