package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.FilmsBrite;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailFilmFragment extends BaseDetailFragment {


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

    @Bind(R.id.cardSpecies)
    LinearLayout mLinearSpecies;

    @Bind(R.id.cardStarships)
    LinearLayout mLinearStarships;

    @Bind(R.id.cardVehicles)
    LinearLayout mLinearVehicles;


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
    public void onResume() {
        super.onResume();

        subscriptions.add(db.createQuery(Tables.FILMS, FilmsBrite.QUERY_FILM_FROM_ID, String.valueOf(objectId))
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


        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_FILMS, QueryLinkTables.QUERY_LINK_PEOPLE_FILMS_GET_PEOPLE_FOR_FILM_ID, String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_PEOPLE_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        for (int i = 0; i < list.size(); i++) {
                            addPeopleForId(list.get(i), mLinearCharacters);
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_SPECIES, QueryLinkTables.QUERY_LINK_FILMS_SPECIES_GET_SPECIES_FOR_FILM_ID, String.valueOf(objectId))
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

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_STARSHIPS, QueryLinkTables.QUERY_LINK_FILMS_STARSHIPS_GET_STARSHIPS_FOR_FILM_ID, String.valueOf(objectId))
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

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_VEHICLES, QueryLinkTables.QUERY_LINK_FILMS_VEHICLES_GET_VEHICLES_FOR_FILM_ID, String.valueOf(objectId))
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


}
