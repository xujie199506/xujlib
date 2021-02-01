package com.xuj;

import android.app.Application;

import com.xuj.lib.db.Sqlite;

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Sqlite.APP = "base";
        Sqlite.DB_VERSION = 2;
        Sqlite db = Sqlite.getInstance(this, Sqlite.DB_COMMON);
        boolean recover = db.recover();
    }
}
