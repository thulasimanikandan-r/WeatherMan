package com.mani.weather.weatherman.common.sync;

import android.content.Context;
import android.content.Intent;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherManSyncUtil {

    public static void startImmediateSync(final Context context) {
        Intent intentToSyncImmediately = new Intent(context, WeatherManIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
