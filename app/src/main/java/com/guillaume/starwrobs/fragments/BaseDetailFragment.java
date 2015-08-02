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
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.data.database.brite.SpeciesBrite;
import com.guillaume.starwrobs.data.database.brite.StarshipsBrite;
import com.guillaume.starwrobs.data.database.brite.VehiclesBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

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
    }

    @Override public void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();
    }


    @Override public void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }


    protected void addPeopleForId(final int id, final LinearLayout mLinearCharacters) {
        subscriptions.add(db.createQuery(Tables.PEOPLE, PeopleBrite.QUERY_GET_PEOPLE_FROM_ID, String.valueOf(id))
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

    protected void addFilmsForId(final int id, final LinearLayout mLinearFilms) {
        subscriptions.add(db.createQuery(Tables.PEOPLE, PeopleBrite.QUERY_GET_PEOPLE_FROM_ID, String.valueOf(id))
                .map(PeopleBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PeopleBrite>() {
                    @Override
                    public void onNext(PeopleBrite people) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearFilms, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(people.name());
                        mLinearFilms.addView(child);
                    }
                }));
    }

    protected void addSpeciesForId(final int id, final LinearLayout mLinearSpecies) {
        subscriptions.add(db.createQuery(Tables.SPECIES, SpeciesBrite.QUERY_SPECIES_FROM_ID, String.valueOf(id))
                .map(SpeciesBrite.MAP_SPECIESBRITE_UNIQUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<SpeciesBrite>() {
                    @Override
                    public void onNext(SpeciesBrite species) {
                        View child = inflater.inflate(R.layout.detail_list_item, mLinearSpecies, false);
                        ((TextView) child.findViewById(R.id.item_name)).setText(species.name());
                        mLinearSpecies.addView(child);
                    }
                }));
    }

    protected void addStarshipsForId(final int id, final LinearLayout mLinearStarships) {
        subscriptions.add(db.createQuery(Tables.STARSHIPS, StarshipsBrite.QUERY_STARSHIPS_FROM_ID, String.valueOf(id))
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

    protected void addVehiclesForId(final int id, final LinearLayout mLinearVehicles) {
        subscriptions.add(db.createQuery(Tables.VEHICLES, VehiclesBrite.QUERY_VEHICLES_FROM_ID, String.valueOf(id))
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

}
