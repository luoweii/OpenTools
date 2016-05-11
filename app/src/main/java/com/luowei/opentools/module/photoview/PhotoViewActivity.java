package com.luowei.opentools.module.photoview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class PhotoViewActivity extends BaseActivity {
    public static final String[] options = {"Simple Sample", "ViewPager Sample", "Rotation Sample", "Imageloader Sample"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        setTitle(t.name);
        setContentView(R.layout.photoview_ac_main); RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter());
    }


    private class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ItemViewHolder.newInstance(parent);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, final int position) {
            holder.bind(options[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            startFragment(new SimpleSampleFragment());
                            break;
                        case 1:
                            startFragment(new ViewPagerFragment());
                            break;
                        case 2:
                            startFragment(new RotationSampleFragment());
                            break;
                        case 3:
                            startFragment(new ImageloaderSampleFragment());
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return options.length;
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public static ItemViewHolder newInstance(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.photoview_item_list_item, parent, false);
            return new ItemViewHolder(view);
        }

        public TextView mTextTitle;

        public ItemViewHolder(View view) {
            super(view);
            mTextTitle = (TextView) view.findViewById(R.id.title);
        }

        private void bind(String title) {
            mTextTitle.setText(title);
        }
    }
}
