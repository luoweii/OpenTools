package com.github.paolorotolo.appintroexample.animations;

import android.view.Menu;
import android.view.MenuItem;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintroexample.R;

/**
 * Created by julio on 20/10/15.
 */
public abstract class BaseAppIntro extends AppIntro {
    private int mScrollDurationFactor = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_intro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_factor1) {
            mScrollDurationFactor = 1;

        } else if (i == R.id.action_factor2) {
            mScrollDurationFactor = 2;

        } else if (i == R.id.action_factor4) {
            mScrollDurationFactor = 4;

        } else if (i == R.id.action_factor6) {
            mScrollDurationFactor = 6;

        }
        setScrollDurationFactor(mScrollDurationFactor);
        return super.onOptionsItemSelected(item);
    }
}
