package com.guillaume.starwrobs.data.network;

import com.guillaume.starwrobs.data.network.model.ResultFilms;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.data.network.model.ResultPlanets;
import com.guillaume.starwrobs.data.network.model.ResultSpecies;
import com.guillaume.starwrobs.data.network.model.ResultStarships;
import com.guillaume.starwrobs.data.network.model.ResultVehicles;

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

    public Observable<ResultFilms> getListFilmsPageNumber(final int page) {
        return mApiInterface.fetchFilms(page)
                .concatMap(new Func1<ResultFilms, Observable<ResultFilms>>() {

                    @Override
                    public Observable<ResultFilms> call(ResultFilms response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListFilmsPageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultPlanets> getListPlanetsPageNumber(final int page) {
        return mApiInterface.fetchPlanets(page)
                .concatMap(new Func1<ResultPlanets, Observable<ResultPlanets>>() {

                    @Override
                    public Observable<ResultPlanets> call(ResultPlanets response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListPlanetsPageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultSpecies> getListSpeciesPageNumber(final int page) {
        return mApiInterface.fetchSpecies(page)
                .concatMap(new Func1<ResultSpecies, Observable<ResultSpecies>>() {

                    @Override
                    public Observable<ResultSpecies> call(ResultSpecies response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListSpeciesPageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultStarships> getListStarshipsPageNumber(final int page) {
        return mApiInterface.fetchStarships(page)
                .concatMap(new Func1<ResultStarships, Observable<ResultStarships>>() {

                    @Override
                    public Observable<ResultStarships> call(ResultStarships response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListStarshipsPageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResultVehicles> getListVehiclesPageNumber(final int page) {
        return mApiInterface.fetchVehicles(page)
                .concatMap(new Func1<ResultVehicles, Observable<ResultVehicles>>() {

                    @Override
                    public Observable<ResultVehicles> call(ResultVehicles response) {
                        // Terminal case.
                        if (response.next == null) {
                            return Observable.just(response);
                        }
                        return Observable.just(response)
                                .concatWith(getListVehiclesPageNumber(page + 1));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
