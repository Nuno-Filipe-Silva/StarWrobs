package com.guillaume.starwrobs.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.data.controller.DataController;
import com.guillaume.starwrobs.data.network.ApiManager;
import com.guillaume.starwrobs.data.network.model.ResultPeople;
import com.guillaume.starwrobs.fragments.DummyFragment;
import com.guillaume.starwrobs.util.SimpleObserver;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {

    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        // Proper RxJava subscriptions management with CompositeSubscription
        subscriptions = new CompositeSubscription();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                DataController.refreshData();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_people));
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_films));
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_planets));
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_species));
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_starships));
        adapter.addFragment(new DummyFragment(), getString(R.string.ui_tab_vehicles));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    protected void subscribe(Subscription s) {
        subscriptions.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        subscriptions.unsubscribe();
    }
}
