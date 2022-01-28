package com.me.mvc.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.libcore.net.NetError;
import com.android.libcore_ui.dialog.LoadingDialog;
import com.me.mvc.R;
import com.me.mvc.model.OnLoadWeatherCallback;
import com.me.mvc.model.WeatherInfo;
import com.me.mvc.model.WeatherModel;
import com.me.mvc.model.WeatherModelImpl;

public class WeatherActivity extends Activity implements OnLoadWeatherCallback {
    private TextView tv_name;
    private TextView tv_temperature;
    private TextView tv_wind_d;
    private TextView tv_wind_s;
    private TextView tv_time;
    private LoadingDialog ld;
    private WeatherModel weatherModel;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_wind_d = (TextView) findViewById(R.id.tv_wind_d);
        tv_wind_s = (TextView) findViewById(R.id.tv_wind_s);
        tv_time = (TextView) findViewById(R.id.tv_time);

        weatherModel = new WeatherModelImpl(this);
        ld = new LoadingDialog(this);

        ld.setLoadingText("正在获取天气...");
        ld.show();
        weatherModel.getWeather(this);
    }
    private void onShowWeather(WeatherInfo weatherInfo){
        tv_name.setText(weatherInfo.getCity());
        tv_temperature.setText(weatherInfo.getTemp()+"");
        tv_wind_d.setText(weatherInfo.getWD());
        tv_wind_s.setText(weatherInfo.getWS());
        tv_time.setText(weatherInfo.getTime());
    }

    @Override
        public void onLoadSuccess(WeatherInfo info) {
        ld.dismiss();
        onShowWeather(info);
    }

    @Override
        public void onError(NetError error) {

        ld.dismiss();
        String errorInfo = error.errorCode+": "+error.errorMessage;
        Log.e("panzqww","errorInfo = "+errorInfo);
        //Toast.makeText(WeatherActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
    }
}
