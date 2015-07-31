package com.guillaume.starwrobs.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.SWApplication;
import com.guillaume.starwrobs.adapter.BaseRecyclerViewAdapter;
import com.guillaume.starwrobs.data.database.SWDatabaseContract;
import com.guillaume.starwrobs.data.database.brite.PeopleBrite;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SWListFragment extends BaseFragment {

    public interface Listener {
        void onListClicked(long id);
        void onNewListClicked();
    }

    public static final String ARG_CATEGORY_ID = "arg_category";

    private int mCategoryId = -1;
    private Listener listener;
    private Subscription subscription;
    private BaseRecyclerViewAdapter adapter;


    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    BriteDatabase db;

    /**
     * Empty constructor as per the fragment documentation
     */
    public SWListFragment() {
    }

    public static SWListFragment newInstance(int category) {
        SWListFragment fragment = new SWListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_CATEGORY_ID, category);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof Listener)) {
            throw new IllegalStateException("Activity must implement fragment Listener.");
        }
        super.onAttach(activity);

        SWApplication.get(activity).appComponent().inject(this);

        listener = (Listener) activity;
        adapter = new BaseRecyclerViewAdapter(activity);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override public void onResume() {
        super.onResume();

        subscription = db.createQuery(SWDatabaseContract.Tables.PEOPLE, PeopleBrite.QUERY)
                .map(PeopleBrite.MAP_STRING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
