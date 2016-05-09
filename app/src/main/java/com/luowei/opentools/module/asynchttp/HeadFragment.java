package com.luowei.opentools.module.asynchttp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public class HeadFragment extends FileSampleFragment {
    private static final String LOG_TAG = "HeadSample";

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                debugStatusCode(LOG_TAG, statusCode);
                debugHeaders(LOG_TAG, headers);
                debugResponse(LOG_TAG, String.format("Response of size: %d", responseBody == null ? 0 : responseBody.length));
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                addView(getColoredView(LIGHTRED, String.format("Progress %d from %d", bytesWritten, totalSize)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
                debugStatusCode(LOG_TAG, statusCode);
                debugHeaders(LOG_TAG, headers);
                debugThrowable(LOG_TAG, throwable);
                debugResponse(LOG_TAG, String.format("Response of size: %d", responseBody == null ? 0 : responseBody.length));
            }
        };
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.head(getContext(), URL, headers, null, responseHandler);
    }
}
