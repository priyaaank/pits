package com.pits;

import android.app.Application;

import com.pits.initializer.AppBootstrapper;

public class PitsApplication extends Application {

    private AppBootstrapper appBootstrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        appBootstrapper = new AppBootstrapper(getApplicationContext());
        appBootstrapper.initiateBoot();
    }
}
