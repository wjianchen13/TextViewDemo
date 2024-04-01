package com.example.textviewdemo;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    protected static BaseApp instance = null;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        instance = this;
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}