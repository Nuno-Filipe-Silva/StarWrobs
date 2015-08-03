package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.LinkTables;
import com.guillaume.starwrobs.data.database.SWDatabaseContract.Tables;
import com.guillaume.starwrobs.data.database.brite.QueryLinkTables;
import com.guillaume.starwrobs.data.database.brite.SpeciesBrite;
import com.guillaume.starwrobs.util.SimpleObserver;
import com.guillaume.starwrobs.widget.DetailInfoLayout;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailSpeciesFragment extends BaseDetailFragment {

    @Bind(R.id.layout_info_species_name)
    DetailInfoLayout mName;
    @Bind(R.id.layout_info_species_classification)
    DetailInfoLayout mClassification;
    @Bind(R.id.layout_info_species_designation)
    DetailInfoLayout mDesignation;
    @Bind(R.id.layout_info_species_average_height)
    DetailInfoLayout mHeight;
    @Bind(R.id.layout_info_species_skin_colors)
    DetailInfoLayout mSkinColors;
    @Bind(R.id.layout_info_species_hair_colors)
    DetailInfoLayout mHairColors;
    @Bind(R.id.layout_info_species_eye_colors)
    DetailInfoLayout mEyeColors;
    @Bind(R.id.layout_info_species_average_lifespan)
    DetailInfoLayout mLifespan;
    @Bind(R.id.layout_info_species_homeworld)
    DetailInfoLayout mHomeworld;
    @Bind(R.id.layout_info_species_language)
    DetailInfoLayout mLanguage;
    @Bind(R.id.cardPeople)
    LinearLayout mLinearPeople;
    @Bind(R.id.cardFilms)
    LinearLayout mLinearFilms;

    public static DetailSpeciesFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);

        DetailSpeciesFragment fragment = new DetailSpeciesFragment();
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override

    protected int getLayoutResource() {
        return R.layout.detail_species;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscriptions.add(db.createQuery(Tables.SPECIES, SpeciesBrite.QUERY_SPECIES_FROM_ID, String.valueOf(objectId))
                .map(SpeciesBrite.MAP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<SpeciesBrite>() {
                    @Override
                    public void onNext(SpeciesBrite species) {
                        mName.setContentText(species.name());
                        mClassification.setContentText(species.classification());
                        mDesignation.setContentText(species.designation());
                        mHeight.setContentText(species.averageHeight());
                        mSkinColors.setContentText(species.skinColors());
                        mHairColors.setContentText(species.hairColors());
                        mEyeColors.setContentText(species.eyeColors());
                        mLifespan.setContentText(species.averageLifespan());
                        mLanguage.setContentText(species.language());
                        getHomeworld(species.homeworld(), mHomeworld);
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_PEOPLE_SPECIES,
                QueryLinkTables.QUERY_LINK_PEOPLE_SPECIES_GET_PEOPLE_FOR_SPECIES_ID,
                String.valueOf(objectId))
                .map(QueryLinkTables.QUERY_GET_ALL_PEOPLE_IDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> list) {
                        int upperBound = list.size();
                        if (upperBound == 0) {
                            setEmptyDescription(mLinearPeople);
                        } else {
                            for (int i = 0; i < upperBound; i++) {
                                addPeopleForId(list.get(i), mLinearPeople);
                            }
                        }
                    }
                }));

        subscriptions.add(db.createQuery(LinkTables.LINK_FILMS_SPECIES,
                QueryLinkTables.QUERY_LINK_FILMS_SPECIES_GET_FILM_FOR_SPECIES_ID,
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