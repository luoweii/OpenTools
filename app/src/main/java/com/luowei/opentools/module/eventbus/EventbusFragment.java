package com.luowei.opentools.module.eventbus;

import android.widget.TextView;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * Created by 骆巍 on 2016/4/29.
 */
public class EventbusFragment extends BaseFragment {
    @Bind(R.id.text)
    TextView text;

    @Override
    public int getLayout() {
        return R.layout.eventbus_fr_main;
    }

    @Subscribe(sticky = true)
    public void onEvent(EventEntity e) {
        text.setText(e.msg);
    }
}
