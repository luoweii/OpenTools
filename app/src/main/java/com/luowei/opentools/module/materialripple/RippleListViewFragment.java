package com.luowei.opentools.module.materialripple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.util.UUID;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/25.
 */
public class RippleListViewFragment extends BaseFragment {
    private final String[] data = new String[50];

    @Bind(R.id.listView)
    ListView listView;
    private static RippleListViewFragment fragment;

    public static RippleListViewFragment getInstance() {
        if (fragment == null) fragment = new RippleListViewFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.ripple_fragment_listview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < data.length; i++) {
            data[i] = UUID.randomUUID().toString();
        }
        listView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.ripple_list_item, android.R.id.text1, data));
    }
}
