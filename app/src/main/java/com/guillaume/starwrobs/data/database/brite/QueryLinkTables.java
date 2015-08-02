package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.SimpleIds;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class QueryLinkTables {

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_FILMS_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int filmId = Db.getInt(cursor, SimpleIds.FILM_ID);
                    list.add(filmId);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_PEOPLE_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int filmId = Db.getInt(cursor, SimpleIds.PEOPLE_ID);
                    list.add(filmId);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_PLANETS_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int id = Db.getInt(cursor, SimpleIds.PLANETS_ID);
                    list.add(id);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_SPECIES_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int speciesId = Db.getInt(cursor, SimpleIds.SPECIES_ID);
                    list.add(speciesId);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_STARSHIPS_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int id = Db.getInt(cursor, SimpleIds.STARSHIPS_ID);
                    list.add(id);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_ALL_VEHICLES_IDS = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int id = Db.getInt(cursor, SimpleIds.VEHICLES_ID);
                    list.add(id);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    /**
     * PEOPLE - FILMS
     */

    public static final String QUERY_LINK_PEOPLE_FILMS_GET_FILMS_FOR_PEOPLE_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_FILMS
            + " WHERE "
            + SimpleIds.PEOPLE_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.FILM_ID
            + " ASC";

    public static final String QUERY_LINK_PEOPLE_FILMS_GET_PEOPLE_FOR_FILM_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_FILMS
            + " WHERE "
            + SimpleIds.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.PEOPLE_ID
            + " ASC";

    /**
     * PEOPLE - SPECIES
     */

    public static final String QUERY_LINK_PEOPLE_SPECIES_GET_SPECIES_FOR_PEOPLE_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_SPECIES
            + " WHERE "
            + SimpleIds.PEOPLE_ID
            + " = ?";

    public static final String QUERY_LINK_PEOPLE_SPECIES_GET_PEOPLE_FOR_SPECIES_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_SPECIES
            + " WHERE "
            + SimpleIds.SPECIES_ID
            + " = ?";

    /**
     * PEOPLE - STARSHIPS
     */

    public static final String QUERY_LINK_PEOPLE_STARSHIPS_GET_STARSHIPS_FOR_PEOPLE_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_STARSHIPS
            + " WHERE "
            + SimpleIds.PEOPLE_ID
            + " = ?";

    public static final String QUERY_LINK_PEOPLE_STARSHIPS_GET_PEOPLE_FOR_STARSHIPS_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_STARSHIPS
            + " WHERE "
            + SimpleIds.PEOPLE_ID
            + " = ?";

    /**
     * PEOPLE - VEHICLES
     */

    public static final String QUERY_LINK_PEOPLE_VEHICLES_GET_VEHICLES_FOR_PEOPLE_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_VEHICLES
            + " WHERE "
            + SimpleIds.PEOPLE_ID
            + " = ?";

    public static final String QUERY_LINK_PEOPLE_VEHICLES_GET_PEOPLE_FOR_VEHICLE_ID = "SELECT * FROM "
            + LinkTables.LINK_PEOPLE_VEHICLES
            + " WHERE "
            + SimpleIds.VEHICLES_ID
            + " = ?";

    /**
     * FILMS - PLANETS
     */

    public static final String QUERY_LINK_FILMS_PLANETS_GET_PLANETS_FOR_FILM_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_PLANETS
            + " WHERE "
            + SimpleIds.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.PLANETS_ID
            + " ASC";

    public static final String QUERY_LINK_FILMS_PLANETS_GET_FILMS_FOR_PLANET_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_PLANETS
            + " WHERE "
            + SimpleIds.PLANETS_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.FILM_ID
            + " ASC";

    /**
     * FILMS - SPECIES
     */

    public static final String QUERY_LINK_FILMS_SPECIES_GET_SPECIES_FOR_FILM_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_SPECIES
            + " WHERE "
            + SimpleIds.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.SPECIES_ID
            + " ASC";

    public static final String QUERY_LINK_FILMS_SPECIES_GET_FILM_FOR_SPECIES_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_SPECIES
            + " WHERE "
            + SimpleIds.SPECIES_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.FILM_ID
            + " ASC";

    /**
     * FILMS - STARSHIPS
     */

    public static final String QUERY_LINK_FILMS_STARSHIPS_GET_STARSHIPS_FOR_FILM_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_STARSHIPS
            + " WHERE "
            + SimpleIds.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.STARSHIPS_ID
            + " ASC";

    public static final String QUERY_LINK_FILMS_STARSHIPS_GET_FILMS_FOR_STARSHIPS_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_STARSHIPS
            + " WHERE "
            + SimpleIds.STARSHIPS_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.FILM_ID
            + " ASC";

    /**
     * FILMS - VEHICLES
     */

    public static final String QUERY_LINK_FILMS_VEHICLES_GET_VEHICLES_FOR_FILM_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_VEHICLES
            + " WHERE "
            + SimpleIds.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.VEHICLES_ID
            + " ASC";

    public static final String QUERY_LINK_FILMS_VEHICLES_GET_FILM_FOR_VEHICLES_ID = "SELECT * FROM "
            + LinkTables.LINK_FILMS_VEHICLES
            + " WHERE "
            + SimpleIds.VEHICLES_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.FILM_ID
            + " ASC";

    /**
     * PLANETS - PEOPLE
     */

    public static final String QUERY_LINK_PLANETS_PEOPLE_GET_PEOPLE_FOR_PLANET_ID = "SELECT * FROM "
            + LinkTables.LINK_PLANETS_PEOPLE
            + " WHERE "
            + SimpleIds.PLANETS_ID
            + " = ?"
            + " ORDER BY "
            + SimpleIds.PEOPLE_ID
            + " ASC";

}
