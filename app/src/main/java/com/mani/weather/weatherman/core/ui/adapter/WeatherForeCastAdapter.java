package com.mani.weather.weatherman.core.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mani.weather.weatherman.R;
import com.mani.weather.weatherman.common.data.WeatherContract;
import com.mani.weather.weatherman.common.util.WeatherManDateUtils;
import com.mani.weather.weatherman.common.util.WeatherManUtils;
import com.mani.weather.weatherman.core.ui.MainActivity;
import com.mani.weather.weatherman.databinding.WeatherForecastListItemBinding;

/**
 * Created by thulasimanikandan_ra on 08-05-2018
 */
public class WeatherForeCastAdapter extends RecyclerView.Adapter<WeatherForeCastAdapter.ForecastAdapterViewHolder> {

    private final Context mContext;
    final private ForecastAdapterOnClickHandler mClickHandler;
    private Cursor mCursor;
    WeatherForecastListItemBinding mBinding;


    public interface ForecastAdapterOnClickHandler {
        void onClick(long date);
    }

    public WeatherForeCastAdapter(Context context, ForecastAdapterOnClickHandler mClickHandler) {
        mContext = context;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mBinding = WeatherForecastListItemBinding.inflate(layoutInflater, parent, false);
        return new ForecastAdapterViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        displayDetails(holder);
    }

    @SuppressLint({"StringFormatInvalid", "LocalSuppress", "StringFormatMatches"})
    private void displayDetails(ForecastAdapterViewHolder holder) {

        //Set Image resources
        int weatherId = mCursor.getInt(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID));
        int imagesForWeatherCondition = WeatherManUtils.getImagesForWeatherCondition(weatherId);
        holder.mBinding.ivWeather.setImageResource(imagesForWeatherCondition);

        //Date
        //long dateInMillis = mCursor.getLong(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_DATE));
        String dateInStr = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE));
        String dateString = WeatherManDateUtils.dateInDayFormat(mContext, dateInStr);
        holder.mBinding.tvForecastDay.setText(dateString);

        //Temperature
        float highTemp = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP));
        float lowTemp = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP));
        String tempString = WeatherManUtils.formatHighLows(mContext, highTemp, lowTemp);
        holder.mBinding.tvForecastTemp.setText(tempString);

        //wind
        float windSpeed = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED));
        float windDirection = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DEGREES));
        String windString = WeatherManUtils.getFormattedWind(mContext, windSpeed, windDirection);
        holder.mBinding.tvForecastWind.setText(windString);

        //humidity
        float humidity = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY));
        String humidityString = mContext.getString(R.string.format_humidity, humidity);
        holder.mBinding.tvForecastHumidity.setText(humidityString);

        //Pressure
        float pressure = mCursor.getFloat(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE));
        String pressureString = mContext.getString(R.string.format_pressure, pressure);
        holder.mBinding.tvForecastPressure.setText(pressureString);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final WeatherForecastListItemBinding mBinding;

        ForecastAdapterViewHolder(WeatherForecastListItemBinding weatherForecastListItemBinding) {
            super(weatherForecastListItemBinding.getRoot());
            mBinding = weatherForecastListItemBinding;
            weatherForecastListItemBinding.getRoot().setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
        }
    }
}
