package cc.sumpat.coolweather.util;

import android.app.Application;
import android.content.Context;

import cc.sumpat.coolweather.db.DBManager;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();

        DBManager.initDataBase(this);
    }

    public static Context getContext() {
        return context;
    }
}
