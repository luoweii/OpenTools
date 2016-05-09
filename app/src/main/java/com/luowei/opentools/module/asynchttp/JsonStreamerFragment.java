package com.luowei.opentools.module.asynchttp;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public class JsonStreamerFragment extends PostFragment {
    private static final String LOG_TAG = "JsonStreamSample";

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        JSONObject body;
        if (isRequestBodyAllowed() && (body = getBodyTextAsJSON()) != null) {
            try {
                Iterator keys = body.keys();
                Log.d(LOG_TAG, "JSON data:");
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Log.d(LOG_TAG, "  " + key + ": " + body.get(key));
                    params.put(key, body.get(key).toString());
                }
            } catch (JSONException e) {
                Log.w(LOG_TAG, "Unable to retrieve a JSON value", e);
            }
        }
        return client.post(getContext(), URL, headers, params,
                RequestParams.APPLICATION_JSON, responseHandler);
    }

    @Override
    public HttpEntity getRequestEntity() {
        // Unused in this sample.
        return null;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return false;
    }

    protected JSONObject getBodyTextAsJSON() {
        String bodyText = getBodyText();
        if (bodyText != null && !TextUtils.isEmpty(bodyText)) {
            try {
                return new JSONObject(bodyText);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "User's data is not a valid JSON object", e);
            }
        }
        return null;
    }
}
