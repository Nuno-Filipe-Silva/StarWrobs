package com.guillaume.starwrobs.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.guillaume.starwrobs.R;

public class LicensesActivity extends BaseActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set title
        setTitle(R.string.ui_licenses);

        // Display home as up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((WebView) findViewById(R.id.web_view)).loadUrl("file:///android_asset/licenses");
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

    @Override
    protected int getLayoutResource() {
        return R.layout.licenses_activity;
    }

}