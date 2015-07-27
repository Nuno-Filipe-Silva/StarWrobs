package com.guillaume.starwrobs.data.data.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.People;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.Film;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.Planet;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.Species;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.CommonStarshipVehicle;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.Starship;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkFilmsPlanets;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkFilmsSpecies;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkFilmsStarships;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkFilmsVehicles;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkPeopleFilms;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkPeopleSpecies;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkPeopleStarships;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkPeopleVehicles;
import com.guillaume.starwrobs.data.data.database.SWDatabaseContract.LinkPlanetsPeople;


public class SWDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "starwrobs.db";

    private static final int VERSION_1 = 1;

    private static final int CUR_DATABASE_VERSION = VERSION_1;

    // Text fields for TEXT, INTEGER, ...
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_TEXT_NOT_NULL = " TEXT NOT NULL";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_INTEGER_NOT_NULL = " INTEGER NOT NULL";
    private static final String TYPE_REAL = " REAL";
    private static final String COMMA_SEP = ",";

    private Context mContext;
    private static SWDatabaseHelper mInstance;


    public SWDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    public static synchronized SWDatabaseHelper getHelper(Context context) {
        if (mInstance == null)
            mInstance = new SWDatabaseHelper(context);
        return mInstance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Tables.PEOPLE + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_NAME + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_HEIGHT + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_MASS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_HAIR_COLOR + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_SKIN_COLOR + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_EYE_COLOR + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_BIRTH_YEAR + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_GENDER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + People.PEOPLE_HOMEWORLD + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.FILMS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Film.FILM_TITLE + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Film.FILM_EPISODE_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + Film.FILM_OPENING_CRAWL + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Film.FILM_DIRECTOR + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Film.FILM_PRODUCER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Film.FILM_RELEASE_DATE + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.PLANETS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_NAME + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_ROTATION_PERIOD + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_ORBITAL_PERIOD + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_DIAMETER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_CLIMATE + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_GRAVITY + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_TERRAIN + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_SURFACE_WATER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Planet.PLANET_POPULATION + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.SPECIES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_NAME + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_CLASSIFICATION + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_DESIGNATION + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_AVERAGE_HEIGHT + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_SKIN_COLORS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_HAIR_COLORS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_EYE_COLORS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_AVERAGE_LIFESPAN + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Species.SPECIES_HOMEWORLD + TYPE_TEXT + COMMA_SEP
                        + Species.SPECIES_LANGUAGE + TYPE_TEXT + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.STARSHIPS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_NAME + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CREW + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + Starship.STARSHIP_HYPERDRIVE_RATING + TYPE_TEXT + COMMA_SEP
                        + Starship.STARSHIP_MGLT + TYPE_TEXT + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS + TYPE_TEXT + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.VEHICLES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CommonColumns.COMMON_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_CREATED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonColumns.COMMON_EDITED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_NAME + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CREW + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES + TYPE_TEXT_NOT_NULL + COMMA_SEP
                        + CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS + TYPE_TEXT + COMMA_SEP
                        + "UNIQUE (" + CommonColumns.COMMON_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_PEOPLE_FILMS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkPeopleFilms.PEOPLE_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkPeopleFilms.FILM_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_PEOPLE_SPECIES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkPeopleSpecies.PEOPLE_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkPeopleSpecies.SPECIES_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_PEOPLE_STARSHIPS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkPeopleStarships.PEOPLE_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkPeopleStarships.STARSHIP_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_PEOPLE_VEHICLES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkPeopleVehicles.PEOPLE_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkPeopleVehicles.VEHICLE_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_FILMS_PLANETS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkFilmsPlanets.FILM_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkFilmsPlanets.STARSHIPS_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_FILMS_SPECIES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkFilmsSpecies.FILM_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkFilmsSpecies.SPECIES_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_FILMS_STARSHIPS + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkFilmsStarships.FILM_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkFilmsStarships.STARSHIPS_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_FILMS_VEHICLES + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkFilmsVehicles.FILM_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkFilmsVehicles.VEHICLES_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

        db.execSQL("CREATE TABLE " + LinkTables.LINK_PLANETS_PEOPLE + " ("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LinkPlanetsPeople.PLANET_ID + TYPE_INTEGER_NOT_NULL + COMMA_SEP
                        + LinkPlanetsPeople.PEOPLE_ID + TYPE_INTEGER_NOT_NULL
                        + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
