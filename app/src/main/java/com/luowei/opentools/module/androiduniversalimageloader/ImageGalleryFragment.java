package com.luowei.opentools.module.androiduniversalimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.luowei.opentools.BaseFragment;
import com.luowei.opentools.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageGalleryFragment extends BaseFragment {
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Gallery gallery = (Gallery) rootView.findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(getActivity()));
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				startImagePagerFragment(position);

				Fragment f = new ImagePagerFragment();
				Bundle b = new Bundle();
				b.putInt(Constants.Extra.IMAGE_POSITION, position);
				f.setArguments(b);
				getFragmentManager().beginTransaction()
						.setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
						.add(android.R.id.content, f)
						.addToBackStack(null)
						.commit();
			}
		});

	}

	@Override
	public int getLayout() {
		return R.layout.uil_fr_image_gallery;
	}

	private static class ImageAdapter extends BaseAdapter {

		private static final String[] IMAGE_URLS = Constants.IMAGES;

		private LayoutInflater inflater;

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
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new RoundedBitmapDisplayer(20))
					.build();
		}

		@Override
		public int getCount() {
			return IMAGE_URLS.length;
		}

		@Override
		public Object getItem(int position) {
			return IMAGE_URLS[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) inflater.inflate(R.layout.uil_item_gallery_image, parent, false);
			}
			ImageLoader.getInstance().displayImage(IMAGE_URLS[position], imageView, options);
			return imageView;
		}
	}

//	@Override
//	public boolean onBackPressed() {
//		if (getChildFragmentManager().getBackStackEntryCount() > 0) {
//			getChildFragmentManager().popBackStack();
//			return true;
//		}
//		return false;
//	}
}
