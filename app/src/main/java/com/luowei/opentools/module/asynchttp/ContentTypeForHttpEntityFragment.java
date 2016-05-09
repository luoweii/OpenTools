package com.luowei.opentools.module.asynchttp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class ContentTypeForHttpEntityFragment extends SampleParentFragment {
    private static final String LOG_TAG = "ContentTypeForHttp";

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugResponse(LOG_TAG, responseString);
                debugThrowable(LOG_TAG, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugResponse(LOG_TAG, responseString);
            }
        };
    }

    @Override
    public String getDefaultURL() {
        return "https://httpbin.org/post";
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
        RequestParams rParams = new RequestParams();
        rParams.put("sample_key", "Sample String");
        try {
            File sample_file = File.createTempFile("temp_", "_handled", getContext().getCacheDir());
            rParams.put("sample_file", sample_file);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot add sample file", e);
        }
        return client.post(getContext(), URL, headers, rParams, "multipart/form-data", responseHandler);
    }
}
