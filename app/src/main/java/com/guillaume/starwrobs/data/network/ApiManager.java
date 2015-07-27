package com.guillaume.starwrobs.data.network;

import com.guillaume.starwrobs.data.network.model.ResultPeople;

import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiManager {

    private static final String BASE_URL = "http://swapi.co/api/";
    private ApiInterface mApiInterface;

    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

    public ApiManager() {
        mApiInterface = restAdapter.create(ApiInterface.class);
    }

    public Observable<ResultPeople> fetchPeople(int page) {
        return mApiInterface.fetchPeople(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
