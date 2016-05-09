package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RangeFileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class RangeResponseFragment extends GetFragment {

    public static final String LOG_TAG = "RangeResponseSample";

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String ACCEPT_RANGES = "Accept-Ranges";
    private static final int CHUNK_SIZE = 10240;

    private File file;
    private long fileSize = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Temporary file to host the URL's downloaded contents.
            file = File.createTempFile("temp_", "_handled", getContext().getCacheDir());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot create temporary file", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove temporary file.
        if (file != null) {
            if (!file.delete()) {
                Log.e(LOG_TAG, String.format("Couldn't remove temporary file in path: %s", file.getAbsolutePath()));
            }
            file = null;
        }
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return false;
    }

    @Override
    public String getDefaultURL() {
        return "https://upload.wikimedia.org/wikipedia/commons/f/fa/Geysers_on_Mars.jpg";
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        if (fileSize > 0) {
            // Send a GET query when we know the size of the remote file.
            return client.get(getContext(), URL, headers, null, responseHandler);
        } else {
            // Send a HEAD query to know the size of the remote file.
            return client.head(getContext(), URL, headers, null, responseHandler);
        }
    }

    public void sendNextRangeRequest() {
        if (file.length() < fileSize) {
            // File is still smaller than remote file; send a new request.
            onRunButtonPressed();
        }
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new RangeFileAsyncHttpResponseHandler(file) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);

                if (fileSize < 1) {
                    boolean supportsRange = false;
                    // Cycle through the headers and look for the Content-Length header.
                    for (Header header : headers) {
                        String headerName = header.getName();
                        if (CONTENT_LENGTH.equals(headerName)) {
                            fileSize = Long.parseLong(header.getValue());
                        } else if (ACCEPT_RANGES.equals(headerName)) {
                            supportsRange = true;
                        }
                    }

                    // Is the content length known?
                    if (!supportsRange || fileSize < 1) {
                        Toast.makeText(
                                getContext(),
                                "Unable to determine remote file's size, or\nremote server doesn't support ranges",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                // If remote file size is known, request next portion.
                if (fileSize > 0) {
                    debugFileResponse(file);
                    // Send a new request for the same resource.
                    sendNextRangeRequest();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, File file) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, e);
                debugFileResponse(file);
            }

            @Override
            public void updateRequestHeaders(HttpUriRequest uriRequest) {
                // Call super so appending could work.
                super.updateRequestHeaders(uriRequest);

                // Length of the downloaded content thus far.
                long length = file.length();

                // Request the next portion of the file to be downloaded.
                uriRequest.setHeader("Range", "bytes=" + length + "-" + (length + CHUNK_SIZE - 1));
            }

            void debugFileResponse(File file) {
                debugResponse(LOG_TAG, "File size thus far: " + file.length() + " bytes");
            }
        };
    }
}
