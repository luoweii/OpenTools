package com.luowei.opentools.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.CommonUtil;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/21.
 */
public class SameListActivity extends BaseActivity {
    @Bind(R.id.listView)
    ListView listView;
    private SameListAdapter adapter = new SameListAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samelist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView.setAdapter(adapter);
        Intent it = getIntent();
        if (it != null) {
            Tool tool = (Tool) it.getSerializableExtra(Constant.TOOL);
            setTitle(tool.name);
            adapter.setData(tool.children);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Tool t = adapter.getItem((int) id);
                    startActivity(new Intent(getBaseContext(), Class.forName(t.intent)).putExtra(Constant.TOOL,t));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
