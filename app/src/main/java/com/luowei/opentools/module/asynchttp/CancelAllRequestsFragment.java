package com.luowei.opentools.module.asynchttp;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class CancelAllRequestsFragment extends ThreadingTimeoutFragment {

    @Override
    public void onCancelButtonPressed() {
        getAsyncHttpClient().cancelAllRequests(true);
    }
}
