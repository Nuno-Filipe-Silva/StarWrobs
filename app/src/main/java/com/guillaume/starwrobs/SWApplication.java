package com.guillaume.starwrobs;


import android.app.Application;

public class SWApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Database setup

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Close database
    }
}
