package com.luowei.opentools.module.asynchttp;

import android.util.Log;

import com.loopj.android.http.RequestHandle;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class CancelRequestHandleFragment extends ThreadingTimeoutFragment {

    private static final String LOG_TAG = "CancelRequestHandle";

    @Override
    public void onCancelButtonPressed() {
        Log.d(LOG_TAG, String.format("Number of handles found: %d", getRequestHandles().size()));
        int counter = 0;
        for (RequestHandle handle : getRequestHandles()) {
            if (!handle.isCancelled() && !handle.isFinished()) {
                Log.d(LOG_TAG, String.format("Cancelling handle %d", counter));
                Log.d(LOG_TAG, String.format("Handle %d cancel", counter) + (handle.cancel(true) ? " succeeded" : " failed"));
            } else {
                Log.d(LOG_TAG, String.format("Handle %d already non-cancellable", counter));
            }
            counter++;
        }
    }
}
