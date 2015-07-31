package com.guillaume.starwrobs.data.database.brite;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.guillaume.starwrobs.data.database.SWDatabaseContract;

public abstract class BaseBuilder {
    public final ContentValues values = new ContentValues();

    public BaseBuilder id(long id) {
        values.put(BaseColumns._ID, id);
        return this;
    }

    public BaseBuilder objectId(long objectId) {
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, objectId);
        return this;
    }

    public BaseBuilder created(String created) {
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, created);
        return this;
    }

    public BaseBuilder edited(String edited) {
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, edited);
        return this;
    }

    public ContentValues build() {
        return values; // TODO defensive copy?
    }
}
