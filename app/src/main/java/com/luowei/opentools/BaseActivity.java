package com.luowei.opentools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.luowei.opentools.module.androiduniversalimageloader.ImageListFragment;
import com.luowei.opentools.utils.CommonUtil;
import com.luowei.opentools.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by 骆巍 on 2016/4/20.
 */
public class BaseActivity extends AppCompatActivity {
    public EventBus eventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LogUtil.i("--创建界面-->> " + getClass().getSimpleName());
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("--进入界面-->> " + getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("--离开界面-->> " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
        LogUtil.i("--销毁界面-->> " + getClass().getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(String msg) {
        CommonUtil.showToast(msg + " " + toString());
    }

    public void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .replace(android.R.id.content,fragment)
                .addToBackStack(null)
                .commit();
    }

}
