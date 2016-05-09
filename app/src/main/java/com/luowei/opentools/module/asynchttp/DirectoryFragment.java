package com.luowei.opentools.module.asynchttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public class DirectoryFragment extends SampleParentFragment {
    private static final String LOG_TAG = "DirectorySample";
    private FileAsyncHttpResponseHandler lastResponseHandler = null;
    private CheckBox cbAppend, cbRename;

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
        return "https://httpbin.org/robots.txt";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button deleteTargetFile = new Button(getContext());
        deleteTargetFile.setText("Delete original target file");
        deleteTargetFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOutputs();
                if (lastResponseHandler != null) {
                    File toBeDeleted = lastResponseHandler.getTargetFile();
                    debugResponse(LOG_TAG, String.format("File was deleted? %b", toBeDeleted.delete()));
                    debugResponse(LOG_TAG, String.format("Delete file path: %s", toBeDeleted.getAbsolutePath()));
                } else {
                    debugThrowable(LOG_TAG, new Error("You have to Run example first"));
                }
            }
        });
        cbAppend = new CheckBox(getContext());
        cbAppend.setText("Constructor \"append\" is true?");
        cbAppend.setChecked(false);
        cbRename = new CheckBox(getContext());
        cbRename.setText("Constructor \"renameTargetFileIfExists\" is true?");
        cbRename.setChecked(true);
        customFieldsLayout.addView(deleteTargetFile);
        customFieldsLayout.addView(cbAppend);
        customFieldsLayout.addView(cbRename);
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        lastResponseHandler = new FileAsyncHttpResponseHandler(getContext().getCacheDir(), cbAppend.isChecked(), cbRename.isChecked()) {
            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugFile(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, throwable);
                debugFile(file);
            }

            private void debugFile(File file) {
                if (file == null || !file.exists()) {
                    debugResponse(LOG_TAG, "Response is null");
                    return;
                }
                try {
                    debugResponse(LOG_TAG, file.getAbsolutePath() + "\r\n\r\n" + FileUtil.getStringFromFile(file));
                } catch (Throwable t) {
                    Log.e(LOG_TAG, "Cannot debug file contents", t);
                }
            }
        };
        return lastResponseHandler;
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return client.get(getContext(), URL, headers, null, responseHandler);
    }
}
