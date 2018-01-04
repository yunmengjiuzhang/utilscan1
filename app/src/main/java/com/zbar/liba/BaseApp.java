package com.zbar.liba;

import android.app.Application;

/**
 * Created by xuany on 2018/1/4.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this, "utilscan1");
    }
}
