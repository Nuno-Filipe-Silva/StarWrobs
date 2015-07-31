package com.guillaume.starwrobs.data.network;

import com.guillaume.starwrobs.data.network.model.ResultFilms;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.data.network.model.ResultPlanets;
import com.guillaume.starwrobs.data.network.model.ResultSpecies;
import com.guillaume.starwrobs.data.network.model.ResultStarships;
import com.guillaume.starwrobs.data.network.model.ResultVehicles;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


public interface ApiInterface {

    /**
     * Fetch people page
     */
    @GET("/people/")
    Observable<ResultPeople> fetchPeople(@Query("page") int page);

    /**
     * Fetch films page
     */
    @GET("/films/")
    Observable<ResultFilms> fetchFilms(@Query("page") int page);

    /**
     * Fetch planets page
     */
    @GET("/planets/")
    Observable<ResultPlanets> fetchPlanets(@Query("page") int page);

    /**
     * Fetch species page
     */
    @GET("/species/")
    Observable<ResultSpecies> fetchSpecies(@Query("page") int page);

    /**
     * Fetch starships page
     */
    @GET("/starships/")
    Observable<ResultStarships> fetchStarships(@Query("page") int page);

    /**
     * Fetch vehicles page
     */
    @GET("/vehicles/")
    Observable<ResultVehicles> fetchVehicles(@Query("page") int page);
}
