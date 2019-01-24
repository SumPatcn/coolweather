package cc.sumpat.coolweather.util;

import android.text.TextUtils;


import org.json.JSONArray;
import org.json.JSONObject;

import cc.sumpat.coolweather.db.Province;

public class Utility {
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvonces=new JSONArray(response);
                for (int i = 0; i < allProvonces.length(); i++) {
                    JSONObject provinceObject=allProvonces.getJSONObject(i);
                    Province province=new Province();

                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
