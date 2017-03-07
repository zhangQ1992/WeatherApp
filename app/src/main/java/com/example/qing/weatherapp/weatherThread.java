package com.example.qing.weatherapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qing on 17-3-4.
 */

public class weatherThread extends Thread {
    public boolean isgetflag = false;
    String my_s;
    String weatherData;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    Request mRequest;

    public weatherThread(String s) {
        this.my_s = s;
        mRequest = new Request.Builder().url(my_s).build();
    }

    //run方法只会执行一次，结束后即代表线程结束，无需停止线程
    @Override
    public void run() {
        try {
            Response mResponse = mOkHttpClient.newCall(mRequest).execute();
            if(mResponse.isSuccessful()){
                weatherData = mResponse.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            isgetflag = true;
        }
    }

    public String getWeather(){
        return weatherData;
    }

    public boolean getFlag(){
        return isgetflag;
    }
}
