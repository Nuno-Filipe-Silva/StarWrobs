package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class QueryLinkTables {

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_FILMS_WHERE_PEOPLE = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int filmId = Db.getInt(cursor, SWDatabaseContract.LinkPeopleFilms.FILM_ID);
                    list.add(filmId);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_SPECIES_WHERE_PEOPLE = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int speciesId = Db.getInt(cursor, SWDatabaseContract.LinkPeopleSpecies.SPECIES_ID);
                    list.add(speciesId);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_STARSHIPS_WHERE_PEOPLE = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int id = Db.getInt(cursor, SWDatabaseContract.LinkPeopleStarships.STARSHIP_ID);
                    list.add(id);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<SqlBrite.Query, List<Integer>> QUERY_GET_VEHICLES_WHERE_PEOPLE = new Func1<SqlBrite.Query, List<Integer>>() {
        @Override
        public List<Integer> call(SqlBrite.Query query) {
            Cursor cursor = query.run();
            try {
                List<Integer> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int id = Db.getInt(cursor, SWDatabaseContract.LinkPeopleVehicles.VEHICLE_ID);
                    list.add(id);
                }
                return list;
            } finally {
                cursor.close();
            }
        }
    };

}
