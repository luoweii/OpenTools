package com.luowei.opentools.module.asynchttp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class DigestAuthFragment extends  GetFragment{

    private EditText usernameField, passwordField;

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "httpbin.org/digest-auth/auth/user/passwd2";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        usernameField = new EditText(getContext());
        passwordField = new EditText(getContext());
        usernameField.setHint("Username");
        passwordField.setHint("Password");
        usernameField.setText("user");
        passwordField.setText("passwd2");
        customFieldsLayout.addView(usernameField);
        customFieldsLayout.addView(passwordField);
    }

    @Override
    public RequestHandle executeSample(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        setCredentials(client, URL);
        return client.get(getContext(), URL, headers, null, responseHandler);
    }

    @Override
    public boolean isCancelButtonAllowed() {
        return true;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    private void setCredentials(AsyncHttpClient client, String URL) {
        Uri parsed = Uri.parse(URL);
        client.clearCredentialsProvider();
        client.setCredentials(
                new AuthScope(parsed.getHost(), parsed.getPort() == -1 ? 80 : parsed.getPort()),
                new UsernamePasswordCredentials(
                        usernameField.getText().toString(),
                        passwordField.getText().toString()
                )
        );
    }
}
