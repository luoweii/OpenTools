package com.luowei.opentools;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
    private CharSequence activityTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = getLayoutInflater(savedInstanceState);
        setHasOptionsMenu(true);
        setTitle(getTitle());
    }

    public CharSequence setTitle(CharSequence title) {
        Activity activity = getActivity();
        if (activity != null) {
            activityTitle = activity.getTitle();
            activity.setTitle(title);
        }
        return activityTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        Activity a = getActivity();
        if (a != null && activityTitle != null) {
            a.setTitle(activityTitle);
        }
        super.onDestroy();
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
