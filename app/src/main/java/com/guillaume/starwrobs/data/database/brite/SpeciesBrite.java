package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Species;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class SpeciesBrite {

    public static final Func1<Query, List<SpeciesBrite>> MAP = new Func1<Query, List<SpeciesBrite>>() {
        @Override
        public List<SpeciesBrite> call(Query query) {
            Cursor cursor = query.run();
            try {
                List<SpeciesBrite> values = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
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
                    String homeworld = Db.getString(cursor, Species.SPECIES_HOMEWORLD);
                    String language = Db.getString(cursor, Species.SPECIES_LANGUAGE);

                    values.add(new AutoParcel_SpeciesBrite(id, objectId, created, edited, name, classification, designation, averageHeight, skinColors, hairColors, eyeColors, averageLifespan, homeworld, language));
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

    public abstract String classification();

    public abstract String designation();

    public abstract String averageHeight();

    public abstract String skinColors();

    public abstract String hairColors();

    public abstract String eyeColors();

    public abstract String averageLifespan();

    public abstract String homeworld();

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

        public Builder homeworld(String value) {
            values.put(Species.SPECIES_HOMEWORLD, value);
            return this;
        }

        public Builder language(String value) {
            values.put(Species.SPECIES_LANGUAGE, value);
            return this;
        }
    }
}