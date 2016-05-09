package com.luowei.opentools.module.asynchttp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class CancelRequestByTagFragment extends ThreadingTimeoutFragment {

    private static final String LOG_TAG = "CancelRequestByTag";
    private static final Integer REQUEST_TAG = 132435;

    @Override
    public void onCancelButtonPressed() {
        Log.d(LOG_TAG, "Canceling requests by TAG: " + REQUEST_TAG);
        getAsyncHttpClient().cancelRequestsByTAG(REQUEST_TAG, false);
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new AsyncHttpResponseHandler() {

            private final int id = counter++;

            @Override
            public void onStart() {
                setStatus(id, "TAG:" + getTag() + ", START");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                setStatus(id, "SUCCESS");
            }

            @Override
            public void onFinish() {
                setStatus(id, "FINISH");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                setStatus(id, "FAILURE");
            }

            @Override
            public void onCancel() {
                setStatus(id, "CANCEL");
            }
        };
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.get(getContext(), URL, headers, null, responseHandler).setTag(REQUEST_TAG);
    }
}
