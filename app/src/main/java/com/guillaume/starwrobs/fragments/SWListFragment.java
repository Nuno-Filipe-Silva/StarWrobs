package com.guillaume.starwrobs.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class SWListFragment extends BaseFragment {

    public static final String ARG_CATEGORY_ID = "arg_category";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private int mCategoryId = -1;
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
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);

        setupRecyclerView(mRecyclerView);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        ArrayList<String> test = new ArrayList<>(3);
        test.add(0, "salut");
        test.add(1, "comment");
        test.add(2, "tu vas");


        recyclerView.setAdapter(new BaseRecyclerViewAdapter(getActivity(), test));
    }
}
