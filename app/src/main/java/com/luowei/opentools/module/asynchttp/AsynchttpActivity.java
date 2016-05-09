package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.BaseAdapter;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.ViewHelper;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public class AsynchttpActivity extends BaseActivity {
    @Bind(R.id.list)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynchttp_ac_main);
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        setTitle(t.name);
        listView.setAdapter(new BaseAdapter<Tool>(t.children) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.asynchttp_list_item, null);
                }
                TextView tvName = ViewHelper.get(convertView, R.id.tvName);
                tvName.setText(getItem(position).name);
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Tool t = (Tool) parent.getAdapter().getItem((int) id);
                    startFragment((Fragment) Class.forName(t.intent).newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
