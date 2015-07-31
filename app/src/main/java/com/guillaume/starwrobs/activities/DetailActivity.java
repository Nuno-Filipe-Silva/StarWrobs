package com.guillaume.starwrobs.activities;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.guillaume.starwrobs.R;
import com.guillaume.starwrobs.fragments.DetailPeopleFragment;
import com.guillaume.starwrobs.fragments.SWListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    public static final String KEY_CATEGORY = "category";
    public static final String KEY_OBJECT_ID = "id";
    public static final String KEY_NAME = "name";

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.backdrop)
    ImageView mBackdrop;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        String name;
        int categoryId;
        int id;

        if (extras != null) {
            name = extras.getString(KEY_NAME);
            categoryId = extras.getInt(KEY_CATEGORY);
            id = extras.getInt(KEY_OBJECT_ID);

            collapsingToolbarLayout.setTitle(name);
            loadBackdropAndData(categoryId, id);
        }
    }


    private void loadBackdropAndData(int categoryId, int objectId) {

        int resId = 0;
        Fragment fragment = null;

        switch (categoryId) {
            case (SWListFragment.KEY_PEOPLE):
                resId = R.drawable.people;
                fragment = DetailPeopleFragment.newInstance(objectId);
                break;
            case (SWListFragment.KEY_FILMS):
                resId = R.drawable.films;
                break;
            case (SWListFragment.KEY_PLANETS):
                resId = R.drawable.planets;
                break;
            case (SWListFragment.KEY_SPECIES):
                resId = R.drawable.species;
                break;
            case (SWListFragment.KEY_VEHICLES):
                resId = R.drawable.vehicles;
                break;
            case (SWListFragment.KEY_STARSHIPS):
                resId = R.drawable.starships;
                break;
        }

        mBackdrop.setImageResource(resId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
