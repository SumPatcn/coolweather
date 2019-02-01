package cc.sumpat.coolweather.util;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cc.sumpat.coolweather.db.DaoMaster;
import cc.sumpat.coolweather.db.DaoSession;

public class MyApplication extends Application {
    private static final String DATA_BASE_NAME = "db_coolweather";
    private static DaoSession mDaoSession;
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        initDataBase();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                this, DATA_BASE_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        if (mDaoSession != null) {
            return mDaoSession;
        } else {
            throw new IllegalStateException("DaoSession not initialized");
        }
    }

    public static Context getContext() {
        return context;
    }
}
