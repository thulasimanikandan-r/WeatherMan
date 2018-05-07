package com.mani.weather.weatherman.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thulasimanikandan_ra on 04-05-2018
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weatherman.db";
    private static final int DATABASE_VERSION = 7;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                        WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WeatherContract.WeatherEntry.COLUMN_DATE + " TEXT, " +
                        WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL," +
                        WeatherContract.WeatherEntry.COLUMN_WEATHER_DESCRIPTION + " TEXT , " +
                        WeatherContract.WeatherEntry.COLUMN_TEMPERATURE + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_MIN_TEMP + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_MAX_TEMP + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_PRESSURE + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_WIND_SPEED + " REAL , " +
                        WeatherContract.WeatherEntry.COLUMN_DEGREES + " REAL, " +
                        WeatherContract.WeatherEntry.COLUMN_WEATHER_CITY + " TEXT )  ";

        db.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
