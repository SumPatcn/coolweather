package cc.sumpat.coolweather.util;

import android.text.TextUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.sumpat.coolweather.db.City;
import cc.sumpat.coolweather.db.CityDao;
import cc.sumpat.coolweather.db.County;
import cc.sumpat.coolweather.db.CountyDao;
import cc.sumpat.coolweather.db.DBManager;
import cc.sumpat.coolweather.db.DaoSession;
import cc.sumpat.coolweather.db.Province;
import cc.sumpat.coolweather.db.ProvinceDao;

public class Utility {
    private static DaoSession mDaoSession;

    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvonces = new JSONArray(response);
                for (int i = 0; i < allProvonces.length(); i++) {
                    JSONObject provinceObject = allProvonces.getJSONObject(i);
                    mDaoSession = DBManager.getDaoSession();
                    ProvinceDao provinceDao = mDaoSession.getProvinceDao();
                    Province province = new Province();
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

    public static boolean handleCityResponse(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCitys = new JSONArray(response);
                for (int i = 0; i < allCitys.length(); i++) {
                    JSONObject cityObject = allCitys.getJSONObject(i);
                    mDaoSession = DBManager.getDaoSession();
                    CityDao cityDao=mDaoSession.getCityDao();
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    cityDao.insert(city);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCountys = new JSONArray(response);
                for (int i = 0; i < allCountys.length(); i++) {
                    JSONObject countyObject = allCountys.getJSONObject(i);
                    mDaoSession = DBManager.getDaoSession();
                    CountyDao countyDao=mDaoSession.getCountyDao();
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    countyDao.insert(county);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
