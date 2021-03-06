package com.luowei.opentools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.util.Linkify;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.luowei.opentools.entity.Tool;
import com.luowei.opentools.test.TestListActivity;
import com.luowei.opentools.test.TestRippleActivity;
import com.luowei.opentools.utils.CommonUtil;
import com.luowei.opentools.utils.JsonUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.listView)
    ListView listView;
    private List<Tool> data = new ArrayList<>();
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(getBaseContext(), TestRippleActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initData();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void initData() {
        try {
            InputStream is = getResources().openRawResource(R.raw.tools);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String json = new String(buffer);
            data = JsonUtil.fromJson(json, new TypeToken<List<Tool>>() {
            }.getType());
            adapter = new MainAdapter();
            listView.setAdapter(adapter);
            listView.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(data);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Tool t = adapter.getItem((int) id);
                    startActivity(new Intent(getBaseContext(), Class.forName(t.intent))
                            .putExtra(Constant.TOOL, t));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int padding = (int) CommonUtil.dp2px(12);
                TextView tv = new TextView(view.getContext());
                tv.setTextIsSelectable(true);
                tv.setAutoLinkMask(Linkify.ALL);
                tv.setTextSize(18);
                tv.setPadding(padding, padding, padding, padding);
                tv.setText(adapter.getItem((int) id).url);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(tv)
                        .setPositiveButton("确定", null)
                        .show();
                return true;
            }
        });
    }
}
