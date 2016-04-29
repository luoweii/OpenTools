package com.luowei.opentools.module.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.utils.CommonUtil;
import com.luowei.opentools.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/28.
 */
public class EventbusActivity extends BaseActivity {
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_ac_main);
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        if (t != null) setTitle(t.name);
    }

    public void postEvent(View view) {
        text.setText("");
        eventBus.post(new EventEntity(spinner.getSelectedItem().toString()));
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(EventEntity e) {
        String msg = "ThreadMode.POSTING " + Thread.currentThread() + " " + e.msg;
        LogUtil.d(msg);
        text.append(msg + "\n\n");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent1(EventEntity e) {
        String msg = "ThreadMode.MAIN " + Thread.currentThread() + " " + e.msg;
        LogUtil.d(msg);
        text.append(msg + "\n\n");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent2(EventEntity e) {
        final String msg = "ThreadMode.BACKGROUND " + Thread.currentThread() + " " + e.msg;
        LogUtil.d(msg);
        text.post(new Runnable() {
            @Override
            public void run() {
                text.append(msg + "\n\n");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent3(EventEntity e) {
        final String msg = "ThreadMode.ASYNC " + Thread.currentThread() + " " + e.msg;
        LogUtil.d(msg);
        text.post(new Runnable() {
            @Override
            public void run() {
                text.append(msg + "\n\n");
            }
        });
    }

    public void postSticky(View view) {
        text.setText("");
        eventBus.postSticky(new EventEntity("postSticky"));
        startFragment(new EventbusFragment());
    }
}
