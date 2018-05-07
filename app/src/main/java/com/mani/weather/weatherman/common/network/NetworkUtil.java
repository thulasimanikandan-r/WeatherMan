package com.mani.weather.weatherman.common.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.mani.weather.weatherman.BuildConfig;
import com.mani.weather.weatherman.core.application.AppConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class NetworkUtil {

    private static String QUERY_PARAM = "q";
    private static String APP_ID_PARAM = "appid";
    private static String TAG = AppConstant.LOG_TAG;


    private static URL buildUrlWithLocationQuery(String locationQuery) {
        Uri weatherQueryUri = Uri.parse(AppConstant.WEATHER_MAN_BASE_URL).
                buildUpon()
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(APP_ID_PARAM, BuildConfig.API_KEY)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.d(AppConstant.LOG_TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getUrl(Context context){
        String location = "Bangalore,IN";
        return buildUrlWithLocationQuery(location);
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

}
