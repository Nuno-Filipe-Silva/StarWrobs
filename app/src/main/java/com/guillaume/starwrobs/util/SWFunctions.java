package com.guillaume.starwrobs.util;

import android.support.annotation.NonNull;


public class SWFunctions {

    private static final String BASE_URL = "http://swapi.co/api/";
    private static final String URL_PEOPLE = BASE_URL + "people/";
    private static final String URL_FILMS = BASE_URL + "films/";
    private static final String URL_SPECIES = BASE_URL + "species/";
    private static final String URL_PLANETS = BASE_URL + "planets/";
    private static final String URL_STARSHIPS = BASE_URL + "starships/";
    private static final String URL_VEHICLES = BASE_URL + "vehicles/";

    /**
     * Hack to retrieve the id
     *
     * @param url the url
     * @return the id of the object
     */

    /* example   "http://swapi.co/api/films/6/"     */
    public static int getIdFromUrl(@NonNull String url) {
        if (url.contains(URL_PEOPLE)) {
            return Integer.parseInt(url.substring(URL_PEOPLE.length(), url.length() - 1));
        } else if (url.contains(URL_FILMS)) {
            return Integer.parseInt(url.substring(URL_FILMS.length(), url.length() - 1));
        } else if (url.contains(URL_SPECIES)) {
            return Integer.parseInt(url.substring(URL_SPECIES.length(), url.length() - 1));
        } else if (url.contains(URL_PLANETS)) {
            return Integer.parseInt(url.substring(URL_PLANETS.length(), url.length() - 1));
        } else if (url.contains(URL_STARSHIPS)) {
            return Integer.parseInt(url.substring(URL_STARSHIPS.length(), url.length() - 1));
        } else if (url.contains(URL_VEHICLES)) {
            return Integer.parseInt(url.substring(URL_VEHICLES.length(), url.length() - 1));
        } else {
            return 0;
        }
    }

}
