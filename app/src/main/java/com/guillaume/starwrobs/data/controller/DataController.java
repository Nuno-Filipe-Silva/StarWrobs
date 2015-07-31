package com.guillaume.starwrobs.data.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.network.ApiManager;
import com.guillaume.starwrobs.data.network.model.Film;
import com.guillaume.starwrobs.data.network.model.People;
import com.guillaume.starwrobs.data.network.model.Planet;
import com.guillaume.starwrobs.data.network.model.ResultFilms;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.data.network.model.ResultPlanets;
import com.guillaume.starwrobs.data.network.model.ResultSpecies;
import com.guillaume.starwrobs.data.network.model.ResultStarships;
import com.guillaume.starwrobs.data.network.model.ResultVehicles;
import com.guillaume.starwrobs.data.network.model.Species;
import com.guillaume.starwrobs.data.network.model.Starship;
import com.guillaume.starwrobs.data.network.model.Vehicle;
import com.guillaume.starwrobs.util.SWFunctions;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class DataController {

    @Inject
    SQLiteDatabase mDatabase;

    @Inject
    ApiManager mApiManager;

    public DataController(Context context) {
        SWApplication.get(context).appComponent().inject(this);
    }

    public void refreshData() {

        mDatabase.beginTransaction();

        try {

            deleteDatabase();

            getAndInsertPeople();
            getAndInsertFilms();
            getAndInsertPlanets();
            getAndInsertSpecies();
            getAndInsertStarships();
            getAndInsertVehicles();


            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            //Error in between database transaction
        } finally {
            mDatabase.endTransaction();
        }
    }

    private void deleteDatabase() {
        mDatabase.delete(SWDatabaseContract.Tables.PEOPLE, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PEOPLE_SPECIES, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PEOPLE_STARSHIPS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PEOPLE_VEHICLES, null, null);

        mDatabase.delete(SWDatabaseContract.Tables.FILMS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_FILMS_PLANETS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_FILMS_SPECIES, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_FILMS_STARSHIPS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_FILMS_VEHICLES, null, null);

        mDatabase.delete(SWDatabaseContract.Tables.PLANETS, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PLANETS_PEOPLE, null, null);

        mDatabase.delete(SWDatabaseContract.Tables.SPECIES, null, null);
        mDatabase.delete(SWDatabaseContract.Tables.VEHICLES, null, null);
        mDatabase.delete(SWDatabaseContract.Tables.STARSHIPS, null, null);
    }

    private void getAndInsertPeople() {
        mApiManager.getListPeoplePageNumber(1)
                .concatMap(new Func1<ResultPeople, Observable<People>>() {

                    @Override
                    public Observable<People> call(ResultPeople response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<People>() {

                    @Override
                    public void call(People people) {
                        Log.d("result", "people = " + people.name);
                        insertPeople(people);
                    }
                });
    }

    private void getAndInsertFilms() {
        mApiManager.getListFilmsPageNumber(1)
                .concatMap(new Func1<ResultFilms, Observable<Film>>() {

                    @Override
                    public Observable<Film> call(ResultFilms response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<Film>() {

                    @Override
                    public void call(Film film) {
                        Log.d("result", "film = " + film.title);
                        insertFilm(film);
                    }
                });
    }

    private void getAndInsertPlanets() {
        mApiManager.getListPlanetsPageNumber(1)
                .concatMap(new Func1<ResultPlanets, Observable<Planet>>() {

                    @Override
                    public Observable<Planet> call(ResultPlanets response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<Planet>() {

                    @Override
                    public void call(Planet planet) {
                        Log.d("result", "planet = " + planet.name);
                        insertPlanet(planet);
                    }
                });
    }

    private void getAndInsertSpecies() {
        mApiManager.getListSpeciesPageNumber(1)
                .concatMap(new Func1<ResultSpecies, Observable<Species>>() {

                    @Override
                    public Observable<Species> call(ResultSpecies response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<Species>() {

                    @Override
                    public void call(Species species) {
                        Log.d("result", "species = " + species.name);
                        insertSpecies(species);
                    }
                });
    }

    private void getAndInsertStarships() {
        mApiManager.getListStarshipsPageNumber(1)
                .concatMap(new Func1<ResultStarships, Observable<Starship>>() {

                    @Override
                    public Observable<Starship> call(ResultStarships response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<Starship>() {

                    @Override
                    public void call(Starship starship) {
                        Log.d("result", "starship = " + starship.name);
                        insertStarship(starship);
                    }
                });
    }

    private void getAndInsertVehicles() {
        mApiManager.getListVehiclesPageNumber(1)
                .concatMap(new Func1<ResultVehicles, Observable<Vehicle>>() {

                    @Override
                    public Observable<Vehicle> call(ResultVehicles response) {
                        return Observable.from(response.results);
                    }

                })
                .subscribe(new Action1<Vehicle>() {

                    @Override
                    public void call(Vehicle vehicle) {
                        Log.d("result", "vehicle = " + vehicle.name);
                        insertVehicle(vehicle);
                    }
                });
    }


    private void insertPeople(People people) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(people.url);
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, id);
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, people.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, people.edited);
        values.put(SWDatabaseContract.People.PEOPLE_NAME, people.name);
        values.put(SWDatabaseContract.People.PEOPLE_HEIGHT, people.height);
        values.put(SWDatabaseContract.People.PEOPLE_MASS, people.mass);
        values.put(SWDatabaseContract.People.PEOPLE_HAIR_COLOR, people.hair_color);
        values.put(SWDatabaseContract.People.PEOPLE_SKIN_COLOR, people.skin_color);
        values.put(SWDatabaseContract.People.PEOPLE_EYE_COLOR, people.eye_color);
        values.put(SWDatabaseContract.People.PEOPLE_BIRTH_YEAR, people.birth_year);
        values.put(SWDatabaseContract.People.PEOPLE_GENDER, people.gender);
        values.put(SWDatabaseContract.People.PEOPLE_HOMEWORLD, people.homeworld);
        mDatabase.insert(SWDatabaseContract.Tables.PEOPLE, null, values);


        int nbOfIterations = people.films.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPeopleFilms.PEOPLE_ID, id);
            values.put(SWDatabaseContract.LinkPeopleFilms.FILM_ID, SWFunctions.getIdFromUrl(people.films.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, null, values);
        }

        nbOfIterations = people.species.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPeopleSpecies.PEOPLE_ID, id);
            values.put(SWDatabaseContract.LinkPeopleSpecies.SPECIES_ID, SWFunctions.getIdFromUrl(people.species.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PEOPLE_SPECIES, null, values);
        }

        nbOfIterations = people.vehicles.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPeopleVehicles.PEOPLE_ID, id);
            values.put(SWDatabaseContract.LinkPeopleVehicles.VEHICLE_ID, SWFunctions.getIdFromUrl(people.vehicles.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PEOPLE_VEHICLES, null, values);
        }

        nbOfIterations = people.starships.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPeopleStarships.PEOPLE_ID, id);
            values.put(SWDatabaseContract.LinkPeopleStarships.STARSHIP_ID, SWFunctions.getIdFromUrl(people.starships.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PEOPLE_STARSHIPS, null, values);
        }
    }

    private void insertFilm(Film film) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(film.url);
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, id);
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, film.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, film.edited);
        values.put(SWDatabaseContract.Film.FILM_TITLE, film.title);
        values.put(SWDatabaseContract.Film.FILM_DIRECTOR, film.director);
        values.put(SWDatabaseContract.Film.FILM_PRODUCER, film.producer);
        values.put(SWDatabaseContract.Film.FILM_EPISODE_ID, film.episode_id);
        values.put(SWDatabaseContract.Film.FILM_OPENING_CRAWL, film.opening_crawl);
        values.put(SWDatabaseContract.Film.FILM_RELEASE_DATE, film.release_date);
        mDatabase.insert(SWDatabaseContract.Tables.FILMS, null, values);


        int nbOfIterations = film.planets.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkFilmsPlanets.FILM_ID, id);
            values.put(SWDatabaseContract.LinkFilmsPlanets.PLANETS_ID, SWFunctions.getIdFromUrl(film.planets.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_FILMS_PLANETS, null, values);
        }

        nbOfIterations = film.species.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkFilmsSpecies.FILM_ID, id);
            values.put(SWDatabaseContract.LinkFilmsSpecies.SPECIES_ID, SWFunctions.getIdFromUrl(film.species.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_FILMS_SPECIES, null, values);
        }

        nbOfIterations = film.starships.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkFilmsStarships.FILM_ID, id);
            values.put(SWDatabaseContract.LinkFilmsStarships.STARSHIPS_ID, SWFunctions.getIdFromUrl(film.starships.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_FILMS_STARSHIPS, null, values);
        }

        nbOfIterations = film.vehicles.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkFilmsVehicles.FILM_ID, id);
            values.put(SWDatabaseContract.LinkFilmsVehicles.VEHICLES_ID, SWFunctions.getIdFromUrl(film.vehicles.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_FILMS_VEHICLES, null, values);
        }
    }

    private void insertPlanet(Planet planet) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(planet.url);
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, id);
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, planet.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, planet.edited);
        values.put(SWDatabaseContract.Planet.PLANET_NAME, planet.name);
        values.put(SWDatabaseContract.Planet.PLANET_ROTATION_PERIOD, planet.rotation_period);
        values.put(SWDatabaseContract.Planet.PLANET_ORBITAL_PERIOD, planet.orbital_period);
        values.put(SWDatabaseContract.Planet.PLANET_DIAMETER, planet.diameter);
        values.put(SWDatabaseContract.Planet.PLANET_CLIMATE, planet.climate);
        values.put(SWDatabaseContract.Planet.PLANET_GRAVITY, planet.gravity);
        values.put(SWDatabaseContract.Planet.PLANET_TERRAIN, planet.terrain);
        values.put(SWDatabaseContract.Planet.PLANET_SURFACE_WATER, planet.surface_water);
        values.put(SWDatabaseContract.Planet.PLANET_POPULATION, planet.population);
        mDatabase.insert(SWDatabaseContract.Tables.PLANETS, null, values);


        int nbOfIterations = planet.residents.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPlanetsPeople.PLANET_ID, id);
            values.put(SWDatabaseContract.LinkPlanetsPeople.PEOPLE_ID, SWFunctions.getIdFromUrl(planet.residents.get(i)));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PLANETS_PEOPLE, null, values);
        }
    }

    private void insertSpecies(Species species) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(species.url));
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, species.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, species.edited);
        values.put(SWDatabaseContract.Species.SPECIES_NAME, species.name);
        values.put(SWDatabaseContract.Species.SPECIES_CLASSIFICATION, species.classification);
        values.put(SWDatabaseContract.Species.SPECIES_DESIGNATION, species.designation);
        values.put(SWDatabaseContract.Species.SPECIES_AVERAGE_HEIGHT, species.average_height);
        values.put(SWDatabaseContract.Species.SPECIES_SKIN_COLORS, species.skin_colors);
        values.put(SWDatabaseContract.Species.SPECIES_HAIR_COLORS, species.hair_colors);
        values.put(SWDatabaseContract.Species.SPECIES_EYE_COLORS, species.eye_colors);
        values.put(SWDatabaseContract.Species.SPECIES_AVERAGE_LIFESPAN, species.average_lifespan);
        values.put(SWDatabaseContract.Species.SPECIES_HOMEWORLD, species.homeworld);
        values.put(SWDatabaseContract.Species.SPECIES_LANGUAGE, species.language);
        mDatabase.insert(SWDatabaseContract.Tables.SPECIES, null, values);
    }

    private void insertStarship(Starship starship) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(starship.url));
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, starship.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, starship.edited);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_NAME, starship.name);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL, starship.model);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER, starship.manufacturer);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS, starship.cost_in_credits);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH, starship.length);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED, starship.max_atmosphering_speed);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CREW, starship.crew);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS, starship.passengers);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY, starship.cargo_capacity);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES, starship.consumables);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS, starship.starship_class);
        values.put(SWDatabaseContract.Starship.STARSHIP_HYPERDRIVE_RATING, starship.hyperdrive_rating);
        values.put(SWDatabaseContract.Starship.STARSHIP_MGLT, starship.MGLT);
        mDatabase.insert(SWDatabaseContract.Tables.STARSHIPS, null, values);
    }

    private void insertVehicle(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(vehicle.url));
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, vehicle.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, vehicle.edited);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_NAME, vehicle.name);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL, vehicle.model);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER, vehicle.manufacturer);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS, vehicle.cost_in_credits);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH, vehicle.length);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED, vehicle.max_atmosphering_speed);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CREW, vehicle.crew);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS, vehicle.passengers);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY, vehicle.cargo_capacity);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES, vehicle.consumables);
        values.put(SWDatabaseContract.CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS, vehicle.vehicle_class);
        mDatabase.insert(SWDatabaseContract.Tables.VEHICLES, null, values);
    }
}
