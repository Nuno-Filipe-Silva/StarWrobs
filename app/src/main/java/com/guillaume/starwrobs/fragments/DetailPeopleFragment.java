package com.guillaume.starwrobs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.adapter.ReactiveGenericRecyclerViewAdapter;
import com.guillaume.starwrobs.adapter.SimpleStringRecyclerViewAdapter;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.FilmsBrite;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.data.database.brite.PlanetsBrite;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.data.database.brite.SimpleGenericObjectForRecyclerview;
import com.guillaume.starwrobs.data.database.brite.SpeciesBrite;
import com.guillaume.starwrobs.data.database.brite.StarshipsBrite;
import com.guillaume.starwrobs.data.database.brite.VehiclesBrite;
import com.guillaume.starwrobs.data.network.model.Species;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailCardLayout;
import com.guillaume.starwrobs.widget.DetailInfoLayout;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DetailPeopleFragment extends BaseFragment {

    private static final String KEY_ID = "id";
    private static final String TAG_UNKNOWN = "unknown";
    private CompositeSubscription subscriptions;
    private int peopleId;
    private LayoutInflater inflater;

    @Inject
    BriteDatabase db;

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

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        SWApplication.get(getActivity()).appComponent().inject(this);
        peopleId = getArguments().getInt(KEY_ID);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override public void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();

        subscriptions.add(db.createQuery(Tables.PEOPLE, QUERY, String.valueOf(peopleId))
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


        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, QUERY_CAST, String.valueOf(peopleId))
                .map(QueryLinkTables.QUERY_GET_FILMS_WHERE_PEOPLE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> listOfMovies) {
                        for (int i = 0; i < listOfMovies.size(); i++) {
                            addFilmNameForId(listOfMovies.get(i));
                        }
                    }
                }));

        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_SPECIES, QUERY_SPECIES, String.valueOf(peopleId))
                .map(QueryLinkTables.QUERY_GET_SPECIES_WHERE_PEOPLE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> species) {
                        for (int i = 0; i < species.size(); i++) {
                            addSpeciesForId(species.get(i));
                        }
                    }
                }));

        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_STARSHIPS, QUERY_STARSHIPS, String.valueOf(peopleId))
                .map(QueryLinkTables.QUERY_GET_STARSHIPS_WHERE_PEOPLE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addStarshipsForId(list.get(i));
                        }
                    }
                }));

        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_VEHICLES, QUERY_VEHICLES, String.valueOf(peopleId))
                .map(QueryLinkTables.QUERY_GET_VEHICLES_WHERE_PEOPLE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addVehiclesForId(list.get(i));
                        }
                    }
                }));
    }

    private void getHomeworld(int homeworldId) {
        subscriptions.add(db.createQuery(Tables.PLANETS, QUERY_HOMEWORLD_NAME, String.valueOf(homeworldId))
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

    private void addFilmNameForId(final int id) {
        subscriptions.add(db.createQuery(Tables.FILMS, QUERY_FILM_FROM_ID, String.valueOf(id))
                .map(FilmsBrite.MAP_FILMSBRITE_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<FilmsBrite>() {
                    @Override
                    public void onNext(FilmsBrite film) {
                        String title = film.title() + " (episode " + film.episodeId()+ ")";
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearFilm, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(title);
                        mLinearFilm.addView(child);
                    }
                }));
    }

    private void addSpeciesForId(final int id) {
        subscriptions.add(db.createQuery(Tables.SPECIES, QUERY_SPECIES_FROM_ID, String.valueOf(id))
                .map(SpeciesBrite.MAP_SPECIESBRITE_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<SpeciesBrite>() {
                    @Override
                    public void onNext(SpeciesBrite species) {
                        Timber.d("species name = " + species.name());
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearSpecies, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(species.name());
                        mLinearSpecies.addView(child);
                    }
                }));
    }

    private void addStarshipsForId(final int id) {
        subscriptions.add(db.createQuery(Tables.STARSHIPS, QUERY_STARSHIPS_FROM_ID, String.valueOf(id))
                .map(StarshipsBrite.MAP_STARSHIPSBRITE_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<StarshipsBrite>() {
                    @Override
                    public void onNext(StarshipsBrite species) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearStarships, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(species.name());
                        mLinearStarships.addView(child);
                    }
                }));
    }

    private void addVehiclesForId(final int id) {
        subscriptions.add(db.createQuery(Tables.STARSHIPS, QUERY_VEHICLES_FROM_ID, String.valueOf(id))
                .map(VehiclesBrite.MAP_VEHICLES_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<VehiclesBrite>() {
                    @Override
                    public void onNext(VehiclesBrite object) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearVehicles, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(object.name());
                        mLinearVehicles.addView(child);
                    }
                }));
    }

    @Override public void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }




    private static final String QUERY = "SELECT * FROM "
            + Tables.PEOPLE
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    private static final String QUERY_HOMEWORLD_NAME = "SELECT * FROM "
            + Tables.PLANETS
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    private static final String QUERY_CAST = "SELECT * FROM "
            + SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS
            + " WHERE "
            + SWDatabaseContract.LinkPeopleFilms.PEOPLE_ID
            + " = ?"
            + " ORDER BY "
            + SWDatabaseContract.LinkPeopleFilms.FILM_ID
            + " ASC";

    private static final String QUERY_SPECIES = "SELECT * FROM "
            + SWDatabaseContract.LinkTables.LINK_PEOPLE_SPECIES
            + " WHERE "
            + SWDatabaseContract.LinkPeopleSpecies.PEOPLE_ID
            + " = ?";

    private static final String QUERY_STARSHIPS = "SELECT * FROM "
            + SWDatabaseContract.LinkTables.LINK_PEOPLE_STARSHIPS
            + " WHERE "
            + SWDatabaseContract.LinkPeopleSpecies.PEOPLE_ID
            + " = ?";

    private static final String QUERY_VEHICLES = "SELECT * FROM "
            + SWDatabaseContract.LinkTables.LINK_PEOPLE_VEHICLES
            + " WHERE "
            + SWDatabaseContract.LinkPeopleSpecies.PEOPLE_ID
            + " = ?";

    private static final String QUERY_FILM_FROM_ID = "SELECT * FROM "
            + Tables.FILMS
            + " WHERE "
            + SWDatabaseContract.Film.FILM_EPISODE_ID
            + " = ?";



    private static final String QUERY_SPECIES_FROM_ID = "SELECT * FROM "
            + Tables.SPECIES
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    private static final String QUERY_STARSHIPS_FROM_ID = "SELECT * FROM "
            + Tables.STARSHIPS
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    private static final String QUERY_VEHICLES_FROM_ID = "SELECT * FROM "
            + Tables.VEHICLES
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";


}
