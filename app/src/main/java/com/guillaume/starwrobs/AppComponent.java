package com.guillaume.starwrobs;

import android.support.annotation.NonNull;

import com.guillaume.starwrobs.data.controller.DataController;
import com.guillaume.starwrobs.data.database.DbModule;
import com.guillaume.starwrobs.data.network.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DbModule.class,
                ApiModule.class,
        }
)
public interface AppComponent {
    // void inject(@NonNull TweetsFragment fragment);
    void inject(@NonNull DataController dataController);
}