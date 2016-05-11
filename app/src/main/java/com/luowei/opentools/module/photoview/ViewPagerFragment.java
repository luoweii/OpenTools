package com.luowei.opentools.module.photoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 骆巍 on 2016/5/10.
 */
public class ViewPagerFragment extends BaseFragment {
    private ViewPager mViewPager;

    @Override
    public int getLayout() {
        return R.layout.photoview_fr_view_pager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager = (HackyViewPager) rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    static class SamplePagerAdapter extends PagerAdapter {

        private static final int[] sDrawables = { R.drawable.photoview_wallpaper, R.drawable.photoview_bizhi, R.drawable.photoview_wallpaper,
                R.drawable.photoview_bizhi, R.drawable.photoview_bizhi, R.drawable.photoview_wallpaper };

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
