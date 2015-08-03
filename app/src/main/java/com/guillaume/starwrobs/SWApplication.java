package com.guillaume.starwrobs;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.guillaume.starwrobs.data.database.DbModule;

import timber.log.Timber;

public class SWApplication extends Application {

    @Nullable
    private volatile AppComponent appComponent;

    @NonNull
    public static SWApplication get(@NonNull Context context) {
        return (SWApplication) context.getApplicationContext();
    }

    @NonNull
    public AppComponent appComponent() {
        if (appComponent == null) {
            synchronized (SWApplication.class) {
                if (appComponent == null) {
                    appComponent = createAppComponent();
                }
            }
        }

        //noinspection ConstantConditions
        return appComponent;
    }

    @NonNull
    private AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
