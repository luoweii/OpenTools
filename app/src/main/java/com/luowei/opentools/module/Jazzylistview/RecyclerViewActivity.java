/*
 * Copyright (C) 2015 Two Toasters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.luowei.opentools.module.jazzylistview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.luowei.jazzylistview.JazzyHelper;
import com.luowei.jazzylistview.JazzyRecyclerViewScrollListener;
import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.ResUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewActivity extends BaseActivity {

    private static final String KEY_TRANSITION_EFFECT = "transition_effect";

    private Map<String, Integer> mEffectMap;
    private int mCurrentTransitionEffect = JazzyHelper.HELIX;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jazzy_activity_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        setTitle(t.name);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        try {
            JSONArray ja = new JSONArray(t.extra);
            recyclerView.setLayoutManager(createLayoutManager(ResUtil.getId("layout", ja.getString(0)), ja.getBoolean(1)));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new SampleRecyclerViewAdapter(ListModel.getModel(),
                    ResUtil.getId("layout", ja.getString(0)), ja.getBoolean(1)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        recyclerView.setOnScrollListener(jazzyScrollListener);

        if (savedInstanceState != null) {
            mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, JazzyHelper.HELIX);
            setupJazziness(mCurrentTransitionEffect);
        }
    }

    private LayoutManager createLayoutManager(int itemLayoutRes, boolean isStaggered) {
        if (itemLayoutRes == R.layout.jazzy_item) {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        } else {
            if (isStaggered) {
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                return new GridLayoutManager(this, 2);
            }
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
        if (item.getItemId() != android.R.id.home) {
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
        jazzyScrollListener.setTransitionEffect(mCurrentTransitionEffect);
    }
}
