package com.guillaume.starwrobs.data.network;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class ApiModule {

    static OkHttpClient createOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        return client;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createOkHttpClient();
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(OkHttpClient okHttpClient) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(okHttpClient))
                .setEndpoint(ApiManager.BASE_URL);

        /*if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }*/

        return builder.build();
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(RestAdapter restAdapter) {
        return restAdapter.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    ApiManager provideUserManager(ApiInterface mApiService) {
        return new ApiManager(mApiService);
    }
}
