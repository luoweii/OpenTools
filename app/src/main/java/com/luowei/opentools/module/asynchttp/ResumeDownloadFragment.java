package com.luowei.opentools.module.asynchttp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RangeFileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class ResumeDownloadFragment extends SampleParentFragment {

    private static final String LOG_TAG = "ResumeDownloadSample";
    private File downloadTarget;

    private File getDownloadTarget() {
        try {
            if (downloadTarget == null) {
                downloadTarget = File.createTempFile("download_", "_resume", getContext().getCacheDir());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Couldn't create cache file to download to");
        }
        return downloadTarget;
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new RangeFileAsyncHttpResponseHandler(getDownloadTarget()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                debugStatusCode(LOG_TAG, statusCode);
                debugHeaders(LOG_TAG, headers);
                debugThrowable(LOG_TAG, throwable);
                if (file != null) {
                    addView(getColoredView(LIGHTGREEN, "Download interrupted (" + statusCode + "): (bytes=" + file.length() + "), path: " + file.getAbsolutePath()));
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                debugStatusCode(LOG_TAG, statusCode);
                debugHeaders(LOG_TAG, headers);
                if (file != null) {
                    addView(getColoredView(LIGHTGREEN, "Request succeeded (" + statusCode + "): (bytes=" + file.length() + "), path: " + file.getAbsolutePath()));
                }
            }
        };
    }

    @Override
    public String getDefaultHeaders() {
        return "Range=bytes=10-20";
    }

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "www.google.com/images/srpr/logo11w.png";
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.get(getContext(), URL, headers, null, responseHandler);
    }
}
