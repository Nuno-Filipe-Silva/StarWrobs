package com.guillaume.starwrobs.fragments;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.FilmsBrite;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.data.database.brite.PlanetsBrite;
import com.guillaume.starwrobs.data.database.brite.SimpleGenericObjectForRecyclerview;
import com.guillaume.starwrobs.data.database.brite.SpeciesBrite;
import com.guillaume.starwrobs.data.database.brite.StarshipsBrite;
import com.guillaume.starwrobs.data.database.brite.VehiclesBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseDetailFragment extends BaseFragment {

    @Inject
    BriteDatabase db;

    protected static final String KEY_ID = "id";
    protected static final String TAG_UNKNOWN = "unknown";
    protected CompositeSubscription subscriptions;
    protected int objectId;
    protected LayoutInflater inflater;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        SWApplication.get(getActivity()).appComponent().inject(this);
        objectId = getArguments().getInt(KEY_ID);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        subscriptions = new CompositeSubscription();
    }


    @Override public void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }


    protected void addPeopleForId(final int id, final LinearLayout mLinear) {
        subscriptions.add(db.createQuery(Tables.PEOPLE, PeopleBrite.QUERY_GET_PEOPLE_FROM_ID, String.valueOf(id))
                .map(PeopleBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PeopleBrite>() {
                    @Override
                    public void onNext(PeopleBrite people) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinear, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(people.name());
                        mLinear.addView(child);
                    }
                }));
    }

    protected void addFilmsForId(final int id, final LinearLayout mLinear) {
        subscriptions.add(db.createQuery(Tables.FILMS, FilmsBrite.QUERY_FILM_FROM_ID, String.valueOf(id))
                .map(FilmsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<FilmsBrite>() {
                    @Override
                    public void onNext(FilmsBrite film) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinear, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(film.title());
                        mLinear.addView(child);
                    }
                }));
    }

    protected void addPlanetsForId(final int id, final LinearLayout mLinearPlanets) {
        subscriptions.add(db.createQuery(Tables.PLANETS, PlanetsBrite.QUERY_PLANET_FROM_ID, String.valueOf(id))
                .map(PlanetsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PlanetsBrite>() {
                    @Override
                    public void onNext(PlanetsBrite planet) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearPlanets, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(planet.name());
                        mLinearPlanets.addView(child);
                    }
                }));
    }

    protected void addSpeciesForId(final int id, final LinearLayout mLinear) {
        subscriptions.add(db.createQuery(Tables.SPECIES, SpeciesBrite.QUERY_SPECIES_FROM_ID, String.valueOf(id))
                .map(SpeciesBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<SpeciesBrite>() {
                    @Override
                    public void onNext(SpeciesBrite object) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinear, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(object.name());
                        mLinear.addView(child);
                    }
                }));
    }

    protected void addStarshipsForId(final int id, final LinearLayout mLinear) {
        subscriptions.add(db.createQuery(Tables.STARSHIPS, StarshipsBrite.QUERY_STARSHIPS_FROM_ID, String.valueOf(id))
                .map(StarshipsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<StarshipsBrite>() {
                    @Override
                    public void onNext(StarshipsBrite object) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinear, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(object.name());
                        mLinear.addView(child);
                    }
                }));
    }

    protected void addVehiclesForId(final int id, final LinearLayout mLinear) {
        subscriptions.add(db.createQuery(Tables.VEHICLES, VehiclesBrite.QUERY_VEHICLES_FROM_ID, String.valueOf(id))
                .map(VehiclesBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<VehiclesBrite>() {
                    @Override
                    public void onNext(VehiclesBrite object) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinear, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(object.name());
                        mLinear.addView(child);
                    }
                }));
    }

    protected void getHomeworld(final int homeworldId, final DetailInfoLayout mHomeworld) {
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

    protected void setEmptyDescription(final LinearLayout ll) {
        View child = inflater.inflate(R.layout.detail_list_item, ll, false);
        ((TextView) child.findViewById(R.id.item_name)).setText(getString(R.string.ui_card_empty_description));
        ll.addView(child);
    }

}
