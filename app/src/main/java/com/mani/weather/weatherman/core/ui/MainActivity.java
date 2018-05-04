package com.mani.weather.weatherman.core.ui;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.mani.weather.weatherman.R;
import com.mani.weather.weatherman.common.data.WeatherContract;
import com.mani.weather.weatherman.common.sync.WeatherManSyncUtil;
import com.mani.weather.weatherman.common.util.WeatherManUtils;
import com.mani.weather.weatherman.core.application.AppConstant;
import com.mani.weather.weatherman.databinding.ActivityMainBinding;

import static com.mani.weather.weatherman.core.application.AppConstant.WEATHER_DETAIL_PROJECTION;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ActivityMainBinding mBinding;
    private Uri mUri;
    private static final int ID_DETAIL_LOADER = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);

        WeatherManSyncUtil.startImmediateSync(this);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {

            case ID_DETAIL_LOADER:
                Uri mUri = WeatherContract.WeatherEntry.CONTENT_URI;
                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            //Pressure

            //humidity
            float humidity = data.getFloat(AppConstant.INDEX_WEATHER_HUMIDITY);
            String humidityString = getString(R.string.format_humidity, humidity);
            mBinding.humidity.setText(humidityString);

            //wind
            float windSpeed = data.getFloat(AppConstant.INDEX_WEATHER_WIND_SPEED);
            float windDirection = data.getFloat(AppConstant.INDEX_WEATHER_DEGREES);
            String windString = WeatherManUtils.getFormattedWind(this, windSpeed, windDirection);
            mBinding.wind.setText(windString);


        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
