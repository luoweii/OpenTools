package com.luowei.opentools.module.mpandroidchart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/5/4.
 */
public class ScrollViewFragment extends BaseFragment {
    @Bind(R.id.chart1)
    BarChart mChart;

    @Override
    public int getLayout() {
        return R.layout.mpa_fragment_scrollview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mChart.setDescription("");
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelsToSkip(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);

        mChart.getLegend().setEnabled(false);

        setData(10);
    }

    private void setData(int count) {

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * count) + 15;
            yVals.add(new BarEntry((int) val, i));
            xVals.add((int) val + "");
        }

        BarDataSet set = new BarDataSet(yVals, "Data Set");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setDrawValues(false);

        BarData data = new BarData(xVals, set);

        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(800);
    }
}
