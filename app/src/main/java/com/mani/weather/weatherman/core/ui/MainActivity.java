package com.mani.weather.weatherman.core.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mani.weather.weatherman.R;
import com.mani.weather.weatherman.common.data.WeatherContract;
import com.mani.weather.weatherman.common.sync.WeatherManSyncUtil;
import com.mani.weather.weatherman.common.util.WeatherManUtils;
import com.mani.weather.weatherman.core.application.AppConstant;
import com.mani.weather.weatherman.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ActivityMainBinding mBinding;
    private static final int ID_DETAIL_LOADER = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0f);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        WeatherManSyncUtil.startImmediateSync(this);
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);


        showHideLoading(true);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {

            case ID_DETAIL_LOADER:


                Uri mUri = WeatherContract.WeatherEntry.CONTENT_URI;
                return new CursorLoader(this,
                        mUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                showHideLoading(false);
                displayScreenDetails(cursor);
                cursor.close();
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(AppConstant.LOG_TAG, "Exception in loader finished" + e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    void showHideLoading(boolean isVisible) {
        if (isVisible) {
            mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
            mBinding.humidity.setVisibility(View.INVISIBLE);
            mBinding.wind.setVisibility(View.INVISIBLE);
        } else {
            mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
            mBinding.humidity.setVisibility(View.VISIBLE);
            mBinding.wind.setVisibility(View.VISIBLE);
        }
    }

    void displayScreenDetails(Cursor data) {

       /* //High Temperature
        float highTemp = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP));
        String hTempString = getString(R.string.format_temperature, highTemp);
        mBinding.tvTemperature.setText(hTempString);

        //Low Temperature
        float lowTemp = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP));
        String lTempString = getString(R.string.format_temperature, lowTemp);
        mBinding.tvTemperature.setText(lTempString);*/

        //Temperature
        float temp = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_TEMPERATURE));
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String twmpString = getString(R.string.format_temperature, temp);
        mBinding.tvTemperature.setText(twmpString);

        //Pressure
        float pressure = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE));
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String pressureString = getString(R.string.format_pressure, pressure);
        mBinding.uvRays.setText(pressureString);

        //humidity
        float humidity = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY));
        @SuppressLint("StringFormatMatches") String humidityString = getString(R.string.format_humidity, humidity);
        mBinding.humidity.setText(humidityString);

        //wind
        float windSpeed = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED));
        float windDirection = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DEGREES));
        String windString = WeatherManUtils.getFormattedWind(this, windSpeed, windDirection);
        mBinding.wind.setText(windString);

        //city
        String city = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_CITY));
        mBinding.mCity.setText(city);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
