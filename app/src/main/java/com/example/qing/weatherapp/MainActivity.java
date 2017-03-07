package com.example.qing.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {

    final static String LOG_TAG = "loggggggggggg";
    EditText mEditText;
    Button mButton;
    TextView mTextView;

    WeatherData mWeatherData;
    WeatherInfo mWeatherInfo;
    String weatherString;
    String weatherHttp;

    Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.ed_city);
        mTextView = (TextView) findViewById(R.id.tv_log);
        mButton = (Button) findViewById(R.id.btn_show);


        weatherHttp = String.format("城市代码参考："+"\r\n"
                +"101010100=北京"+"\r\n"
                +"101020100=上海"+"\r\n"
                +"101040100=重庆"+"\r\n"
                +"101080101=呼和浩特"+"\r\n"
                +"101200101=武汉"+"\r\n"
                +"101240101=南昌"+"\r\n"
                +"101240711=会昌"+"\r\n"
                +"101280101=广州"+"\r\n"
        );

        mTextView.setText(weatherHttp);





        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherHttp = String.format("http://www.weather.com.cn/data/sk/"+mEditText.getText()+".html");
                weatherThread mWeatherThread =  new weatherThread(weatherHttp);
                mWeatherThread.start();
                Log.d(LOG_TAG,"mWeatherThread.start();");
                while(mWeatherThread.getFlag() == false);
                Log.d(LOG_TAG,"mWeatherThread.stop();");
                String dat = mWeatherThread.getWeather();
                mWeatherData = mGson.fromJson(dat,WeatherData.class);
                if(mWeatherData != null) {
                    mWeatherInfo = mWeatherData.getWeatherinfo();
                    weatherString = String.format("城市:"+mWeatherInfo.city+"\r\n"
                                    +"城市ID:"+mWeatherInfo.cityid+"\r\n"
                                    +"温度:"+mWeatherInfo.temp+"\r\n"
                                    +"风向:"+mWeatherInfo.WD+"\r\n"
                                    +"风力等级:"+mWeatherInfo.WS+"\r\n"
//                        +"SD:"+mWeatherInfo.SD+"\r\n"
                                    +"WSE:"+mWeatherInfo.WSE+"\r\n"
                                    +"检测时间:"+mWeatherInfo.time+"\r\n"
                                    +"是否安置雷达:"+mWeatherInfo.isRadar+"\r\n"
                                    +"雷达标号:"+mWeatherInfo.Radar+"\r\n"
                                    +"njd:"+mWeatherInfo.njd+"\r\n"
                                    +"qy:"+mWeatherInfo.qy+"\r\n"
                                    +"雨量数据:"+mWeatherInfo.rain+"\r\n"
                    );
                    mTextView.setText(weatherString);
                }
                else {
                    Toast.makeText(MainActivity.this,"请输入正确的城市代码",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
