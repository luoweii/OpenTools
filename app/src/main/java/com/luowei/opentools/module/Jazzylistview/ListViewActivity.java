package com.luowei.opentools.module.jazzylistview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.luowei.jazzylistview.JazzyHelper;
import com.luowei.jazzylistview.JazzyListView;
import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 骆巍 on 2016/4/22.
 */
public class ListViewActivity extends BaseActivity {
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";

    private JazzyListView mList;
    private Map<String, Integer> mEffectMap;
    private int mCurrentTransitionEffect = JazzyHelper.HELIX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jazzy_activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("JazzyListView");
        mList = (JazzyListView) findViewById(android.R.id.list);
        mList.setAdapter(new ListAdapter(this, R.layout.jazzy_item));

        if (savedInstanceState != null) {
            mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, JazzyHelper.HELIX);
            setupJazziness(mCurrentTransitionEffect);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mEffectMap = Utils.buildEffectMap(this);
        Utils.populateEffectMenu(menu, new ArrayList<>(mEffectMap.keySet()), this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home != item.getItemId()) {
            String strEffect = item.getTitle().toString();
            Toast.makeText(this, strEffect, Toast.LENGTH_SHORT).show();
            setupJazziness(mEffectMap.get(strEffect));
            setTitle(strEffect);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TRANSITION_EFFECT, mCurrentTransitionEffect);
    }

    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        mList.setTransitionEffect(mCurrentTransitionEffect);
    }
}
