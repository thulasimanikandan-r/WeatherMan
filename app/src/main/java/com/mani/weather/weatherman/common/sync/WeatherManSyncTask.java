package com.mani.weather.weatherman.common.sync;

import android.content.Context;
import android.util.Log;

import com.mani.weather.weatherman.common.util.NetworkUtil;
import com.mani.weather.weatherman.core.application.AppConstant;

import java.net.URL;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherManSyncTask {

    public static void WeatherManSync(Context context){

        try {

            URL url = NetworkUtil.getUrl(context);

            String mWeatherManJsonResponse = NetworkUtil.getResponseFromHttpUrl(url);
            Log.d(AppConstant.LOG_TAG, "weatherResponse : " + mWeatherManJsonResponse);

        }catch(Exception e){
            Log.e(AppConstant.LOG_TAG, e.getMessage());
        }

    }
}
