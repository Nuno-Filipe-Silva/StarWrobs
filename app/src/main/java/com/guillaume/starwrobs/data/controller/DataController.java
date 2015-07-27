package com.guillaume.starwrobs.data.controller;


import android.util.Log;

import com.guillaume.starwrobs.data.network.ApiManager;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.util.SimpleObserver;

public class DataController {

    public static void refreshData() {

        new ApiManager().fetchPeople(1).subscribe(new SimpleObserver<ResultPeople>() {
            @Override
            public void onNext(ResultPeople list) {
                Log.d("[success]", list.next);
            }
        });

    }
}
