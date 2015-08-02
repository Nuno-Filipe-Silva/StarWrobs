package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonStarshipVehicle;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Starship;
import com.guillaume.starwrobs.fragments.SWListFragment;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class StarshipsBrite {

    public static String QUERY = ""
            + "SELECT *"
            + " FROM " + SWDatabaseContract.Tables.STARSHIPS
            + " ORDER BY " + CommonStarshipVehicle.STARSHIP_VEHICLE_NAME + " ASC";

    public static final String QUERY_STARSHIPS_FROM_ID = "SELECT * FROM "
            + SWDatabaseContract.Tables.STARSHIPS
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    public static final Func1<Query, List<StarshipsBrite>> MAP = new Func1<Query, List<StarshipsBrite>>() {
        @Override
        public List<StarshipsBrite> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<StarshipsBrite> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String name = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_NAME);
                    String model = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL);
                    String manufacturer = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER);
                    String costInCredits = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS);
                    String length = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH);
                    String maxAtmospheringSpeed = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED);
                    String crew = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CREW);
                    String passengers = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS);
                    String cargoCapacity = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY);
                    String consumables = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES);
                    String objectClass = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS);

                    String hyperdriveRating = Db.getString(cursor, Starship.STARSHIP_HYPERDRIVE_RATING);
                    String MGLT = Db.getString(cursor, Starship.STARSHIP_MGLT);

                    values.add(new AutoParcel_StarshipsBrite(id, objectId, created, edited, name, model, manufacturer, costInCredits, length, maxAtmospheringSpeed, crew, passengers, cargoCapacity, consumables, objectClass, hyperdriveRating, MGLT));
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };

    public static final Func1<Query, StarshipsBrite> MAP_STARSHIPSBRITE_UNIQUE = new Func1<Query, StarshipsBrite>() {
        @Override
        public StarshipsBrite call(Query query) {
            Cursor cursor = query.run();
            try {
                if (cursor.moveToFirst()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String name = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_NAME);
                    String model = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL);
                    String manufacturer = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER);
                    String costInCredits = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS);
                    String length = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH);
                    String maxAtmospheringSpeed = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED);
                    String crew = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CREW);
                    String passengers = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS);
                    String cargoCapacity = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY);
                    String consumables = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES);
                    String objectClass = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS);

                    String hyperdriveRating = Db.getString(cursor, Starship.STARSHIP_HYPERDRIVE_RATING);
                    String MGLT = Db.getString(cursor, Starship.STARSHIP_MGLT);

                    return new AutoParcel_StarshipsBrite(id, objectId, created, edited, name, model, manufacturer, costInCredits, length, maxAtmospheringSpeed, crew, passengers, cargoCapacity, consumables, objectClass, hyperdriveRating, MGLT);
                }
            } finally {
                cursor.close();
            }
            return null;
        }
    };

    public static final Func1<Query, List<SimpleGenericObjectForRecyclerview>> MAP_STRING = new Func1<Query, List<SimpleGenericObjectForRecyclerview>>() {
        @Override
        public List<SimpleGenericObjectForRecyclerview> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<SimpleGenericObjectForRecyclerview> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String name = Db.getString(cursor, CommonStarshipVehicle.STARSHIP_VEHICLE_NAME);
                    values.add(new SimpleGenericObjectForRecyclerview(objectId, SWListFragment.KEY_STARSHIPS, name));
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

    public abstract String model();

    public abstract String manufacturer();

    public abstract String costInCredits();

    public abstract String length();

    public abstract String maxAtmospheringSpeed();

    public abstract String crew();

    public abstract String passengers();

    public abstract String cargoCapacity();

    public abstract String consumables();

    public abstract String objectClass();

    public abstract String hyperdriveRating();

    public abstract String MGLT();

    public static final class Builder extends CommonStarshipVehicleBuilder {

        public Builder hyperdriveRating(String value) {
            values.put(Starship.STARSHIP_HYPERDRIVE_RATING, value);
            return this;
        }

        public Builder MGLT(String value) {
            values.put(Starship.STARSHIP_MGLT, value);
            return this;
        }
    }
}