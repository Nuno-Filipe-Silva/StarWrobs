package com.guillaume.starwrobs;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @NonNull
    private final Application app;

    AppModule(@NonNull Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return app;
    }
}
