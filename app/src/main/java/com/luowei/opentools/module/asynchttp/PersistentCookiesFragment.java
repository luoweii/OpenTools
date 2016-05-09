package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.CookieStore;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class PersistentCookiesFragment extends SampleParentFragment {

    private static final String LOG_TAG = "PersistentCookiesSample";

    private CookieStore cookieStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Use the application's context so that memory leakage doesn't occur.
        cookieStore = new PersistentCookieStore(getContext());

        // Set the new cookie store.
        getAsyncHttpClient().setCookieStore(cookieStore);

        super.onCreate(savedInstanceState);
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
        // The base URL for testing cookies.
        String url = PROTOCOL + "httpbin.org/cookies";

        // If the cookie store is empty, suggest a cookie.
        if (cookieStore.getCookies().isEmpty()) {
            url += "/set?time=" + System.currentTimeMillis();
        }

        return url;
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new BaseJsonHttpResponseHandler<SampleJSON>() {
            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SampleJSON response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                if (response != null) {
                    debugResponse(LOG_TAG, rawJsonResponse);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SampleJSON errorResponse) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, throwable);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, rawJsonData);
                }
            }

            @Override
            protected SampleJSON parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValues(new JsonFactory().createParser(rawJsonData), SampleJSON.class).next();
            }
        };
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        client.setEnableRedirects(true);
        return client.get(getContext(), URL, headers, null, responseHandler);
    }

}
