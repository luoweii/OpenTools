package com.luowei.opentools.module.androiduniversalimageloader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/28.
 */
public class ComplexImageFragment extends BaseFragment {
    private static final String STATE_POSITION = "STATE_POSITION";
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    public int getLayout() {
        return R.layout.uil_fragment_complex;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int pagerPosition = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_POSITION);
        pager.setAdapter(new ImagePagerAdapter(getChildFragmentManager()));
        pager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentPagerAdapter {
        ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ImageListFragment();
                case 1:
                    return  new ImageGridFragment();
                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_list);
                case 1:
                    return getString(R.string.title_grid);
                default:
                    return null;
            }
        }
    }
}
