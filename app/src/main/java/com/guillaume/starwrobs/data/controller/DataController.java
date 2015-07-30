package com.guillaume.starwrobs.data.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.network.ApiManager;
import com.guillaume.starwrobs.data.network.model.People;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.util.Functions;


import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class DataController {

    @Inject
    SQLiteDatabase mDatabase;

    @Inject
    ApiManager mApiManager;

    public DataController(Context context) {
        SWApplication.get(context).appComponent().inject(this);
    }

    public void refreshData() {

        mDatabase.beginTransaction();

        try {

            deleteDatabase();



            mApiManager.getListPeoplePageNumber(1)
                    .concatMap(new Func1<ResultPeople, Observable<People>>() {

                        @Override
                        public Observable<People> call(ResultPeople response) {
                            return Observable.from(response.results);
                        }

                    })
                    .subscribe(new Action1<People>() {

                        @Override
                        public void call(People people) {
                            Log.d("result", "name = " + people.name);
                            insertPeople(people);
                        }
                    });
            mDatabase.setTransactionSuccessful();

        } catch(Exception e) {
            //Error in between database transaction
        } finally{
            mDatabase.endTransaction();
        }
    }

    private void deleteDatabase() {
        mDatabase.delete(SWDatabaseContract.Tables.PEOPLE, null, null);
        mDatabase.delete(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, null, null);
    }


    private void insertPeople(People people) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(SWDatabaseContract.CommonColumns.COMMON_ID, people.url);
        values.put(SWDatabaseContract.CommonColumns.COMMON_CREATED, people.created);
        values.put(SWDatabaseContract.CommonColumns.COMMON_EDITED, people.edited);
        values.put(SWDatabaseContract.People.PEOPLE_NAME, people.name);
        values.put(SWDatabaseContract.People.PEOPLE_HEIGHT, people.height);
        values.put(SWDatabaseContract.People.PEOPLE_MASS, people.mass);
        values.put(SWDatabaseContract.People.PEOPLE_HAIR_COLOR, people.hair_color);
        values.put(SWDatabaseContract.People.PEOPLE_SKIN_COLOR, people.skin_color);
        values.put(SWDatabaseContract.People.PEOPLE_EYE_COLOR, people.eye_color);
        values.put(SWDatabaseContract.People.PEOPLE_BIRTH_YEAR, people.birth_year);
        values.put(SWDatabaseContract.People.PEOPLE_GENDER, people.gender);
        values.put(SWDatabaseContract.People.PEOPLE_HOMEWORLD, people.homeworld);
        mDatabase.insert(SWDatabaseContract.Tables.PEOPLE, null, values);
        

        int nbOfFilms = people.films.size();
        for(int i=0; i<nbOfFilms; i++) {
            values.clear();
            values.put(SWDatabaseContract.LinkPeopleFilms.PEOPLE_ID, people.url);
            values.put(SWDatabaseContract.LinkPeopleFilms.FILM_ID, people.films.get(i));
            mDatabase.insert(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, null, values);
        }
    }
}
