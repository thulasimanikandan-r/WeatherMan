package com.mani.weather.weatherman.common.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherManIntentService extends IntentService {

    public WeatherManIntentService() {
        super("WeatherManIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherManSyncTask.WeatherManSync(this);
    }
}
