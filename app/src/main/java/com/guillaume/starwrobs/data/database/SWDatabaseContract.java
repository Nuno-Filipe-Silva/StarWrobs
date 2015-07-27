package com.guillaume.starwrobs.data.data.database;


import android.net.Uri;

public class SWDatabaseContract {

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.guillaume.starwrobs.provider";

    /**
     * Base URI. (content://com.guillaume.starwrobs.provider)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Empty constructor
     */
    private SWDatabaseContract() {
    }

    /**
     * Common col
     */
    interface CommonColumns {
        String COMMON_ID = "id";
        String COMMON_CREATED = "created";
        String COMMON_EDITED = "edited";
    }

    /* People have many FILMS, SPECIES, VEHICLES and STARSHIPS */
    interface People {
        String PEOPLE_NAME = "people_name";
        String PEOPLE_HEIGHT = "people_height";
        String PEOPLE_MASS = "people_mass";
        String PEOPLE_HAIR_COLOR = "people_hair_color";
        String PEOPLE_SKIN_COLOR = "people_skin_color";
        String PEOPLE_EYE_COLOR = "people_eye_color";
        String PEOPLE_BIRTH_YEAR = "people_birth_year";
        String PEOPLE_GENDER = "people_gender";
        String PEOPLE_HOMEWORLD = "people_homeworld";
    }

    /* A film has many PEOPLE, PLANETS, VEHICLES, SPECIES and STARSHIPS */
    interface Film {
        String FILM_TITLE = "film_title";
        String FILM_EPISODE_ID = "film_episode_id";
        String FILM_OPENING_CRAWL = "film_opening_crawl";
        String FILM_DIRECTOR = "film_director";
        String FILM_PRODUCER = "film_producer";
        String FILM_RELEASE_DATE = "film_release_date";
    }

    /* A planet has many FILMS and PEOPLE */
    interface Planet {
        String PLANET_NAME = "planet_name";
        String PLANET_DIAMETER = "planet_diameter";
        String PLANET_ROTATION_PERIOD = "planet_rotation_period";
        String PLANET_ORBITAL_PERIOD = "planet_orbital_period";
        String PLANET_GRAVITY = "planet_gravity";
        String PLANET_POPULATION = "planet_population";
        String PLANET_CLIMATE = "planet_climate";
        String PLANET_TERRAIN = "planet_terrain";
        String PLANET_SURFACE_WATER = "planet_surface_water";
    }

    /* Species has many PEOPLE and FILMS */
    interface Species {
        String SPECIES_NAME = "species_name";
        String SPECIES_CLASSIFICATION = "species_classification";
        String SPECIES_DESIGNATION = "species_designation";
        String SPECIES_AVERAGE_HEIGHT = "species_average_height";
        String SPECIES_AVERAGE_LIFESPAN = "species_average_lifespan";
        String SPECIES_HAIR_COLORS = "species_hair_colors";
        String SPECIES_SKIN_COLORS = "species_skin_colors";
        String SPECIES_EYE_COLORS = "species_eye_colors";
        String SPECIES_HOMEWORLD = "species_homeworld";
        String SPECIES_LANGUAGE = "species_language";
    }

    /* A starship has many FILMS, PEOPLE*/
    interface Starship {
        String STARSHIP_HYPERDRIVE_RATING = "starship_hyperdrive_rating";
        String STARSHIP_MGLT = "starship_mglt";
    }

    interface CommonStarshipVehicle {
        String STARSHIP_VEHICLE_NAME = "name";
        String STARSHIP_VEHICLE_MODEL = "model";
        String STARSHIP_VEHICLE_MANUFACTURER = "manufacturer";
        String STARSHIP_VEHICLE_COST_IN_CREDITS = "cost_in_credits";
        String STARSHIP_VEHICLE_LENGTH = "length";
        String STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED = "max_atmosphering_speed";
        String STARSHIP_VEHICLE_CREW = "crew";
        String STARSHIP_VEHICLE_PASSENGERS = "passengers";
        String STARSHIP_VEHICLE_CARGO_CAPACITY = "cargo_capacity";
        String STARSHIP_VEHICLE_CONSUMABLES = "consumables";
        String STARSHIP_VEHICLE_CLASS = "class";
    }

    interface LinkPeopleFilms {
        String PEOPLE_ID = "people_id";
        String FILM_ID = "film_id";
    }

    interface LinkPeopleSpecies {
        String PEOPLE_ID = "people_id";
        String SPECIES_ID = "species_id";
    }

    interface LinkPeopleStarships {
        String PEOPLE_ID = "people_id";
        String STARSHIP_ID = "starship_id";
    }

    interface LinkPeopleVehicles {
        String PEOPLE_ID = "people_id";
        String VEHICLE_ID = "vehicle_id";
    }

    interface LinkFilmsPlanets {
        String FILM_ID = "film_id";
        String STARSHIPS_ID = "starships_id";
    }

    interface LinkFilmsStarships {
        String FILM_ID = "film_id";
        String STARSHIPS_ID = "starships_id";
    }

    interface LinkFilmsSpecies {
        String FILM_ID = "film_id";
        String SPECIES_ID = "species_id";
    }

    interface LinkFilmsVehicles {
        String FILM_ID = "film_id";
        String VEHICLES_ID = "vehicles_id";
    }

    interface LinkPlanetsPeople {
        String PLANET_ID = "planet_id";
        String PEOPLE_ID = "people_id";
    }



    interface Tables {
        String FILMS = "table_films";
        String PEOPLE = "table_people";
        String PLANETS = "table_planets";
        String SPECIES = "table_species";
        String STARSHIPS = "table_starships";
        String VEHICLES = "table_vehicles";
    }

    interface LinkTables {
        String LINK_PEOPLE_FILMS = "table_link_people_films";
        String LINK_PEOPLE_SPECIES = "table_link_people_species";
        String LINK_PEOPLE_STARSHIPS = "table_link_people_starships";
        String LINK_PEOPLE_VEHICLES = "table_link_people_vehicles";

        String LINK_FILMS_PLANETS = "table_link_films_planets";
        String LINK_FILMS_STARSHIPS = "table_link_films_starships";
        String LINK_FILMS_SPECIES = "table_link_films_species";
        String LINK_FILMS_VEHICLES = "table_link_films_vehicles";

        String LINK_PLANETS_PEOPLE = "table_link_planets_people";
    }
}
