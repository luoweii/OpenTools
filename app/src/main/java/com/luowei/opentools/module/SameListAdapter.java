package com.luowei.opentools.module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luowei.opentools.BaseAdapter;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.ViewHelper;

/**
 * Created by 骆巍 on 2016/4/21.
 */
public class SameListAdapter extends BaseAdapter<Tool> {
    private LayoutInflater inflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.list_item_list, parent, false);
        }
        Tool t = data.get(position);
        TextView tvName = ViewHelper.get(convertView, R.id.tvName);
        tvName.setText(t.name);
        return convertView;
    }
}
