package com.guillaume.starwrobs.data.network;

import com.guillaume.starwrobs.data.network.model.ResultPeople;

import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApiManager {

    public static final String BASE_URL = "http://swapi.co/api/";

    private ApiInterface mApiInterface;

    public ApiManager(ApiInterface apiService) {
        this.mApiInterface = apiService;
    }

    public Observable<ResultPeople> getListPeoplePageNumber(final int page) {
        return mApiInterface.fetchPeople(page)
                .concatMap(new Func1<ResultPeople, Observable<ResultPeople>>() {

                    @Override
                    public Observable<ResultPeople> call(ResultPeople response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListPeoplePageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
