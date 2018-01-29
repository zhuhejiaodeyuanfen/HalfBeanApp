package com.wq.halfbeanapp;

import android.app.Application;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
