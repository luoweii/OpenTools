/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.luowei.opentools.module.androiduniversalimageloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;
import com.luowei.opentools.module.photoview.ViewPagerFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import butterknife.Bind;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class AbsListViewBaseFragment extends BaseFragment {
    @Bind(R.id.listView)
    AbsListView listView;

    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        applyScrollListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem pauseOnScrollItem = menu.findItem(R.id.item_pause_on_scroll);
        pauseOnScrollItem.setVisible(true);
        pauseOnScrollItem.setChecked(pauseOnScroll);

        MenuItem pauseOnFlingItem = menu.findItem(R.id.item_pause_on_fling);
        pauseOnFlingItem.setVisible(true);
        pauseOnFlingItem.setChecked(pauseOnFling);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_pause_on_scroll:
                pauseOnScroll = !pauseOnScroll;
                item.setChecked(pauseOnScroll);
                applyScrollListener();
                break;
            case R.id.item_pause_on_fling:
                pauseOnFling = !pauseOnFling;
                item.setChecked(pauseOnFling);
                applyScrollListener();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void startImagePagerFragment(int position) {
        Fragment f = new ImagePagerFragment();
        Bundle b = new Bundle();
        b.putInt(Constants.Extra.IMAGE_POSITION, position);
        f.setArguments(b);
        Fragment fragment = this;
        if (getParentFragment() instanceof ComplexImageFragment) {
            fragment = getParentFragment();
        }
        fragment.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .add(android.R.id.content, f)
                .addToBackStack(null)
                .commit();
    }

    private void applyScrollListener() {
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
    }

}
