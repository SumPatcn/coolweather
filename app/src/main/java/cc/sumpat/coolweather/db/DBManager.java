package cc.sumpat.coolweather.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import cc.sumpat.coolweather.util.MyApplication;

public class DBManager {
    private static final String DATA_BASE_NAME = "db_coolweather";
    private static DaoSession mDaoSession;

    private DBManager() {
    }

    public static void initDataBase() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(
                MyApplication.getContext(), DATA_BASE_NAME);
        Database db = openHelper.getWritableDb();
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
}
