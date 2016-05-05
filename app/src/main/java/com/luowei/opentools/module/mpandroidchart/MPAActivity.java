package com.luowei.opentools.module.mpandroidchart;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luowei.opentools.App;
import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.BaseAdapter;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.ViewHelper;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/29.
 */
public class MPAActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpa_activity_main);
        setTitle("MPAndroidChart Example");
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);

        final MPAAdapter adapter = new MPAAdapter(t.children);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Tool t = adapter.getItem((int) id);
                    startFragment((Fragment) Class.forName(t.intent).newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class MPAAdapter extends BaseAdapter<Tool> {
        private Typeface mTypeFaceLight;
        private Typeface mTypeFaceRegular;

        public MPAAdapter(List<Tool> d) {
            super(d);
            mTypeFaceLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
            mTypeFaceRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mpa_list_item_main, null);
            }
            Tool t = getItem(position);
            TextView tvName = ViewHelper.get(convertView, R.id.tvName);
            TextView tvDesc = ViewHelper.get(convertView, R.id.tvDesc);
            TextView tvNew = ViewHelper.get(convertView, R.id.tvNew);
            tvName.setTypeface(mTypeFaceRegular);
            tvDesc.setTypeface(mTypeFaceLight);
            tvNew.setTypeface(mTypeFaceRegular);
            tvName.setText(t.name);
            tvDesc.setText(t.desc);
            tvNew.setVisibility(View.GONE);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mpa_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.viewGithub:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart"));
                startActivity(i);
                break;
            case R.id.report:
                i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "philjay.librarysup@gmail.com", null));
                i.putExtra(Intent.EXTRA_SUBJECT, "MPAndroidChart Issue");
                i.putExtra(Intent.EXTRA_TEXT, "Your error report here...");
                startActivity(Intent.createChooser(i, "Report Problem"));
                break;
            case R.id.blog:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.xxmassdeveloper.com"));
                startActivity(i);
                break;
            case R.id.website:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://at.linkedin.com/in/philippjahoda"));
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
