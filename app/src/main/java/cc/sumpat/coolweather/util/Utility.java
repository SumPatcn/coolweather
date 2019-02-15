package cc.sumpat.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.sumpat.coolweather.db.City;
import cc.sumpat.coolweather.db.CityDao;
import cc.sumpat.coolweather.db.County;
import cc.sumpat.coolweather.db.CountyDao;
import cc.sumpat.coolweather.db.DaoSession;
import cc.sumpat.coolweather.db.Province;
import cc.sumpat.coolweather.db.ProvinceDao;
import cc.sumpat.coolweather.gson.Aqi;
import cc.sumpat.coolweather.gson.Weather;

public class Utility {
    private static DaoSession mDaoSession;

    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvonces = new JSONArray(response);
                for (int i = 0; i < allProvonces.length(); i++) {
                    JSONObject provinceObject = allProvonces.getJSONObject(i);
                    mDaoSession = MyApplication.getDaoSession();
                    ProvinceDao provinceDao = mDaoSession.getProvinceDao();
                    Province province = new Province();
                    province.setId(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    provinceDao.insert(province);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceCode) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCitys = new JSONArray(response);
                for (int i = 0; i < allCitys.length(); i++) {
                    JSONObject cityObject = allCitys.getJSONObject(i);
                    mDaoSession = MyApplication.getDaoSession();
                    CityDao cityDao = mDaoSession.getCityDao();
                    City city = new City();
                    city.setId(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceCode);
                    cityDao.insert(city);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityCode) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    mDaoSession = MyApplication.getDaoSession();
                    CountyDao countyDao = mDaoSession.getCountyDao();
                    County county = new County();
                    county.setId(countyObject.getInt("id"));
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityCode);
                    countyDao.insert(county);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Aqi handleAqiResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String aqiContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(aqiContent, Aqi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
