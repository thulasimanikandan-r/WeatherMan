package com.mani.weather.weatherman.common.network;

import android.content.ContentValues;

import com.mani.weather.weatherman.common.data.WeatherContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherJsonUtil {


    public static ContentValues[] getWeatherDetailsFromJson(String weatherJsonStr) throws JSONException {

        JSONObject forecastJson = new JSONObject(weatherJsonStr);


        JSONArray listJsonArray = forecastJson.getJSONArray("list");
        ContentValues[] weatherContentValues = new ContentValues[listJsonArray.length()];//listJson.length()

        //CITY
        JSONObject cityJson = forecastJson.getJSONObject("city");
        String city = cityJson.getString("name");

        for (int i = 0; i < listJsonArray.length(); i++) {

            JSONObject listJson = listJsonArray.getJSONObject(i);

            //For WeatherDates
            long weatherDate = listJson.getLong("dt");
            String dateStr = listJson.getString("dt_txt");

            //For Weather Wind Details
            JSONObject windJson = listJson.getJSONObject("wind");
            double windSpeed = windJson.getDouble("speed");
            int windDirection = windJson.getInt("deg");

            //For Main Weatehr All
            JSONObject mainJson = listJson.getJSONObject("main");
            double temperature = mainJson.getDouble("temp");
            double pressure = mainJson.getDouble("pressure");
            double humidity = mainJson.getDouble("humidity");
            double tempMin = mainJson.getDouble("temp_min");
            double tempMax = mainJson.getDouble("temp_max");

            //For Weather All
            JSONArray weatherJosnList = listJson.getJSONArray("weather");
            int weatherId = 0;
            String weatherDescription = "";
            for (int j = 0; j < weatherJosnList.length(); j++) {
                JSONObject weatherJson = weatherJosnList.getJSONObject(j);
                weatherId = weatherJson.getInt("id");
                weatherDescription = weatherJson.getString("description");

            }


            ContentValues weatherValues = new ContentValues();
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_DATE, weatherDate);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateStr);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, humidity);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, pressure);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_TEMPERATURE, temperature);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, tempMax);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, tempMin);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weatherId);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_DESCRIPTION, weatherDescription);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_CITY, city);

            weatherContentValues[i] = weatherValues;

        }
        return weatherContentValues;
    }
}
