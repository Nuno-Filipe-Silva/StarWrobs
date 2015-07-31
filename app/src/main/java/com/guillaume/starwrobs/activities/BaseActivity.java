package com.guillaume.starwrobs.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guillaume.starwrobs.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutResource() > 0) {
            setContentView(getLayoutResource());

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }

        // Proper RxJava subscriptions management with CompositeSubscription
        subscriptions = new CompositeSubscription();
    }

    protected void subscribe(Subscription s) {
        subscriptions.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        subscriptions.unsubscribe();
    }

    protected abstract int getLayoutResource();

}