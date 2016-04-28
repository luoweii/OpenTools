/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.luowei.opentools.module.androiduniversalimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luowei.opentools.R;
import com.luowei.opentools.module.materialripple.RippleListViewFragment;
import com.luowei.opentools.utils.LogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageListFragment extends AbsListViewBaseFragment {
    @Override
    public int getLayout() {
        return R.layout.uil_fragment_image_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(new ImageAdapter(getContext()));
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startImagePagerFragment(position);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

    private static class ImageAdapter extends BaseAdapter {
        private static final String[] IMAGE_URLS = Constants.IMAGES;
        private LayoutInflater inflater;
        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        private DisplayImageOptions options;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.uil_ic_stub)
                    .showImageForEmptyUri(R.drawable.uil_ic_empty)
                    .showImageOnFail(R.drawable.uil_ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                    .build();
        }

        @Override
        public int getCount() {
            return IMAGE_URLS.length;
        }

        @Override
        public String getItem(int position) {
            return IMAGE_URLS[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = inflater.inflate(R.layout.uil_item_list_image, parent, false);
                holder = new ViewHolder();
                holder.text = (TextView) view.findViewById(R.id.text);
                holder.image = (ImageView) view.findViewById(R.id.image);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.text.setText("Item " + (position + 1));

            ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);

            return view;
        }
    }

    static class ViewHolder {
        TextView text;
        ImageView image;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
