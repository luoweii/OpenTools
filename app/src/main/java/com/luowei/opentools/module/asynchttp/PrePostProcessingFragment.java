package com.luowei.opentools.module.asynchttp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.AbstractHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class PrePostProcessingFragment extends SampleParentFragment {

    protected static final int LIGHTGREY = Color.parseColor("#E0E0E0");
    protected static final int DARKGREY = Color.parseColor("#888888");
    private static final String LOG_TAG = "PrePostProcessingSample";

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.post(getContext(), URL, headers, entity, null, responseHandler);
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return true;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "httpbin.org/post";
    }

    @Override
    public AsyncHttpRequest getHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        return new PrePostProcessRequest(client, httpContext, uriRequest, responseHandler);
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new AsyncHttpResponseHandler() {

            @Override
            public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                debugProcessing(LOG_TAG, "Pre",
                        "Response is about to be pre-processed", LIGHTGREY);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                debugProcessing(LOG_TAG, "Post",
                        "Response is about to be post-processed", DARKGREY);
            }

            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugResponse(LOG_TAG, new String(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, e);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, new String(errorResponse));
                }
            }
        };
    }

    protected void debugProcessing(String TAG, String state, String message, final int color) {
        final String debugMessage = String.format(Locale.US, "%s-processing: %s", state, message);
        Log.d(TAG, debugMessage);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addView(getColoredView(color, debugMessage));
            }
        });
    }

    private class PrePostProcessRequest extends AsyncHttpRequest {

        public PrePostProcessRequest(AbstractHttpClient client, HttpContext httpContext, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
            super(client, httpContext, request, responseHandler);
        }

        @Override
        public void onPreProcessRequest(AsyncHttpRequest request) {
            debugProcessing(LOG_TAG, "Pre",
                    "Request is about to be pre-processed", LIGHTGREY);
        }

        @Override
        public void onPostProcessRequest(AsyncHttpRequest request) {
            debugProcessing(LOG_TAG, "Post",
                    "Request is about to be post-processed", DARKGREY);
        }
    }
}
