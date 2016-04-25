package com.luowei.opentools.module.materialripple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.util.UUID;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/25.
 */
public class RippleRecyclerFragment extends BaseFragment {
    private final String[] data = new String[50];
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private static RippleRecyclerFragment fragment;

    public static RippleRecyclerFragment getInstance() {
        if (fragment == null) fragment = new RippleRecyclerFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < data.length; i++) {
            data[i] = UUID.randomUUID().toString();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter());

    }

    @Override
    public int getLayout() {
        return R.layout.ripple_fragment_recycleview;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            return new MyViewHolder(
                    MaterialRippleLayout.on(inflater.inflate(R.layout.ripple_recycler_item, viewGroup, false))
                            .rippleOverlay(true)
                            .rippleAlpha(0.2f)
                            .rippleColor(0xFF585858)
                            .rippleHover(true)
                            .rippleBackground(Color.WHITE)
                            .create()
            );
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.text.setText(data[i]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Rippled item: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            if (getAdapterPosition() % 2 == 0) {
                Toast.makeText(v.getContext(), "long item: " + getAdapterPosition() + " and not consumed",
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
            Toast.makeText(v.getContext(), "long item: " + getAdapterPosition() + " and consumed", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
    }
}
