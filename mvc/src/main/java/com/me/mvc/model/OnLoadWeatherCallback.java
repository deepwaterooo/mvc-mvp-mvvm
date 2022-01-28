package com.me.mvc.model;

import com.android.libcore.net.NetError;

public interface OnLoadWeatherCallback {
    void onLoadSuccess(WeatherInfo info);
    void onError(NetError error);
}
