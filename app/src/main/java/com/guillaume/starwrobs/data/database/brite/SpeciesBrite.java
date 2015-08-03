package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Species;
import com.guillaume.starwrobs.fragments.SWListFragment;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class SpeciesBrite {

    public static final String QUERY_SPECIES_FROM_ID = "SELECT * FROM "
            + SWDatabaseContract.Tables.SPECIES
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";
    public static final Func1<Query, SpeciesBrite> MAP = new Func1<Query, SpeciesBrite>() {
        @Override
        public SpeciesBrite call(Query query) {
            Cursor cursor = query.run();
            try {
                if (cursor.moveToFirst()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String name = Db.getString(cursor, Species.SPECIES_NAME);
                    String classification = Db.getString(cursor, Species.SPECIES_CLASSIFICATION);
                    String designation = Db.getString(cursor, Species.SPECIES_DESIGNATION);
                    String averageHeight = Db.getString(cursor, Species.SPECIES_AVERAGE_HEIGHT);
                    String skinColors = Db.getString(cursor, Species.SPECIES_SKIN_COLORS);
                    String hairColors = Db.getString(cursor, Species.SPECIES_HAIR_COLORS);
                    String eyeColors = Db.getString(cursor, Species.SPECIES_EYE_COLORS);
                    String averageLifespan = Db.getString(cursor, Species.SPECIES_AVERAGE_LIFESPAN);
                    int homeworld = Db.getInt(cursor, Species.SPECIES_HOMEWORLD);
                    String language = Db.getString(cursor, Species.SPECIES_LANGUAGE);

                    return (new AutoParcel_SpeciesBrite(id, objectId, created, edited, name, classification, designation, averageHeight, skinColors, hairColors, eyeColors, averageLifespan, homeworld, language));
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
                    String name = Db.getString(cursor, Species.SPECIES_NAME);
                    values.add(new SimpleGenericObjectForRecyclerview(objectId, SWListFragment.KEY_SPECIES, name));
                }
                return values;
            } finally {
                cursor.close();
            }
        }
    };
    public static String QUERY = ""
            + "SELECT *"
            + " FROM " + SWDatabaseContract.Tables.SPECIES
            + " ORDER BY " + Species.SPECIES_NAME + " ASC";

    public abstract long id();

    public abstract int objectId();

    public abstract String created();

    public abstract String edited();

    public abstract String name();

    public abstract String classification();

    public abstract String designation();

    public abstract String averageHeight();

    public abstract String skinColors();

    public abstract String hairColors();

    public abstract String eyeColors();

    public abstract String averageLifespan();

    public abstract int homeworld();

    public abstract String language();

    public static final class Builder extends BaseBuilder {

        public Builder name(String value) {
            values.put(Species.SPECIES_NAME, value);
            return this;
        }

        public Builder classification(String value) {
            values.put(Species.SPECIES_CLASSIFICATION, value);
            return this;
        }

        public Builder designation(String value) {
            values.put(Species.SPECIES_DESIGNATION, value);
            return this;
        }

        public Builder averageHeight(String value) {
            values.put(Species.SPECIES_AVERAGE_HEIGHT, value);
            return this;
        }

        public Builder skinColors(String value) {
            values.put(Species.SPECIES_SKIN_COLORS, value);
            return this;
        }

        public Builder hairColors(String value) {
            values.put(Species.SPECIES_HAIR_COLORS, value);
            return this;
        }

        public Builder eyeColors(String value) {
            values.put(Species.SPECIES_EYE_COLORS, value);
            return this;
        }

        public Builder averageLifespan(String value) {
            values.put(Species.SPECIES_AVERAGE_LIFESPAN, value);
            return this;
        }

        public Builder homeworld(int value) {
            values.put(Species.SPECIES_HOMEWORLD, value);
            return this;
        }

        public Builder language(String value) {
            values.put(Species.SPECIES_LANGUAGE, value);
            return this;
        }
    }
}