package cc.sumpat.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import cc.sumpat.coolweather.gson.Aqi;
import cc.sumpat.coolweather.gson.Weather;
import cc.sumpat.coolweather.service.AutoUpdateService;
import cc.sumpat.coolweather.util.HttpUtil;
import cc.sumpat.coolweather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private String TAG = "WeatherActivity";

    public SwipeRefreshLayout swipeRefresh;

    public DrawerLayout drawerLayout;

    private ImageView bingPicImg;

    private ScrollView weatherLayout;

    private Button navButton;

    private String mWeatherId;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        drawerLayout = findViewById(R.id.drawer_layout);
        bingPicImg = findViewById(R.id.bing_pic_img);
        weatherLayout = findViewById(R.id.weather_layout);
        navButton = findViewById(R.id.nav_button);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comf_text);
        carWashText = findViewById(R.id.cw_text);
        sportText = findViewById(R.id.sport_text);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                this);
        String weatherString = preferences.getString("weather", null);
        String aqiString = preferences.getString("aqi", null);
        String bingPic = preferences.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
        if (weatherString != null || aqiString != null) {
            Log.d(TAG, "onCreate: 有缓存数据运行");
            Weather weather = Utility.handleWeatherResponse(weatherString);
            Aqi aqi = Utility.handleAqiResponse(aqiString);
            mWeatherId = weather.getBasic().getCid();
            showWeatherInfo(weather);
            showAqiInfo(aqi);
        } else {
            Log.d(TAG, "onCreate: 无缓存数据运行");
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
            requestAqi(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
                requestAqi(mWeatherId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
                        Toast.makeText(WeatherActivity.this, "服务器获取常规天气信息失败",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
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
                        if (weather != null && "ok".equals(weather.getStatus())) {
                            Log.d(TAG, "成功获取常规天气数据");
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.getBasic().getCid();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this,
                                    "服务器返回天气信息异常", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "服务器返回天气信息异常 " + responseText);
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
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
                        Toast.makeText(WeatherActivity.this,
                                "服务器获取空气质量信息失败", Toast.LENGTH_SHORT).show();
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
                            showAqiInfo(aqi);
                        } else {
                            Toast.makeText(WeatherActivity.this,
                                    "服务器返回空气质量信息异常", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "服务器返回空气质量信息异常 " + responseText);
                        }
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.getBasic().getLocation();
        String updateTime = weather.getUpdate().getLoc().split(" ")[1];
        String degree = weather.getNow().getTmp() + " ℃";
        String weatherInfo = weather.getNow().getCond_txt();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        if (weather.getLifestyle() != null && weather.getLifestyle().size() > 0) {
            for (Weather.LifestyleBean lifeStyle : weather.getLifestyle()) {
                String comf = "comf";
                String cw = "cw";
                String sportT = "sport";
                if (comf.equals(lifeStyle.getType())) {
                    String comfort = "舒适度：" + lifeStyle.getTxt();
                    comfortText.setText(comfort);
                }
                if (cw.equals(lifeStyle.getType())) {
                    String carWash = "洗车指数：" + lifeStyle.getTxt();
                    carWashText.setText(carWash);
                }
                if (sportT.equals(lifeStyle.getType())) {
                    String sport = "运动指数：" + lifeStyle.getTxt();
                    sportText.setText(sport);
                }
            }
        } else {
            Log.d(TAG, "showWeatherInfo: 获取指数列表数据失败");
        }
        if (weather.getDaily_forecast() != null && weather.getDaily_forecast().size() > 0) {
            for (Weather.DailyForecastBean dailyForecast : weather.getDaily_forecast()) {
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
            Toast.makeText(this, "获取预报列表数据失败", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "showWeatherInfo: 获取预报列表数据失败");
        }
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent=new Intent(this,AutoUpdateService.class);
        startActivity(intent);
    }

    private void showAqiInfo(Aqi aqi) {
        if (aqi != null) {
            aqiText.setText(aqi.getAir_now_city().getAqi());
            pm25Text.setText(aqi.getAir_now_city().getPm25());
        } else {
            Toast.makeText(this, "获取空气质量信息失败", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "showAqiInfo: 获取空气质量信息失败");
        }
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }
}
