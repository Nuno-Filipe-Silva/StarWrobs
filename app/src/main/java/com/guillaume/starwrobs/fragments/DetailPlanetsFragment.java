package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.FilmsBrite;
import com.guillaume.starwrobs.data.database.brite.PlanetsBrite;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPlanetsFragment extends BaseDetailFragment {


    @Bind(R.id.layout_info_planets_climate)
    DetailInfoLayout mClimate;
    @Bind(R.id.layout_info_planets_diameter)
    DetailInfoLayout mDiameter;
    @Bind(R.id.layout_info_planets_gravity)
    DetailInfoLayout mGravity;
    @Bind(R.id.layout_info_planets_name)
    DetailInfoLayout mName;
    @Bind(R.id.layout_info_planets_orbital_period)
    DetailInfoLayout mOrbital;
    @Bind(R.id.layout_info_planets_rotation_period)
    DetailInfoLayout mRotation;
    @Bind(R.id.layout_info_planets_population)
    DetailInfoLayout mPopulation;
    @Bind(R.id.layout_info_planets_surface_water)
    DetailInfoLayout mSurfaceWater;
    @Bind(R.id.layout_info_planets_terrain)
    DetailInfoLayout mTerrain;


    @Bind(R.id.cardResidents)
    LinearLayout mLinearResidents;

    @Bind(R.id.cardFilms)
    LinearLayout mLinearFilms;


    public static DetailPlanetsFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailPlanetsFragment fragment = new DetailPlanetsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.detail_planets;
    }


    @Override
    public void onResume() {
        super.onResume();

        subscriptions.add(db.createQuery(Tables.PLANETS, PlanetsBrite.QUERY_PLANET_FROM_ID, String.valueOf(objectId))
                .map(PlanetsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PlanetsBrite>() {

                    @Override
                    public void onNext(PlanetsBrite planet) {
                        mName.setContentText(planet.name());
                        mRotation.setContentText(planet.rotationPeriod());
                        mOrbital.setContentText(planet.orbitalPeriod());
                        mDiameter.setContentText(planet.diameter());
                        mClimate.setContentText(planet.climate());
                        mGravity.setContentText(planet.gravity());
                        mTerrain.setContentText(planet.terrain());
                        mSurfaceWater.setContentText(planet.surfaceWater());
                        mPopulation.setContentText(planet.population());
                    }
                }));


        subscriptions.add(db.createQuery(LinkTables.LINK_PLANETS_PEOPLE, QueryLinkTables.QUERY_LINK_PLANETS_PEOPLE_GET_PEOPLE_FOR_PLANET_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_PEOPLE_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addPeopleForId(list.get(i), mLinearResidents);
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_PLANETS, QueryLinkTables.QUERY_LINK_FILMS_PLANETS_GET_FILMS_FOR_PLANET_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_FILMS_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addFilmsForId(list.get(i), mLinearFilms);
                        }
                    }
                }));

    }


}
