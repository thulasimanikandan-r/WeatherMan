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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.mani.weather.weatherman.R;
import com.mani.weather.weatherman.common.data.WeatherContract;
import com.mani.weather.weatherman.common.sync.WeatherManSyncUtil;
import com.mani.weather.weatherman.common.util.WeatherManUtils;
import com.mani.weather.weatherman.core.application.AppConstant;
import com.mani.weather.weatherman.core.ui.adapter.WeatherForeCastAdapter;
import com.mani.weather.weatherman.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, WeatherForeCastAdapter.ForecastAdapterOnClickHandler {

    ActivityMainBinding mBinding;
    private static final int ID_DETAIL_LOADER = 123;

    private WeatherForeCastAdapter mWeatherForeCastAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    Animation mAnimation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0f);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        WeatherManSyncUtil.startImmediateSync(this);


        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);
        initializeWeatherForeCastAdapter();

        showHideLoading(true);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);
        this.recreate();
    }

    private void initializeWeatherForeCastAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvWeatherForecast.setLayoutManager(layoutManager);
        mBinding.rvWeatherForecast.setHasFixedSize(true);
        mWeatherForeCastAdapter = new WeatherForeCastAdapter(this, this);
        mBinding.rvWeatherForecast.setAdapter(mWeatherForeCastAdapter);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {

            case ID_DETAIL_LOADER:


                Uri mUri = WeatherContract.WeatherEntry.CONTENT_URI;
                String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";

                // String selection = WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();
                return new CursorLoader(this,
                        mUri,
                        AppConstant.MAIN_FORECAST_PROJECTION,
                        null,
                        null,
                        sortOrder);

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
            }
        } catch (Exception e) {
            Log.e(AppConstant.LOG_TAG, "Exception in loader finished" + e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //Set null for RecyclerView Adapter
        mWeatherForeCastAdapter.swapCursor(null);
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

    @SuppressLint({"StringFormatInvalid", "LocalSuppress", "StringFormatMatches"})
    void displayScreenDetails(Cursor data) {

        //Set Adapter
        mWeatherForeCastAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mBinding.rvWeatherForecast.smoothScrollToPosition(mPosition);

        //Temperature
        //High Temperature
        float highTemp = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP));

        //Low Temperature
        float lowTemp = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP));

        String tempString = WeatherManUtils.formatHighLows(this, highTemp, lowTemp);
        mBinding.tvTemperature.setText(tempString);

        //Set Image resources
        int weatherId = data.getInt(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID));
        int imagesForWeatherCondition = WeatherManUtils.getImagesForWeatherCondition(weatherId);
        mBinding.ivIcon.setImageResource(imagesForWeatherCondition);
        mBinding.ivIcon.startAnimation(mAnimation);

        //Set Weather description
        String weatherDesc = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_DESCRIPTION));
        mBinding.tvWeatherDescription.setText(weatherDesc);

        //Pressure
        float pressure = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE));
        String pressureString = getString(R.string.format_pressure, pressure);
        mBinding.uvRays.setText(pressureString);

        //humidity
        float humidity = data.getFloat(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY));
        String humidityString = getString(R.string.format_humidity, humidity);
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

    @Override
    public void onClick(long date) {
    //TODO mani's comment - add to next activity called detailsWeatherActivity and pass all the values to display
    }
}
