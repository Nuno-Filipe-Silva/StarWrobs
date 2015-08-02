package com.guillaume.starwrobs.data.database.brite;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.Db;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonColumns;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.People;
import com.guillaume.starwrobs.fragments.SWListFragment;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

import static com.squareup.sqlbrite.SqlBrite.Query;

@AutoParcel
public abstract class PeopleBrite {

    public static final String QUERY = ""
            + "SELECT *"
            + " FROM " + SWDatabaseContract.Tables.PEOPLE
            + " ORDER BY " + People.PEOPLE_NAME + " ASC";

    public static final String QUERY_GET_PEOPLE_FROM_ID = "SELECT * FROM "
            + SWDatabaseContract.Tables.PEOPLE
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    public static final Func1<Query, PeopleBrite> MAP = new Func1<Query, PeopleBrite>() {
        @Override
        public PeopleBrite call(Query query) {
            Cursor cursor = query.run();
            try {
                if (cursor.moveToFirst()) {
                    long id = Db.getLong(cursor, BaseColumns._ID);
                    int objectId = Db.getInt(cursor, CommonColumns.COMMON_ID);
                    String created = Db.getString(cursor, CommonColumns.COMMON_CREATED);
                    String edited = Db.getString(cursor, CommonColumns.COMMON_EDITED);

                    String name = Db.getString(cursor, People.PEOPLE_NAME);
                    String height = Db.getString(cursor, People.PEOPLE_HEIGHT);
                    String mass = Db.getString(cursor, People.PEOPLE_MASS);
                    String hairColor = Db.getString(cursor, People.PEOPLE_HAIR_COLOR);
                    String skinColor = Db.getString(cursor, People.PEOPLE_SKIN_COLOR);
                    String eyeColor = Db.getString(cursor, People.PEOPLE_EYE_COLOR);
                    String birthYear = Db.getString(cursor, People.PEOPLE_BIRTH_YEAR);
                    String gender = Db.getString(cursor, People.PEOPLE_GENDER);
                    int homeworld = Db.getInt(cursor, People.PEOPLE_HOMEWORLD);

                    return new AutoParcel_PeopleBrite(id, objectId, created, edited, name, height, mass, hairColor, skinColor, eyeColor, birthYear, gender, homeworld);
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
                    String name = Db.getString(cursor, People.PEOPLE_NAME);
                    values.add(new SimpleGenericObjectForRecyclerview(objectId, SWListFragment.KEY_PEOPLE, name));
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

    public abstract String height();

    public abstract String mass();

    public abstract String hairColor();

    public abstract String skinColor();

    public abstract String eyeColor();

    public abstract String birthYear();

    public abstract String gender();

    public abstract int homeworld();

    public static final class Builder extends BaseBuilder {

        public Builder name(String name) {
            values.put(People.PEOPLE_NAME, name);
            return this;
        }

        public Builder height(String height) {
            values.put(People.PEOPLE_HEIGHT, height);
            return this;
        }

        public Builder mass(String mass) {
            values.put(People.PEOPLE_MASS, mass);
            return this;
        }

        public Builder hairColor(String hairColor) {
            values.put(People.PEOPLE_HAIR_COLOR, hairColor);
            return this;
        }

        public Builder skinColor(String skinColor) {
            values.put(People.PEOPLE_SKIN_COLOR, skinColor);
            return this;
        }

        public Builder eyeColor(String eyeColor) {
            values.put(People.PEOPLE_EYE_COLOR, eyeColor);
            return this;
        }

        public Builder birthYear(String birthYear) {
            values.put(People.PEOPLE_BIRTH_YEAR, birthYear);
            return this;
        }

        public Builder gender(String gender) {
            values.put(People.PEOPLE_GENDER, gender);
            return this;
        }

        public Builder homeworld(String homeworld) {
            values.put(People.PEOPLE_HOMEWORLD, homeworld);
            return this;
        }
    }
}