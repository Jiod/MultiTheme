package com.fishpan.multitheme;

import android.app.Application;

/**
 * Application
 * Created by yupan on 17/5/28.
 */
public class MultithemeApplication extends Application {
    private static MultithemeApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MultithemeApplication getInstance(){
        return sInstance;
    }
}
