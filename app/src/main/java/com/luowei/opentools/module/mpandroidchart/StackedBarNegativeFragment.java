package com.luowei.opentools.module.mpandroidchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/5/4.
 */
public class StackedBarNegativeFragment extends BaseFragment implements OnChartValueSelectedListener {
    @Bind(R.id.chart1)
    HorizontalBarChart mChart;

    @Override
    public int getLayout() {
        return R.layout.mpa_fragment_age_distribution;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setAxisMaxValue(25f);
        mChart.getAxisRight().setAxisMinValue(-25f);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisRight().setDrawZeroLine(true);
        mChart.getAxisRight().setLabelCount(7, false);
        mChart.getAxisRight().setValueFormatter(new CustomFormatter());
        mChart.getAxisRight().setTextSize(9f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(new float[]{-10, 10}, 0));
        yValues.add(new BarEntry(new float[]{-12, 13}, 1));
        yValues.add(new BarEntry(new float[]{-15, 15}, 2));
        yValues.add(new BarEntry(new float[]{-17, 17}, 3));
        yValues.add(new BarEntry(new float[]{-19, 20}, 4));
        yValues.add(new BarEntry(new float[]{-19, 19}, 5));
        yValues.add(new BarEntry(new float[]{-16, 16}, 6));
        yValues.add(new BarEntry(new float[]{-13, 14}, 7));
        yValues.add(new BarEntry(new float[]{-10, 11}, 8));
        yValues.add(new BarEntry(new float[]{-5, 6}, 9));
        yValues.add(new BarEntry(new float[]{-1, 2}, 10));

        BarDataSet set = new BarDataSet(yValues, "Age Distribution");
        set.setValueFormatter(new CustomFormatter());
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setBarSpacePercent(40f);
        set.setColors(new int[]{Color.rgb(67, 67, 72), Color.rgb(124, 181, 236)});
        set.setStackLabels(new String[]{
                "Men", "Women"
        });

        String[] xVals = new String[]{"0-10", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90", "90-100", "100+"};

        BarData data = new BarData(xVals, set);
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mpa_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                List<IBarDataSet> sets = mChart.getData()
                        .getDataSets();

                for (IBarDataSet iSet : sets) {

                    BarDataSet set = (BarDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleHighlightArrow: {
                if (mChart.isDrawHighlightArrowEnabled())
                    mChart.setDrawHighlightArrow(false);
                else
                    mChart.setDrawHighlightArrow(true);
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        BarEntry entry = (BarEntry) e;
        Log.i("VAL SELECTED",
                "Value: " + Math.abs(entry.getVals()[h.getStackIndex()]));
    }

    @Override
    public void onNothingSelected() {
        Log.i("NOTING SELECTED", "");
    }

    private class CustomFormatter implements ValueFormatter, YAxisValueFormatter {

        private DecimalFormat mFormat;

        public CustomFormatter() {
            mFormat = new DecimalFormat("###");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(Math.abs(value)) + "m";
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(Math.abs(value)) + "m";
        }
    }
}
