package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.data.database.brite.PlanetsBrite;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.data.database.brite.SimpleGenericObjectForRecyclerview;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DetailPeopleFragment extends BaseDetailFragment {

    @Bind(R.id.layout_info_name)
    DetailInfoLayout mName;
    @Bind(R.id.layout_info_height)
    DetailInfoLayout mHeight;
    @Bind(R.id.layout_info_mass)
    DetailInfoLayout mMass;
    @Bind(R.id.layout_info_eye_color)
    DetailInfoLayout mEyeColor;
    @Bind(R.id.layout_info_skin_color)
    DetailInfoLayout mSkinColor;
    @Bind(R.id.layout_info_hair_color)
    DetailInfoLayout mHairColor;
    @Bind(R.id.layout_info_gender)
    DetailInfoLayout mGender;
    @Bind(R.id.layout_info_homeworld)
    DetailInfoLayout mHomeworld;

    @Bind(R.id.cardFilms)
    LinearLayout mLinearFilm;

    @Bind(R.id.cardSpecies)
    LinearLayout mLinearSpecies;

    @Bind(R.id.cardStarships)
    LinearLayout mLinearStarships;

    @Bind(R.id.cardVehicles)
    LinearLayout mLinearVehicles;


    public static DetailPeopleFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailPeopleFragment fragment = new DetailPeopleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.detail_people;
    }

    @Override public void onResume() {
        super.onResume();

        subscriptions.add(db.createQuery(Tables.PEOPLE, PeopleBrite.QUERY_GET_PEOPLE_FROM_ID, String.valueOf(objectId))
                .map(PeopleBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PeopleBrite>() {
                    @Override
                    public void onNext(PeopleBrite people) {
                        mName.setContentText(people.name());
                        String value = people.mass();
                        if (!value.equals(TAG_UNKNOWN))
                            value = value + " kg";
                        mMass.setContentText(value);
                        value = people.height();
                        if (!value.equals(TAG_UNKNOWN))
                            value = value + " cm";
                        mHeight.setContentText(value);
                        mEyeColor.setContentText(people.eyeColor());
                        mHairColor.setContentText(people.hairColor());
                        mSkinColor.setContentText(people.skinColor());
                        mGender.setContentText(people.gender());
                        getHomeworld(people.homeworld());
                    }
                }));


        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_FILMS, QueryLinkTables.QUERY_LINK_PEOPLE_FILMS_GET_FILMS_FOR_PEOPLE_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_FILMS_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> listOfMovies) {
                        for (int i = 0; i < listOfMovies.size(); i++) {
                            addFilmsForId(listOfMovies.get(i), mLinearFilm);
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_SPECIES, QueryLinkTables.QUERY_LINK_PEOPLE_SPECIES_GET_SPECIES_FOR_PEOPLE_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_SPECIES_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> species) {
                        for (int i = 0; i < species.size(); i++) {
                            addSpeciesForId(species.get(i), mLinearSpecies);
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_STARSHIPS, QueryLinkTables.QUERY_LINK_PEOPLE_STARSHIPS_GET_STARSHIPS_FOR_PEOPLE_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_STARSHIPS_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addStarshipsForId(list.get(i), mLinearStarships);
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_VEHICLES, QueryLinkTables.QUERY_LINK_PEOPLE_VEHICLES_GET_VEHICLES_FOR_PEOPLE_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_VEHICLES_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addVehiclesForId(list.get(i), mLinearVehicles);
                        }
                    }
                }));
    }

    private void getHomeworld(int homeworldId) {
        subscriptions.add(db.createQuery(Tables.PLANETS, PlanetsBrite.QUERY_PLANET_FROM_ID, String.valueOf(homeworldId))
                .map(PlanetsBrite.MAP_STRING_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<SimpleGenericObjectForRecyclerview>() {
                    @Override
                    public void onNext(SimpleGenericObjectForRecyclerview planet) {
                        mHomeworld.setContentText(planet.name);
                    }
                }));
    }
}
