package com.luowei.opentools;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luowei.opentools.utils.CommonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by 骆巍 on 2016/4/25.
 */
public abstract class BaseFragment extends Fragment {
    protected EventBus eventBus;
    public View rootView;
    public LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        inflater = getLayoutInflater(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
    }

    public String getTitle() {
        return getClass().getSimpleName();
    }

    @LayoutRes
    public abstract int getLayout();

    @Subscribe
    public void onEvent(String msg) {
        CommonUtil.showToast(msg + " " + toString());
    }

    public boolean onBackPressed() {
        return false;
    }
}
