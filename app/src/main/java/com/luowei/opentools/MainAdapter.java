package com.luowei.opentools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.ResUtil;
import com.luowei.opentools.utils.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 骆巍 on 2016/4/20.
 */
public class MainAdapter extends BaseAdapter<Tool> {
    private LayoutInflater inflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.list_item_main, parent, false);
        }
        Tool t = data.get(position);
        TextView tvName = ViewHelper.get(convertView, R.id.tvName);
        tvName.setText(t.name);
        TextView tvDesc = ViewHelper.get(convertView, R.id.tvDesc);
        tvDesc.setText(t.desc);
        ImageView image = ViewHelper.get(convertView, R.id.imageView);
        ImageLoader.getInstance().displayImage("drawable://"+ResUtil.getId("drawable", t.imgId),image);
        return convertView;
    }
}
