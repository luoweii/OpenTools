package com.luowei.opentools.module.photoview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 骆巍 on 2016/5/10.
 */
public class ImageloaderSampleFragment extends BaseFragment {
    private CharSequence activityTitle;
    @Override
    public int getLayout() {
        return R.layout.photoview_fr_imageloader;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityTitle = getActivity().getTitle();
        getActivity().setTitle(getTitle());

        PhotoView photoView = (PhotoView) rootView.findViewById(R.id.iv_photo);
        ImageLoader.getInstance().displayImage("http://pbs.twimg.com/media/Bist9mvIYAAeAyQ.jpg", photoView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle(activityTitle);
    }
}
