package com.zbar.liba;

import android.app.Application;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this, "utilscan1");
    }
}
