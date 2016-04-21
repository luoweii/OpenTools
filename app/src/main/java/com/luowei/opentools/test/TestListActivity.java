package com.luowei.opentools.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.R;

/**
 * Created by 骆巍 on 2016/4/21.
 */
public class TestListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_list);

        OverscrollListView listView = (OverscrollListView)findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] {
                "List item 1",
                "List item 2",
                "List item 3",
                "List item 4",
                "List item 5",
                "List item 6",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 7",
                "List item 8"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

}
