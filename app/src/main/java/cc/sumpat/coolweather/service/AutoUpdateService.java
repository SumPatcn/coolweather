package cc.sumpat.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import cc.sumpat.coolweather.WeatherActivity;
import cc.sumpat.coolweather.gson.Aqi;
import cc.sumpat.coolweather.gson.Weather;
import cc.sumpat.coolweather.util.HttpUtil;
import cc.sumpat.coolweather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    private String TAG = "AutoUpdateService";

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeeather();
        updateBingPic();
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=8*60*60*1000;
        long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,i,0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        String aqiString = preferences.getString("aqi", null);
        if (weatherString != null || aqiString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.getBasic().getCid();
            String weatherUrl = "https://free-api.heweather.net/s6/weather?location=" + weatherId +
                    "&key=2e34dcfa8cf2445c966410994b69f060";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather1 = Utility.handleWeatherResponse(responseText);
                    if (weather1 != null && "ok".equals(weather1.getStatus())) {
                        Log.d(TAG, "成功获取常规天气数据");
                        SharedPreferences.Editor editor = PreferenceManager.
                                getDefaultSharedPreferences(AutoUpdateService.this)
                                .edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                    } else {
                        Toast.makeText(AutoUpdateService.this,
                                "服务器返回天气信息异常", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "服务器返回天气信息异常 " + responseText);
                    }
                }
            });
            Aqi aqi = Utility.handleAqiResponse(aqiString);
            String aqiId = aqi.getBasic().getCid();
            String aqiUrl = "https://free-api.heweather.net/s6/air/now?location=" + aqiId +
                    "&key=2e34dcfa8cf2445c966410994b69f060";
            HttpUtil.sendOkHttpRequest(aqiUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Aqi aqi1 = Utility.handleAqiResponse(responseText);
                    if (aqi1 != null && "ok".equals(aqi1.getStatus())) {
                        Log.d(TAG, "成功获取空气质量信息" + responseText);
                        SharedPreferences.Editor editor = PreferenceManager.
                                getDefaultSharedPreferences(AutoUpdateService.this)
                                .edit();
                        editor.putString("aqi", responseText);
                        editor.apply();
                    } else {
                        Toast.makeText(AutoUpdateService.this,
                                "服务器返回空气质量信息异常", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "服务器返回空气质量信息异常 " + responseText);
                    }
                }
            });
        }
    }

    private void updateBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(
                        AutoUpdateService.this).edit();
                editor.putString("bing_Pic",bingPic);
                editor.apply();
            }
        });
    }
}
