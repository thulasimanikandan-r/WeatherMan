package com.mani.weather.weatherman.common.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherManUtils {

    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

    public static boolean isDateNormalized(long millisSinceEpoch) {
        boolean isDateNormalized = false;
        if (millisSinceEpoch % DAY_IN_MILLIS == 0) {
            isDateNormalized = true;
        }

        return isDateNormalized;
    }
}
