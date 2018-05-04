package com.mani.weather.weatherman.core.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mani.weather.weatherman.R;
import com.mani.weather.weatherman.common.sync.WeatherManSyncUtil;
import com.mani.weather.weatherman.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        WeatherManSyncUtil.startImmediateSync(this);
    }
}
