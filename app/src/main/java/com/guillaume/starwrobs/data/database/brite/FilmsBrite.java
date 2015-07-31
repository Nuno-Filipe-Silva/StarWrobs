package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Film;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class FilmsBrite {

    public static String QUERY = ""
            + "SELECT *"
            + " FROM " + SWDatabaseContract.Tables.FILMS
            + " ORDER BY " + Film.FILM_EPISODE_ID + " ASC";

    public static final Func1<Query, List<FilmsBrite>> MAP = new Func1<Query, List<FilmsBrite>>() {
        @Override
        public List<FilmsBrite> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<FilmsBrite> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String title = Db.getString(cursor, Film.FILM_TITLE);
                    int episodeId = Db.getInt(cursor, Film.FILM_EPISODE_ID);
                    String openingCrawl = Db.getString(cursor, Film.FILM_OPENING_CRAWL);
                    String director = Db.getString(cursor, Film.FILM_DIRECTOR);
                    String producer = Db.getString(cursor, Film.FILM_PRODUCER);
                    String releaseDate = Db.getString(cursor, Film.FILM_RELEASE_DATE);

                    values.add(new AutoParcel_FilmsBrite(id, objectId, created, edited, title, episodeId, openingCrawl, director, producer, releaseDate));
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<Query, List<String>> MAP_STRING = new Func1<Query, List<String>>() {
        @Override
        public List<String> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<String> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    String name = Db.getString(cursor, Film.FILM_TITLE)
                                + " (ep " + Db.getInt(cursor, Film.FILM_EPISODE_ID) + ")";
                    values.add(name);
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public abstract long id();

    public abstract int objectId();

    public abstract String created();

    public abstract String edited();

    public abstract String title();

    public abstract int episodeId();

    public abstract String openingCrawl();

    public abstract String director();

    public abstract String producer();

    public abstract String releaseDate();

    public static final class Builder extends BaseBuilder {

        public Builder title(String title) {
            values.put(Film.FILM_TITLE, title);
            return this;
        }

        public Builder episodeId(int episodeId) {
            values.put(Film.FILM_EPISODE_ID, episodeId);
            return this;
        }

        public Builder openingCrawl(String openingCrawl) {
            values.put(Film.FILM_OPENING_CRAWL, openingCrawl);
            return this;
        }

        public Builder director(String director) {
            values.put(Film.FILM_DIRECTOR, director);
            return this;
        }

        public Builder producer(String producer) {
            values.put(Film.FILM_PRODUCER, producer);
            return this;
        }

        public Builder releaseDate(String releaseDate) {
            values.put(Film.FILM_RELEASE_DATE, releaseDate);
            return this;
        }
    }
}