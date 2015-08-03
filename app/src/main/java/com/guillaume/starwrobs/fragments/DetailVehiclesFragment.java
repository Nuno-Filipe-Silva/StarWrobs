package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.data.database.brite.VehiclesBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailVehiclesFragment extends BaseDetailFragment {

    @Bind(R.id.layout_info_vehicles_name)
    DetailInfoLayout mName;
    @Bind(R.id.layout_info_vehicles_model)
    DetailInfoLayout mModel;
    @Bind(R.id.layout_info_vehicles_manufacturer)
    DetailInfoLayout mManufacturer;
    @Bind(R.id.layout_info_vehicles_cost_in_credits)
    DetailInfoLayout mCost;
    @Bind(R.id.layout_info_vehicles_length)
    DetailInfoLayout mLength;
    @Bind(R.id.layout_info_vehicles_max_atmosphering_speed)
    DetailInfoLayout mMaxAtmospheringSpeed;
    @Bind(R.id.layout_info_vehicles_crew)
    DetailInfoLayout mCrew;
    @Bind(R.id.layout_info_vehicles_passengers)
    DetailInfoLayout mPassengers;
    @Bind(R.id.layout_info_vehicles_cargo_capacity)
    DetailInfoLayout mCargoCapacity;
    @Bind(R.id.layout_info_vehicles_consumables)
    DetailInfoLayout mConsumables;
    @Bind(R.id.layout_info_vehicles_class)
    DetailInfoLayout mClass;

    @Bind(R.id.cardPilots)
    LinearLayout mLinearPilots;

    @Bind(R.id.cardFilms)
    LinearLayout mLinearFilms;

    public static DetailVehiclesFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailVehiclesFragment fragment = new DetailVehiclesFragment();
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override

    protected int getLayoutResource() {
        return R.layout.detail_vehicles;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscriptions.add(db.createQuery(Tables.VEHICLES,
                VehiclesBrite.QUERY_VEHICLES_FROM_ID, String.valueOf(objectId))
                .map(VehiclesBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<VehiclesBrite>() {
                    @Override
                    public void onNext(VehiclesBrite vehicle) {
                        mName.setContentText(vehicle.name());
                        mModel.setContentText(vehicle.model());
                        mManufacturer.setContentText(vehicle.manufacturer());
                        mCost.setContentText(vehicle.costInCredits());
                        mLength.setContentText(vehicle.length());
                        mMaxAtmospheringSpeed.setContentText(vehicle.maxAtmospheringSpeed());
                        mCrew.setContentText(vehicle.crew());
                        mPassengers.setContentText(vehicle.passengers());
                        mCargoCapacity.setContentText(vehicle.cargoCapacity());
                        mConsumables.setContentText(vehicle.consumables());
                        mClass.setContentText(vehicle.objectClass());
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_VEHICLES,
                QueryLinkTables.QUERY_LINK_PEOPLE_VEHICLES_GET_PEOPLE_FOR_VEHICLE_ID,
                String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_PEOPLE_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        int upperBound = list.size();
                        if (upperBound == 0) {
                            setEmptyDescription(mLinearPilots);
                        } else {
                            for (int i = 0; i < upperBound; i++) {
                                addPeopleForId(list.get(i), mLinearPilots);
                            }
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_VEHICLES,
                QueryLinkTables.QUERY_LINK_FILMS_VEHICLES_GET_FILM_FOR_VEHICLES_ID,
                String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_FILMS_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        int upperBound = list.size();
                        if (upperBound == 0) {
                            setEmptyDescription(mLinearFilms);
                        } else {
                            for (int i = 0; i < upperBound; i++) {
                                addFilmsForId(list.get(i), mLinearFilms);
                            }
                        }
                    }
                }));

    }

}