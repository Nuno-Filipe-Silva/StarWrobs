package com.guillaume.starwrobs.data.network;

import com.guillaume.starwrobs.data.network.model.ResultPeople;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


public interface ApiInterface {

    /**
     * Fetch all people
     */
    @GET("/people/")
    Observable<ResultPeople> fetchPeople(@Query("page") int page);
}
