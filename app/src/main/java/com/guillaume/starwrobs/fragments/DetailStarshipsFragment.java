package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.data.database.brite.StarshipsBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailStarshipsFragment extends BaseDetailFragment {

    @Bind(R.id.layout_info_starships_name)
    DetailInfoLayout mName;
    @Bind(R.id.layout_info_starships_model)
    DetailInfoLayout mModel;
    @Bind(R.id.layout_info_starships_manufacturer)
    DetailInfoLayout mManufacturer;
    @Bind(R.id.layout_info_starships_cost_in_credits)
    DetailInfoLayout mCost;
    @Bind(R.id.layout_info_starships_length)
    DetailInfoLayout mLength;
    @Bind(R.id.layout_info_starships_max_atmosphering_speed)
    DetailInfoLayout mMaxAtmospheringSpeed;
    @Bind(R.id.layout_info_starships_crew)
    DetailInfoLayout mCrew;
    @Bind(R.id.layout_info_starships_passengers)
    DetailInfoLayout mPassengers;
    @Bind(R.id.layout_info_starships_cargo_capacity)
    DetailInfoLayout mCargoCapacity;
    @Bind(R.id.layout_info_starships_consumables)
    DetailInfoLayout mConsumables;
    @Bind(R.id.layout_info_starships_hyperdrive_rating)
    DetailInfoLayout mHyperdriveRating;
    @Bind(R.id.layout_info_starships_MGLT)
    DetailInfoLayout mMGLT;
    @Bind(R.id.layout_info_starships_class)
    DetailInfoLayout mClass;

    @Bind(R.id.cardPilots)
    LinearLayout mLinearPilots;

    @Bind(R.id.cardFilms)
    LinearLayout mLinearFilms;

    public static DetailStarshipsFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailStarshipsFragment fragment = new DetailStarshipsFragment();
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override

    protected int getLayoutResource() {
        return R.layout.detail_starships;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscriptions.add(db.createQuery(Tables.STARSHIPS,
                StarshipsBrite.QUERY_STARSHIPS_FROM_ID, String.valueOf(objectId))
                .map(StarshipsBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<StarshipsBrite>() {
                    @Override
                    public void onNext(StarshipsBrite starship) {
                        mName.setContentText(starship.name());
                        mModel.setContentText(starship.model());
                        mManufacturer.setContentText(starship.manufacturer());
                        mCost.setContentText(starship.costInCredits());
                        mLength.setContentText(starship.length());
                        mMaxAtmospheringSpeed.setContentText(starship.maxAtmospheringSpeed());
                        mCrew.setContentText(starship.crew());
                        mPassengers.setContentText(starship.passengers());
                        mCargoCapacity.setContentText(starship.cargoCapacity());
                        mConsumables.setContentText(starship.consumables());
                        mHyperdriveRating.setContentText(starship.hyperdriveRating());
                        mMGLT.setContentText(starship.MGLT());
                        mClass.setContentText(starship.objectClass());
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_STARSHIPS,
                QueryLinkTables.QUERY_LINK_PEOPLE_STARSHIPS_GET_PEOPLE_FOR_STARSHIPS_ID,
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

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_STARSHIPS,
                QueryLinkTables.QUERY_LINK_FILMS_STARSHIPS_GET_FILMS_FOR_STARSHIPS_ID,
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