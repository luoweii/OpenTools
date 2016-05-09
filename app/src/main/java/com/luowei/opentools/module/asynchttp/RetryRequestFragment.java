package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.conn.ConnectionPoolTimeoutException;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class RetryRequestFragment extends GetFragment {

    private static boolean wasToastShown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The following exceptions will be whitelisted, i.e.: When an exception
        // of this type is raised, the request will be retried.
        AsyncHttpClient.allowRetryExceptionClass(IOException.class);
        AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
        AsyncHttpClient.allowRetryExceptionClass(ConnectTimeoutException.class);

        // The following exceptions will be blacklisted, i.e.: When an exception
        // of this type is raised, the request will not be retried and it will
        // fail immediately.
        AsyncHttpClient.blockRetryExceptionClass(UnknownHostException.class);
        AsyncHttpClient.blockRetryExceptionClass(ConnectionPoolTimeoutException.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!wasToastShown) {
            wasToastShown = true;
            Toast.makeText(
                    getContext(),
                    "Exceptions' whitelist and blacklist updated\nSee RetryRequestSample.java for details",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "httpbin.org/ip";
    }
}
