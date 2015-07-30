package com.guillaume.starwrobs.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @NonNull
    @Singleton
    public SQLiteDatabase provideSQSqLiteOpenHelper(@NonNull Context context) {
        return new SWDatabaseHelper(context).getWritableDatabase();
    }

}
