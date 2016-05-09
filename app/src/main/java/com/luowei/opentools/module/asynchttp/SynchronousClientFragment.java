package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class SynchronousClientFragment extends GetFragment {
    private static final String LOG_TAG = "SyncSample";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAsyncHttpClient(new SyncHttpClient());
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    public String getDefaultURL() {
        return "https://httpbin.org/delay/6";
    }

    @Override
    public RequestHandle executeSample(final AsyncHttpClient client, final String URL, final Header[] headers, HttpEntity entity, final ResponseHandlerInterface responseHandler) {
        if (client instanceof SyncHttpClient) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(LOG_TAG, "Before Request");
                    client.get(getContext(), URL, headers, null, responseHandler);
                    Log.d(LOG_TAG, "After Request");
                }
            }).start();
        } else {
            Log.e(LOG_TAG, "Error, not using SyncHttpClient");
        }
        /**
         * SyncHttpClient does not return RequestHandle,
         * it executes each request directly,
         * therefore those requests are not in cancelable threads
         * */
        return null;
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearOutputs();
                    }
                });
            }

            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final byte[] response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        debugHeaders(LOG_TAG, headers);
                        debugStatusCode(LOG_TAG, statusCode);
                        debugResponse(LOG_TAG, new String(response));
                    }
                });
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final byte[] errorResponse, final Throwable e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        debugHeaders(LOG_TAG, headers);
                        debugStatusCode(LOG_TAG, statusCode);
                        debugThrowable(LOG_TAG, e);
                        if (errorResponse != null) {
                            debugResponse(LOG_TAG, new String(errorResponse));
                        }
                    }
                });
            }
        };
    }
}
