package com.luowei.opentools.module.mpandroidchart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.mikephil.charting.utils.ViewPortHandler;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/5/4.
 */
public class SimpleChartFragment extends BaseFragment {
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    public int getLayout() {
        return R.layout.mpa_fragment_awesomedesign;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pager.setOffscreenPageLimit(3);

        PageAdapter a = new PageAdapter(getChildFragmentManager());
        pager.setAdapter(a);

        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("This is a ViewPager.");
        b.setMessage("Swipe left and right for more awesome design examples!");
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.show();
    }

    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch (pos) {
                case 0:
                    f = SineCosineFragment.newInstance();
                    break;
                case 1:
                    f = ComplexityFragment.newInstance();
                    break;
                case 2:
                    f = BarChartFrag.newInstance();
                    break;
                case 3:
                    f = ScatterChartFrag.newInstance();
                    break;
                case 4:
                    f = PieChartFrag.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
