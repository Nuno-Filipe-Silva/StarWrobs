package com.guillaume.starwrobs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
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
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DetailFilmFragment extends BaseFragment {

    private static final String KEY_ID = "id";
    private static final String TAG_UNKNOWN = "unknown";

    private CompositeSubscription subscriptions;
    private int filmId;
    private LayoutInflater inflater;

    @Inject
    BriteDatabase db;

    @Bind(R.id.layout_info_film_title)
    DetailInfoLayout mTitle;
    @Bind(R.id.layout_info_film_episode_id)
    DetailInfoLayout mEpisodeId;
    @Bind(R.id.layout_info_film_release_date)
    DetailInfoLayout mReleaseDate;
    @Bind(R.id.layout_info_film_director)
    DetailInfoLayout mDirector;
    @Bind(R.id.layout_info_film_producer)
    DetailInfoLayout mProducer;

    @Bind(R.id.openingCrawl)
    TextView mOpeningCrawl;

    @Bind(R.id.cardCharacters)
    LinearLayout mLinearCharacters;


    public static DetailFilmFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailFilmFragment fragment = new DetailFilmFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.detail_film;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        SWApplication.get(getActivity()).appComponent().inject(this);
        filmId = getArguments().getInt(KEY_ID);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();

        subscriptions.add(db.createQuery(Tables.FILMS, QUERY, String.valueOf(filmId))
                .map(FilmsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<FilmsBrite>() {

                    @Override
                    public void onNext(FilmsBrite film) {
                        mTitle.setContentText(film.title());
                        mEpisodeId.setContentText(String.valueOf(film.episodeId()));
                        mReleaseDate.setContentText(film.releaseDate());
                        mDirector.setContentText(film.director());
                        mProducer.setContentText(film.producer());
                        mOpeningCrawl.setText(film.openingCrawl());
                    }
                }));


        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS, QUERY_CAST, String.valueOf(filmId))
                .map(QueryLinkTables.QUERY_GET_PEOPLE_WHERE_FILM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addCharacterForId(list.get(i));
                        }
                    }
                }));

        /*subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_SPECIES, QUERY_SPECIES, String.valueOf(filmId))
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

        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_STARSHIPS, QUERY_STARSHIPS, String.valueOf(filmId))
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

        subscriptions.add(db.createQuery(SWDatabaseContract.LinkTables.LINK_PEOPLE_VEHICLES, QUERY_VEHICLES, String.valueOf(filmId))
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
        */
    }

    private void addCharacterForId(final int id) {
        subscriptions.add(db.createQuery(Tables.PEOPLE, QUERY_PEOPLE_FROM_ID, String.valueOf(id))
                .map(PeopleBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PeopleBrite>() {
                    @Override
                    public void onNext(PeopleBrite people) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearCharacters, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(people.name());
                        mLinearCharacters.addView(child);
                    }
                }));
    }

    /*private void addSpeciesForId(final int id) {
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
    }*/

    @Override
    public void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }


    private static final String QUERY = "SELECT * FROM "
            + Tables.FILMS
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
            + " = ?";

    private static final String QUERY_CAST = "SELECT * FROM "
            + SWDatabaseContract.LinkTables.LINK_PEOPLE_FILMS
            + " WHERE "
            + SWDatabaseContract.LinkPeopleFilms.FILM_ID
            + " = ?"
            + " ORDER BY "
            + SWDatabaseContract.LinkPeopleFilms.PEOPLE_ID
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

    private static final String QUERY_PEOPLE_FROM_ID = "SELECT * FROM "
            + Tables.PEOPLE
            + " WHERE "
            + SWDatabaseContract.CommonColumns.COMMON_ID
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
