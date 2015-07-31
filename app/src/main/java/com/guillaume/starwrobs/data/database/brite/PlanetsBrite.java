package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Planet;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class PlanetsBrite {

    public static final Func1<Query, List<PlanetsBrite>> MAP = new Func1<Query, List<PlanetsBrite>>() {
        @Override
        public List<PlanetsBrite> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<PlanetsBrite> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String name = Db.getString(cursor, Planet.PLANET_NAME);
                    String rotationPeriod = Db.getString(cursor, Planet.PLANET_ROTATION_PERIOD);
                    String orbitalPeriod = Db.getString(cursor, Planet.PLANET_ORBITAL_PERIOD);
                    String diameter = Db.getString(cursor, Planet.PLANET_DIAMETER);
                    String climate = Db.getString(cursor, Planet.PLANET_CLIMATE);
                    String gravity = Db.getString(cursor, Planet.PLANET_GRAVITY);
                    String terrain = Db.getString(cursor, Planet.PLANET_TERRAIN);
                    String surfaceWater = Db.getString(cursor, Planet.PLANET_SURFACE_WATER);
                    String population = Db.getString(cursor, Planet.PLANET_POPULATION);

                    values.add(new AutoParcel_PlanetsBrite(id, objectId, created, edited, name, rotationPeriod, orbitalPeriod, diameter, climate, gravity, terrain, surfaceWater, population));
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

    public abstract String name();

    public abstract String rotationPeriod();

    public abstract String orbitalPeriod();

    public abstract String diameter();

    public abstract String climate();

    public abstract String gravity();

    public abstract String terrain();

    public abstract String surfaceWater();

    public abstract String population();

    public static final class Builder extends BaseBuilder {

        public Builder name(String value) {
            values.put(Planet.PLANET_NAME, value);
            return this;
        }

        public Builder rotationPeriod(String value) {
            values.put(Planet.PLANET_ROTATION_PERIOD, value);
            return this;
        }

        public Builder orbitalPeriod(String value) {
            values.put(Planet.PLANET_ORBITAL_PERIOD, value);
            return this;
        }

        public Builder diameter(String value) {
            values.put(Planet.PLANET_DIAMETER, value);
            return this;
        }

        public Builder climate(String value) {
            values.put(Planet.PLANET_CLIMATE, value);
            return this;
        }

        public Builder gravity(String value) {
            values.put(Planet.PLANET_GRAVITY, value);
            return this;
        }

        public Builder terrain(String value) {
            values.put(Planet.PLANET_TERRAIN, value);
            return this;
        }

        public Builder surfaceWater(String value) {
            values.put(Planet.PLANET_SURFACE_WATER, value);
            return this;
        }

        public Builder population(String value) {
            values.put(Planet.PLANET_POPULATION, value);
            return this;
        }
    }
}
