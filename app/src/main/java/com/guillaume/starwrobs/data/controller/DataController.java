package com.guillaume.starwrobs.data.controller;


import android.content.ContentValues;
import android.content.Context;

import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonStarshipVehicle;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.SimpleIds;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
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
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

public class DataController {

    @Inject
    BriteDatabase mDatabase;

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

    public void deleteDatabase() {
        mDatabase.delete(Tables.PEOPLE, null);
        mDatabase.delete(LinkTables.LINK_PEOPLE_FILMS, null);
        mDatabase.delete(LinkTables.LINK_PEOPLE_SPECIES, null);
        mDatabase.delete(LinkTables.LINK_PEOPLE_STARSHIPS, null);
        mDatabase.delete(LinkTables.LINK_PEOPLE_VEHICLES, null);

        mDatabase.delete(Tables.FILMS, null);
        mDatabase.delete(LinkTables.LINK_FILMS_PLANETS, null);
        mDatabase.delete(LinkTables.LINK_FILMS_SPECIES, null);
        mDatabase.delete(LinkTables.LINK_FILMS_STARSHIPS, null);
        mDatabase.delete(LinkTables.LINK_FILMS_VEHICLES, null);

        mDatabase.delete(Tables.PLANETS, null);
        mDatabase.delete(LinkTables.LINK_PLANETS_PEOPLE, null);

        mDatabase.delete(Tables.SPECIES, null);
        mDatabase.delete(Tables.VEHICLES, null);
        mDatabase.delete(Tables.STARSHIPS, null);
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
                        Timber.d("people = " + people.name);
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
                        Timber.d("film = " + film.title);
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
                        Timber.d("planet = " + planet.name);
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
                        Timber.d("species = " + species.name);
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
                        Timber.d("starship = " + starship.name);
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
                        Timber.d("vehicle = " + vehicle.name);
                        insertVehicle(vehicle);
                    }
                });
    }


    private void insertPeople(People people) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(people.url);
        values.put(CommonColumns.COMMON_ID, id);
        values.put(CommonColumns.COMMON_CREATED, people.created);
        values.put(CommonColumns.COMMON_EDITED, people.edited);
        values.put(SWDatabaseContract.People.PEOPLE_NAME, people.name);
        values.put(SWDatabaseContract.People.PEOPLE_HEIGHT, people.height);
        values.put(SWDatabaseContract.People.PEOPLE_MASS, people.mass);
        values.put(SWDatabaseContract.People.PEOPLE_HAIR_COLOR, people.hair_color);
        values.put(SWDatabaseContract.People.PEOPLE_SKIN_COLOR, people.skin_color);
        values.put(SWDatabaseContract.People.PEOPLE_EYE_COLOR, people.eye_color);
        values.put(SWDatabaseContract.People.PEOPLE_BIRTH_YEAR, people.birth_year);
        values.put(SWDatabaseContract.People.PEOPLE_GENDER, people.gender);
        values.put(SWDatabaseContract.People.PEOPLE_HOMEWORLD, SWFunctions.getIdFromUrl(people.homeworld));
        mDatabase.insert(Tables.PEOPLE, values);


        int nbOfIterations = people.films.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.PEOPLE_ID, id);
            values.put(SimpleIds.FILM_ID, SWFunctions.getIdFromUrl(people.films.get(i)));
            mDatabase.insert(LinkTables.LINK_PEOPLE_FILMS, values);
        }

        nbOfIterations = people.species.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.PEOPLE_ID, id);
            values.put(SimpleIds.SPECIES_ID, SWFunctions.getIdFromUrl(people.species.get(i)));
            mDatabase.insert(LinkTables.LINK_PEOPLE_SPECIES, values);
        }

        nbOfIterations = people.vehicles.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.PEOPLE_ID, id);
            values.put(SimpleIds.VEHICLES_ID, SWFunctions.getIdFromUrl(people.vehicles.get(i)));
            mDatabase.insert(LinkTables.LINK_PEOPLE_VEHICLES, values);
        }

        nbOfIterations = people.starships.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.PEOPLE_ID, id);
            values.put(SimpleIds.STARSHIPS_ID, SWFunctions.getIdFromUrl(people.starships.get(i)));
            mDatabase.insert(LinkTables.LINK_PEOPLE_STARSHIPS, values);
        }
    }

    private void insertFilm(Film film) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(film.url);
        values.put(CommonColumns.COMMON_ID, id);
        values.put(CommonColumns.COMMON_CREATED, film.created);
        values.put(CommonColumns.COMMON_EDITED, film.edited);
        values.put(SWDatabaseContract.Film.FILM_TITLE, film.title);
        values.put(SWDatabaseContract.Film.FILM_DIRECTOR, film.director);
        values.put(SWDatabaseContract.Film.FILM_PRODUCER, film.producer);
        values.put(SWDatabaseContract.Film.FILM_EPISODE_ID, film.episode_id);
        values.put(SWDatabaseContract.Film.FILM_OPENING_CRAWL, film.opening_crawl);
        values.put(SWDatabaseContract.Film.FILM_RELEASE_DATE, film.release_date);
        mDatabase.insert(Tables.FILMS, values);


        int nbOfIterations = film.planets.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.FILM_ID, id);
            values.put(SimpleIds.PLANETS_ID, SWFunctions.getIdFromUrl(film.planets.get(i)));
            mDatabase.insert(LinkTables.LINK_FILMS_PLANETS, values);
        }

        nbOfIterations = film.species.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.FILM_ID, id);
            values.put(SimpleIds.SPECIES_ID, SWFunctions.getIdFromUrl(film.species.get(i)));
            mDatabase.insert(LinkTables.LINK_FILMS_SPECIES, values);
        }

        nbOfIterations = film.starships.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.FILM_ID, id);
            values.put(SimpleIds.STARSHIPS_ID, SWFunctions.getIdFromUrl(film.starships.get(i)));
            mDatabase.insert(LinkTables.LINK_FILMS_STARSHIPS, values);
        }

        nbOfIterations = film.vehicles.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.FILM_ID, id);
            values.put(SimpleIds.VEHICLES_ID, SWFunctions.getIdFromUrl(film.vehicles.get(i)));
            mDatabase.insert(LinkTables.LINK_FILMS_VEHICLES, values);
        }
    }

    private void insertPlanet(Planet planet) {
        ContentValues values = new ContentValues();
        values.clear();
        int id = SWFunctions.getIdFromUrl(planet.url);
        values.put(CommonColumns.COMMON_ID, id);
        values.put(CommonColumns.COMMON_CREATED, planet.created);
        values.put(CommonColumns.COMMON_EDITED, planet.edited);
        values.put(SWDatabaseContract.Planet.PLANET_NAME, planet.name);
        values.put(SWDatabaseContract.Planet.PLANET_ROTATION_PERIOD, planet.rotation_period);
        values.put(SWDatabaseContract.Planet.PLANET_ORBITAL_PERIOD, planet.orbital_period);
        values.put(SWDatabaseContract.Planet.PLANET_DIAMETER, planet.diameter);
        values.put(SWDatabaseContract.Planet.PLANET_CLIMATE, planet.climate);
        values.put(SWDatabaseContract.Planet.PLANET_GRAVITY, planet.gravity);
        values.put(SWDatabaseContract.Planet.PLANET_TERRAIN, planet.terrain);
        values.put(SWDatabaseContract.Planet.PLANET_SURFACE_WATER, planet.surface_water);
        values.put(SWDatabaseContract.Planet.PLANET_POPULATION, planet.population);
        mDatabase.insert(Tables.PLANETS, values);


        int nbOfIterations = planet.residents.size();
        for (int i = 0; i < nbOfIterations; i++) {
            values.clear();
            values.put(SimpleIds.PLANETS_ID, id);
            values.put(SimpleIds.PEOPLE_ID, SWFunctions.getIdFromUrl(planet.residents.get(i)));
            mDatabase.insert(LinkTables.LINK_PLANETS_PEOPLE, values);
        }
    }

    private void insertSpecies(Species species) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(species.url));
        values.put(CommonColumns.COMMON_CREATED, species.created);
        values.put(CommonColumns.COMMON_EDITED, species.edited);
        values.put(SWDatabaseContract.Species.SPECIES_NAME, species.name);
        values.put(SWDatabaseContract.Species.SPECIES_CLASSIFICATION, species.classification);
        values.put(SWDatabaseContract.Species.SPECIES_DESIGNATION, species.designation);
        values.put(SWDatabaseContract.Species.SPECIES_AVERAGE_HEIGHT, species.average_height);
        values.put(SWDatabaseContract.Species.SPECIES_SKIN_COLORS, species.skin_colors);
        values.put(SWDatabaseContract.Species.SPECIES_HAIR_COLORS, species.hair_colors);
        values.put(SWDatabaseContract.Species.SPECIES_EYE_COLORS, species.eye_colors);
        values.put(SWDatabaseContract.Species.SPECIES_AVERAGE_LIFESPAN, species.average_lifespan);
        if (species.homeworld != null) {
            values.put(SWDatabaseContract.Species.SPECIES_HOMEWORLD, SWFunctions.getIdFromUrl(species.homeworld));
        }
        values.put(SWDatabaseContract.Species.SPECIES_LANGUAGE, species.language);
        mDatabase.insert(Tables.SPECIES, values);
    }

    private void insertStarship(Starship starship) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(starship.url));
        values.put(CommonColumns.COMMON_CREATED, starship.created);
        values.put(CommonColumns.COMMON_EDITED, starship.edited);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_NAME, starship.name);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL, starship.model);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER, starship.manufacturer);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS, starship.cost_in_credits);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH, starship.length);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED, starship.max_atmosphering_speed);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CREW, starship.crew);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS, starship.passengers);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY, starship.cargo_capacity);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES, starship.consumables);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS, starship.starship_class);
        values.put(SWDatabaseContract.Starship.STARSHIP_HYPERDRIVE_RATING, starship.hyperdrive_rating);
        values.put(SWDatabaseContract.Starship.STARSHIP_MGLT, starship.MGLT);
        mDatabase.insert(Tables.STARSHIPS, values);
    }

    private void insertVehicle(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CommonColumns.COMMON_ID, SWFunctions.getIdFromUrl(vehicle.url));
        values.put(CommonColumns.COMMON_CREATED, vehicle.created);
        values.put(CommonColumns.COMMON_EDITED, vehicle.edited);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_NAME, vehicle.name);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL, vehicle.model);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER, vehicle.manufacturer);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS, vehicle.cost_in_credits);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH, vehicle.length);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED, vehicle.max_atmosphering_speed);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CREW, vehicle.crew);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS, vehicle.passengers);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY, vehicle.cargo_capacity);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES, vehicle.consumables);
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS, vehicle.vehicle_class);
        mDatabase.insert(Tables.VEHICLES, values);
    }
}
