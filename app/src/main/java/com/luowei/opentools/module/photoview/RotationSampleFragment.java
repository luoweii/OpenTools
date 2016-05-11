package com.luowei.opentools.module.photoview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 骆巍 on 2016/5/10.
 */
public class RotationSampleFragment extends BaseFragment {
    private PhotoView photo;
    private final Handler handler = new Handler();
    private boolean rotating = false;

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        photo = new PhotoView(getContext());
        photo.setImageResource(R.drawable.photoview_bizhi);
        return photo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, 0, Menu.NONE, "Rotate 10° Right");
        menu.add(Menu.NONE, 1, Menu.NONE, "Rotate 10° Left");
        menu.add(Menu.NONE, 2, Menu.NONE, "Toggle automatic rotation");
        menu.add(Menu.NONE, 3, Menu.NONE, "Reset to 0");
        menu.add(Menu.NONE, 4, Menu.NONE, "Reset to 90");
        menu.add(Menu.NONE, 5, Menu.NONE, "Reset to 180");
        menu.add(Menu.NONE, 6, Menu.NONE, "Reset to 270");
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                photo.setRotationBy(10);
                return true;
            case 1:
                photo.setRotationBy(-10);
                return true;
            case 2:
                toggleRotation();
                return true;
            case 3:
                photo.setRotationTo(0);
                return true;
            case 4:
                photo.setRotationTo(90);
                return true;
            case 5:
                photo.setRotationTo(180);
                return true;
            case 6:
                photo.setRotationTo(270);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleRotation() {
        if (rotating) {
            handler.removeCallbacksAndMessages(null);
        } else {
            rotateLoop();
        }
        rotating = !rotating;
    }

    private void rotateLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                photo.setRotationBy(1);
                rotateLoop();
            }
        }, 15);
    }
}
