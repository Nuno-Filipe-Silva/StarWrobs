package com.guillaume.starwrobs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes = getLayoutResource();
        if (layoutRes == 0) {
            throw new IllegalArgumentException(
                    "getLayoutResource() returned 0, which is not allowed. "
                            + "If you don't want to use getLayoutResource(), then override onCreateView();");
        } else {
            return inflater.inflate(layoutRes, container, false);
        }
    }

    /**
     * Return the layout resource like R.layout.fragment
     *
     * @return the layout resource or null, if you don't want to have a UI
     */
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
