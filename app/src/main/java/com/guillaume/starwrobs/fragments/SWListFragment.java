package com.guillaume.starwrobs.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.adapter.BaseRecyclerViewAdapter;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.brite.FilmsBrite;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.data.database.brite.PlanetsBrite;
import com.guillaume.starwrobs.data.database.brite.SimpleGenericObjectForRecyclerview;
import com.guillaume.starwrobs.data.database.brite.SpeciesBrite;
import com.guillaume.starwrobs.data.database.brite.StarshipsBrite;
import com.guillaume.starwrobs.data.database.brite.VehiclesBrite;
import com.guillaume.starwrobs.data.network.model.Vehicle;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SWListFragment extends BaseFragment {

    public static final String ARG_CATEGORY_ID = "arg_category";
    public static final int KEY_PEOPLE = 1;
    public static final int KEY_FILMS = 2;
    public static final int KEY_PLANETS = 3;
    public static final int KEY_SPECIES = 4;
    public static final int KEY_STARSHIPS = 5;
    public static final int KEY_VEHICLES = 6;

    private int mCategoryId = -1;
    private Subscription subscription;
    private BaseRecyclerViewAdapter adapter;


    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    BriteDatabase db;

    /**
     * Empty constructor as per the fragment documentation
     */
    public SWListFragment() {
    }

    public static SWListFragment newInstance(int category) {
        SWListFragment fragment = new SWListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_CATEGORY_ID, category);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        SWApplication.get(activity).appComponent().inject(this);

        mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        adapter = new BaseRecyclerViewAdapter(activity);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(adapter);
    }


    @Override public void onResume() {
        super.onResume();

        String table = "";
        String query = "";
        Func1<SqlBrite.Query, List<SimpleGenericObjectForRecyclerview>> map = null;

        switch (mCategoryId) {
            case KEY_PEOPLE:
                table = SWDatabaseContract.Tables.PEOPLE;
                query = PeopleBrite.QUERY;
                map = PeopleBrite.MAP_STRING;
                break;
            case KEY_FILMS:
                table = SWDatabaseContract.Tables.FILMS;
                query = FilmsBrite.QUERY;
                map = FilmsBrite.MAP_STRING;
                break;
            case KEY_PLANETS:
                table = SWDatabaseContract.Tables.PLANETS;
                query = PlanetsBrite.QUERY;
                map = PlanetsBrite.MAP_STRING;
                break;
            case KEY_SPECIES:
                table = SWDatabaseContract.Tables.SPECIES;
                query = SpeciesBrite.QUERY;
                map = SpeciesBrite.MAP_STRING;
                break;
            case KEY_STARSHIPS:
                table = SWDatabaseContract.Tables.STARSHIPS;
                query = StarshipsBrite.QUERY;
                map = StarshipsBrite.MAP_STRING;
                break;
            case KEY_VEHICLES:
                table = SWDatabaseContract.Tables.VEHICLES;
                query = VehiclesBrite.QUERY;
                map = VehiclesBrite.MAP_STRING;
                break;
        }



        subscription = db.createQuery(table, query)
                .map(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
