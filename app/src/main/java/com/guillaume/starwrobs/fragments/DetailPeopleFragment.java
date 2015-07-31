package com.guillaume.starwrobs.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DetailPeopleFragment extends BaseFragment {

    private static final String KEY_ID = "id";
    private CompositeSubscription subscriptions;

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
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override public void onResume() {
        super.onResume();

        subscriptions = new CompositeSubscription();

        subscriptions.add(db.createQuery(Tables.PEOPLE, QUERY, String.valueOf(getArguments().getInt(KEY_ID)))
                .map(PeopleBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<PeopleBrite>() {
                    @Override
                    public void onNext(PeopleBrite people) {
                        mName.setContentText(people.name());
                        mMass.setContentText(people.mass() + " kg");
                        mHeight.setContentText(people.height() + " cm");
                        mEyeColor.setContentText(people.eyeColor());
                        mHairColor.setContentText(people.hairColor());
                        mSkinColor.setContentText(people.skinColor());
                        mGender.setContentText(people.gender());
                        mHomeworld.setContentText(people.homeworld());
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


}
