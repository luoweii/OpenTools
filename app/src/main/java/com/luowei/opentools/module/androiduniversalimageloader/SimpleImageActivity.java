package com.luowei.opentools.module.androiduniversalimageloader;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 骆巍 on 2016/4/27.
 */
public class SimpleImageActivity extends BaseActivity {
    private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";
    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uil_activity_simple);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        setTitle(t.name);

//        File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
//        if (!testImageOnSdCard.exists()) {
//            copyTestImageToSdCard(testImageOnSdCard);
//        }
    }

    public void onImageListClick(View view) {
        fragment = ImageListFragment.getInstance();
        startFragment(fragment);
    }

    public void onImageGridClick(View view) {
        fragment = ImageGridFragment.getInstance();
        startFragment(fragment);
    }

    public void onImagePagerClick(View view) {
    }

    public void onImageGalleryClick(View view) {
    }

    public void onFragmentsClick(View view) {
    }

    @Override
    public void onBackPressed() {
        ImageLoader.getInstance().stop();
        if (!fragment.onBackPressed()) super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.uil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear_memory_cache:
                ImageLoader.getInstance().clearMemoryCache();
                break;
            case R.id.item_clear_disc_cache:
                ImageLoader.getInstance().clearDiskCache();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void copyTestImageToSdCard(final File testImageOnSdCard) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getAssets().open(TEST_FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
                    byte[] buffer = new byte[8192];
                    int read;
                    try {
                        while ((read = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, read);
                        }
                    } finally {
                        fos.flush();
                        fos.close();
                        is.close();
                    }
                } catch (IOException e) {
                    L.w("Can't copy test image onto SD card");
                }
            }
        }).start();
    }
}
