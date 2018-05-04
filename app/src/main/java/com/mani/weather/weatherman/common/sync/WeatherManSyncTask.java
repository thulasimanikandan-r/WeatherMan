package com.mani.weather.weatherman.common.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.mani.weather.weatherman.common.data.WeatherContract;
import com.mani.weather.weatherman.common.network.NetworkUtil;
import com.mani.weather.weatherman.common.network.WeatherJsonUtil;
import com.mani.weather.weatherman.core.application.AppConstant;

import java.net.URL;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherManSyncTask {

    public static void WeatherManSync(Context context) {

        try {

            URL url = NetworkUtil.getUrl(context);

            String mWeatherManJsonResponse = NetworkUtil.getResponseFromHttpUrl(url);
            Log.d(AppConstant.LOG_TAG, "weatherResponse : " + mWeatherManJsonResponse);

            ContentValues[] weatherValues = WeatherJsonUtil.getWeatherDetailsFromJson(mWeatherManJsonResponse);

            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver weatherManContentResolver = context.getContentResolver();

                weatherManContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                weatherManContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }

        } catch (Exception e) {
            Log.e(AppConstant.LOG_TAG, e.getMessage());
        }

    }
}
