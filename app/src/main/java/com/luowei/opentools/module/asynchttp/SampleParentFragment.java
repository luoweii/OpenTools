package com.luowei.opentools.module.asynchttp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by 骆巍 on 2016/5/6.
 */
public abstract class SampleParentFragment extends BaseFragment implements SampleInterface {
    protected static final String PROTOCOL_HTTP = "http://";
    protected static final String PROTOCOL_HTTPS = "https://";
    protected static final int LIGHTGREEN = Color.parseColor("#00FF66");
    protected static final int LIGHTRED = Color.parseColor("#FF3300");
    protected static final int YELLOW = Color.parseColor("#FFFF00");
    protected static final int LIGHTBLUE = Color.parseColor("#99CCFF");
    private static final String LOG_TAG = "SampleParentActivity";
    private static final int MENU_USE_HTTPS = 0;
    private static final int MENU_CLEAR_VIEW = 1;
    private static final int MENU_LOGGING_VERBOSITY = 2;
    private static final int MENU_ENABLE_LOGGING = 3;
    protected static String PROTOCOL = PROTOCOL_HTTPS;
    private final List<RequestHandle> requestHandles = new LinkedList<RequestHandle>();
    public LinearLayout customFieldsLayout;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient() {

        @Override
        protected AsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
            AsyncHttpRequest httpRequest = getHttpRequest(client, httpContext, uriRequest, contentType, responseHandler, context);
            return httpRequest == null
                    ? super.newAsyncHttpRequest(client, httpContext, uriRequest, contentType, responseHandler, context)
                    : httpRequest;
        }
    };

    private EditText urlEditText, headersEditText, bodyEditText;
    protected final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_run:
                    onRunButtonPressed();
                    break;
                case R.id.button_cancel:
                    onCancelButtonPressed();
                    break;
            }
        }
    };
    private LinearLayout responseLayout;
    private boolean useHttps = true;
    private boolean enableLogging = true;

    protected static String throwableToString(Throwable t) {
        if (t == null)
            return null;

        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static int getContrastColor(int color) {
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

    @Override
    public int getLayout() {
        return R.layout.asynchttp_parent_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        urlEditText = (EditText) rootView.findViewById(R.id.edit_url);
        headersEditText = (EditText) rootView.findViewById(R.id.edit_headers);
        bodyEditText = (EditText) rootView.findViewById(R.id.edit_body);
        customFieldsLayout = (LinearLayout) rootView.findViewById(R.id.layout_custom);
        Button runButton = (Button) rootView.findViewById(R.id.button_run);
        Button cancelButton = (Button) rootView.findViewById(R.id.button_cancel);
        LinearLayout headersLayout = (LinearLayout) rootView.findViewById(R.id.layout_headers);
        LinearLayout bodyLayout = (LinearLayout) rootView.findViewById(R.id.layout_body);
        responseLayout = (LinearLayout) rootView.findViewById(R.id.layout_response);

        urlEditText.setText(getDefaultURL());
        headersEditText.setText(getDefaultHeaders());

        bodyLayout.setVisibility(isRequestBodyAllowed() ? View.VISIBLE : View.GONE);
        headersLayout.setVisibility(isRequestHeadersAllowed() ? View.VISIBLE : View.GONE);

        runButton.setOnClickListener(onClickListener);
        if (cancelButton != null) {
            if (isCancelButtonAllowed()) {
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setOnClickListener(onClickListener);
            } else {
                cancelButton.setEnabled(false);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_USE_HTTPS, Menu.NONE, "Use HTTPS").setCheckable(true);
        menu.add(Menu.NONE, MENU_CLEAR_VIEW, Menu.NONE, "Clear Outputs");
        menu.add(Menu.NONE, MENU_ENABLE_LOGGING, Menu.NONE, "Enable Logging").setCheckable(true);
        menu.add(Menu.NONE, MENU_LOGGING_VERBOSITY, Menu.NONE, "Set Logging Verbosity");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem useHttpsMenuItem = menu.findItem(MENU_USE_HTTPS);
        if (useHttpsMenuItem != null) {
            useHttpsMenuItem.setChecked(useHttps);
        }
        MenuItem enableLoggingMenuItem = menu.findItem(MENU_ENABLE_LOGGING);
        if (enableLoggingMenuItem != null) {
            enableLoggingMenuItem.setChecked(enableLogging);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_USE_HTTPS:
                useHttps = !useHttps;
                PROTOCOL = useHttps ? PROTOCOL_HTTPS : PROTOCOL_HTTP;
                urlEditText.setText(getDefaultURL());
                return true;
            case MENU_ENABLE_LOGGING:
                enableLogging = !enableLogging;
                getAsyncHttpClient().setLoggingEnabled(enableLogging);
                return true;
            case MENU_LOGGING_VERBOSITY:
                showLoggingVerbosityDialog();
                return true;
            case MENU_CLEAR_VIEW:
                clearOutputs();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public AsyncHttpRequest getHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        return null;
    }

    public List<RequestHandle> getRequestHandles() {
        return requestHandles;
    }

    @Override
    public void addRequestHandle(RequestHandle handle) {
        if (null != handle) {
            requestHandles.add(handle);
        }
    }

    private void showLoggingVerbosityDialog() {
        AlertDialog ad = new AlertDialog.Builder(getContext())
                .setTitle("Set Logging Verbosity")
                .setSingleChoiceItems(new String[]{
                        "VERBOSE",
                        "DEBUG",
                        "INFO",
                        "WARN",
                        "ERROR",
                        "WTF"
                }, getAsyncHttpClient().getLoggingLevel() - 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAsyncHttpClient().setLoggingLevel(which + 2);
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .setNeutralButton("Cancel", null)
                .create();
        ad.show();
    }

    public void onRunButtonPressed() {
        addRequestHandle(executeSample(getAsyncHttpClient(),
                getUrlText(getDefaultURL()),
                getRequestHeaders(),
                getRequestEntity(),
                getResponseHandler()));
    }

    public void onCancelButtonPressed() {
        asyncHttpClient.cancelRequests(getContext(), true);
    }

    public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        String headersRaw = headersEditText.getText() == null ? null : headersEditText.getText().toString();

        if (headersRaw != null && headersRaw.length() > 3) {
            String[] lines = headersRaw.split("\\r?\\n");
            for (String line : lines) {
                try {
                    int equalSignPos = line.indexOf('=');
                    if (1 > equalSignPos) {
                        throw new IllegalArgumentException("Wrong header format, may be 'Key=Value' only");
                    }

                    String headerName = line.substring(0, equalSignPos).trim();
                    String headerValue = line.substring(1 + equalSignPos).trim();
                    Log.d(LOG_TAG, String.format("Added header: [%s:%s]", headerName, headerValue));

                    headers.add(new BasicHeader(headerName, headerValue));
                } catch (Throwable t) {
                    Log.e(LOG_TAG, "Not a valid header line: " + line, t);
                }
            }
        }
        return headers;
    }

    public Header[] getRequestHeaders() {
        List<Header> headers = getRequestHeadersList();
        return headers.toArray(new Header[headers.size()]);
    }

    public HttpEntity getRequestEntity() {
        String bodyText;
        if (isRequestBodyAllowed() && (bodyText = getBodyText()) != null) {
            try {
                return new StringEntity(bodyText);
            } catch (UnsupportedEncodingException e) {
                Log.e(LOG_TAG, "cannot create String entity", e);
            }
        }
        return null;
    }

    public String getUrlText() {
        return getUrlText(null);
    }

    public String getUrlText(String defaultText) {
        return urlEditText != null && urlEditText.getText() != null
                ? urlEditText.getText().toString()
                : defaultText;
    }

    public String getBodyText() {
        return getBodyText(null);
    }

    public String getBodyText(String defaultText) {
        return bodyEditText != null && bodyEditText.getText() != null
                ? bodyEditText.getText().toString()
                : defaultText;
    }

    public String getHeadersText() {
        return getHeadersText(null);
    }

    public String getHeadersText(String defaultText) {
        return headersEditText != null && headersEditText.getText() != null
                ? headersEditText.getText().toString()
                : defaultText;
    }

    protected final void debugHeaders(String TAG, Header[] headers) {
        if (headers != null) {
            Log.d(TAG, "Return Headers:");
            StringBuilder builder = new StringBuilder();
            for (Header h : headers) {
                String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
                Log.d(TAG, _h);
                builder.append(_h);
                builder.append("\n");
            }
            addView(getColoredView(YELLOW, builder.toString()));
        }
    }

    protected final void debugThrowable(String TAG, Throwable t) {
        if (t != null) {
            Log.e(TAG, "AsyncHttpClient returned error", t);
            addView(getColoredView(LIGHTRED, throwableToString(t)));
        }
    }

    protected final void debugResponse(String TAG, String response) {
        if (response != null) {
            Log.d(TAG, "Response data:");
            Log.d(TAG, response);
            addView(getColoredView(LIGHTGREEN, response));
        }
    }

    protected final void debugStatusCode(String TAG, int statusCode) {
        String msg = String.format(Locale.US, "Return Status Code: %d", statusCode);
        Log.d(TAG, msg);
        addView(getColoredView(LIGHTBLUE, msg));
    }

    protected View getColoredView(int bgColor, String msg) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(msg);
        tv.setBackgroundColor(bgColor);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(getContrastColor(bgColor));
        return tv;
    }

    @Override
    public String getDefaultHeaders() {
        return null;
    }

    protected final void addView(View v) {
        responseLayout.addView(v);
    }

    protected final void clearOutputs() {
        responseLayout.removeAllViews();
    }

    public boolean isCancelButtonAllowed() {
        return false;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return this.asyncHttpClient;
    }

    @Override
    public void setAsyncHttpClient(AsyncHttpClient client) {
        this.asyncHttpClient = client;
    }

}
