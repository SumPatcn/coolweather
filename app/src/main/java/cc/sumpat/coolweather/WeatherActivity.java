package cc.sumpat.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cc.sumpat.coolweather.gson.Aqi;
import cc.sumpat.coolweather.gson.DailyForecast;
import cc.sumpat.coolweather.gson.LifeStyle;
import cc.sumpat.coolweather.gson.Weather;
import cc.sumpat.coolweather.gson.Aqi;
import cc.sumpat.coolweather.util.HttpUtil;
import cc.sumpat.coolweather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private String TAG = "WeatherActivity";

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private LinearLayout aqiLayout;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiLayout =findViewById(R.id.aqi_layout);
        comfortText = findViewById(R.id.comf_text);
        carWashText = findViewById(R.id.cw_text);
        sportText = findViewById(R.id.sport_text);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences
                (this);
        String weatherString = preferences.getString("weather", null);
        Log.d(TAG, "onCreate: " + weatherString);
        if (weatherString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
            requestAqi(weatherId);
        }
    }

    public void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.net/s6/weather?location=" + weatherId +
                "&key=2e34dcfa8cf2445c966410994b69f060";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "服务器获取信息失败2",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            Log.d(TAG, "成功获取天气数据" + responseText);
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "服务器获取信息失败",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, responseText);
                        }
                    }
                });
            }
        });
    }

    public void requestAqi(final String weatherId) {
        StringBuilder temp = new StringBuilder(weatherId);
        temp.deleteCharAt(temp.length() - 1);
        temp.deleteCharAt(temp.length() - 1);
        temp.append("01");

        String aqiUrl = "https://free-api.heweather.net/s6/air/now?location=" + temp.toString() +
                "&key=2e34dcfa8cf2445c966410994b69f060";

        HttpUtil.sendOkHttpRequest(aqiUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "服务器获取信息失败2",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Aqi aqi = Utility.handleAqiResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (aqi != null && "ok".equals(aqi.getStatus())) {
                            Log.d(TAG, "成功获取空气质量信息" + responseText);
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("aqi", responseText);
                            editor.apply();
                        } else {
                            Toast.makeText(WeatherActivity.this, "服务器获取信息失败",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, responseText);
                        }
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.getLocation();
        String updateTime = weather.update.getLoc().split(" ")[1];
        String degree = weather.now.getTmp() + "℃";
        String weatherInfo = weather.now.getCond_txt();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        if (weather.lifeStyleList != null && weather.lifeStyleList.size() > 0) {
            for (LifeStyle lifeStyle : weather.lifeStyleList) {
                if (lifeStyle.getType().equals("comf")) {
                    String comfort = "舒适度：" + lifeStyle.getTxt();
                    comfortText.setText(comfort);
                } else {
                    comfortText.setText("获取指数信息失败");
                }
                if (lifeStyle.getType().equals("cw")) {
                    String carWash = "洗车指数：" + lifeStyle.getTxt();
                    carWashText.setText(carWash);
                } else {
                    carWashText.setText("获取指数信息失败");
                }
                if (lifeStyle.getType().equals("sport")) {
                    String sport = "运动指数：" + lifeStyle.getTxt();
                    sportText.setText(sport);
                } else {
                    sportText.setText("获取指数信息失败");
                }
            }
        } else {
            Log.d(TAG, "showWeatherInfo: 获取指数列表数据失败");
        }
        if (weather.dailyForecastList != null && weather.dailyForecastList.size() > 0) {
            for (DailyForecast dailyForecast : weather.dailyForecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,
                        forecastLayout, false);
                TextView dateText = view.findViewById(R.id.date_text);
                TextView infoText = view.findViewById(R.id.info_text);
                TextView maxText = view.findViewById(R.id.max_text);
                TextView minText = view.findViewById(R.id.min_text);
                dateText.setText(dailyForecast.getDate());
                infoText.setText(dailyForecast.getCond_txt_d());
                maxText.setText(dailyForecast.getTmp_max());
                minText.setText(dailyForecast.getTmp_min());
                forecastLayout.addView(view);
            }
        } else {
            Log.d(TAG, "showWeatherInfo: 获取未来几天天气列表数据失败");
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void showAqiInfo(Aqi aqi) {
        if (aqi != null) {
            View view=LayoutInflater.from(this).inflate(R.layout.aqi,
                    aqiLayout,false);
            TextView aqiText=view.findViewById(R.id.aqi_text);
            TextView pm25Text=view.findViewById(R.id.pm25_text);
            aqiText.setText(aqi.getAir_now_city().getAqi());
            pm25Text.setText(aqi.getAir_now_city().getPm25());
        } else {
            Toast.makeText(this, "获取空气质量信息失败", Toast.LENGTH_SHORT).show();
        }
    }
}
