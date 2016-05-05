package com.luowei.opentools.module.mpandroidchart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.FileUtils;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/5/4.
 */
public class BarChartSinusFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener{
    @Bind(R.id.chart1)
    BarChart mChart;
    @Bind(R.id.seekbarValues)
    SeekBar mSeekBarX;
    @Bind(R.id.tvValueCount)
    TextView tvX;
    private Typeface mTf;

    private List<BarEntry> mSinusData;

    @Override
    public int getLayout() {
        return R.layout.mpa_fragment_barchart_sinus;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSinusData = FileUtils.loadBarEntriesFromAssets(getContext().getAssets(),"othersine.txt");
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        mTf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(6, false);
        leftAxis.setAxisMinValue(-2.5f);
        leftAxis.setAxisMaxValue(2.5f);
        leftAxis.setGranularity(0.1f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(6, false);
        rightAxis.setAxisMinValue(-2.5f);
        rightAxis.setAxisMaxValue(2.5f);
        rightAxis.setGranularity(0.1f);

        mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarX.setProgress(150); // set data

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        mChart.animateXY(2000, 2000);
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
                for (IBarDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if(mChart.getData() != null) {
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
                mChart.animateX(1500);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1500);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(2000, 2000);
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress()));

        setData(mSeekBarX.getProgress());
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setData(int count) {

        ArrayList<String> xVals = new ArrayList<String>();

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            xVals.add(i+"");
            entries.add(mSinusData.get(i));
        }

        BarDataSet set = new BarDataSet(entries, "Sinus Function");
        set.setBarSpacePercent(40f);
        set.setColor(Color.rgb(240, 120, 124));

        BarData data = new BarData(xVals, set);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        data.setDrawValues(false);

        mChart.setData(data);
    }
}
