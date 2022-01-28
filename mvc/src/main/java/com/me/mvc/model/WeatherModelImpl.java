package com.me.mvc.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.libcore.net.NetError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.libcore.net.NetError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherModelImpl implements WeatherModel {

    private Context mContext;
    public WeatherModelImpl(Context context) {
        mContext = context;
    }

    @Override
        public void getWeather(final OnLoadWeatherCallback callback) {

        final String url = "http://www.weather.com.cn/data/sk/101010100.html";

        if (isNetworkConnected(mContext)) {
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String netData = null;
                        netData = getNetWorkData(url);
                        Log.e("panzqww", "netData = "+netData);
                        if (!TextUtils.isEmpty(netData)) {
                            try {
                                JSONObject jsonObject = new JSONObject(netData);
                                jsonObject = new JSONObject(jsonObject.getString("weatherinfo"));
                                WeatherInfo info = new WeatherInfo();
                                info.setCity(jsonObject.getString("city"));
                                info.setTemp(Double.parseDouble(jsonObject.getString("temp")));
                                info.setWD(jsonObject.getString("WD"));
                                info.setWS(jsonObject.getString("WS"));
                                info.setTime(jsonObject.getString("time"));
                                callback.onLoadSuccess(info);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                            }
                        } else {
                            NetError error = new NetError();
                            error.errorCode = 405;
                            error.errorMessage = "获取数据为空！";
                            callback.onError(error);
                        }
                    }
                }).start();


        } else {
            NetError error = new NetError();
            error.errorCode = 404;
            error.errorMessage = "网络开小差了，请检查网络";
            callback.onError(error);
        }
    }

    private String getNetWorkData(String path) {
        String result = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //默认是get请求   如果想使用post必须指明
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                //把数据流中的json数据  转换成字符串
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int len = -1;
                byte[] buff = new byte[1024];
                while ((len = inputStream.read(buff)) != -1) {
                    bos.write(buff, 0, len);
                }
                result = new String(bos.toByteArray());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return result;
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
