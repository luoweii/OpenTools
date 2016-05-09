package com.luowei.opentools.module.asynchttp;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.util.zip.Inflater;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by 骆巍 on 2016/5/9.
 */
public class Redirect302Fragment extends GetFragment {

    private static final int MENU_ENABLE_REDIRECTS = 10;
    private static final int MENU_ENABLE_CIRCULAR_REDIRECTS = 11;
    private static final int MENU_ENABLE_RELATIVE_REDIRECTs = 12;
    private boolean enableRedirects = true;
    private boolean enableRelativeRedirects = true;
    private boolean enableCircularRedirects = true;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, MENU_ENABLE_REDIRECTS, Menu.NONE, "Enable redirects").setCheckable(true);
        menu.add(Menu.NONE, MENU_ENABLE_RELATIVE_REDIRECTs, Menu.NONE, "Enable relative redirects").setCheckable(true);
        menu.add(Menu.NONE, MENU_ENABLE_CIRCULAR_REDIRECTS, Menu.NONE, "Enable circular redirects").setCheckable(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemEnableRedirects = menu.findItem(MENU_ENABLE_REDIRECTS);
        if (menuItemEnableRedirects != null)
            menuItemEnableRedirects.setChecked(enableRedirects);
        MenuItem menuItemEnableRelativeRedirects = menu.findItem(MENU_ENABLE_RELATIVE_REDIRECTs);
        if (menuItemEnableRelativeRedirects != null)
            menuItemEnableRelativeRedirects.setChecked(enableRelativeRedirects);
        MenuItem menuItemEnableCircularRedirects = menu.findItem(MENU_ENABLE_CIRCULAR_REDIRECTS);
        if (menuItemEnableCircularRedirects != null)
            menuItemEnableCircularRedirects.setChecked(enableCircularRedirects);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isCheckable()) {
            item.setChecked(!item.isChecked());
            if (item.getItemId() == MENU_ENABLE_REDIRECTS) {
                enableRedirects = item.isChecked();
            } else if (item.getItemId() == MENU_ENABLE_RELATIVE_REDIRECTs) {
                enableRelativeRedirects = item.isChecked();
            } else if (item.getItemId() == MENU_ENABLE_CIRCULAR_REDIRECTS) {
                enableCircularRedirects = item.isChecked();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getDefaultURL() {
        return PROTOCOL + "httpbin.org/redirect/6";
    }

    @Override
    public AsyncHttpClient getAsyncHttpClient() {
        AsyncHttpClient ahc = super.getAsyncHttpClient();
        HttpClient client = ahc.getHttpClient();
        if (client instanceof DefaultHttpClient) {
            Toast.makeText(getContext(),
                    String.format("redirects: %b\nrelative redirects: %b\ncircular redirects: %b",
                            enableRedirects, enableRelativeRedirects, enableCircularRedirects),
                    Toast.LENGTH_SHORT
            ).show();
            ahc.setEnableRedirects(enableRedirects, enableRelativeRedirects, enableCircularRedirects);
        }
        return ahc;
    }
}
