package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;

/**
 * @description: 图片详情
 * @author:袁东华
 * @time:2015-10-27下午3:34:44
 */
@SuppressLint("InflateParams")
public class PhotoShowAdapter extends PagerAdapter {
	private String[] photoUri;
	private Activity activity;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private int urlType;

	public static final int PHOTO_URL_TYPE_LOCAL = 8080;
	public static final int PHOTO_URL_TYPE_NET = 3306;

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-21 上午11:19:03
	 */
	public PhotoShowAdapter(Activity activity, String[] photoUri, int urlType) {
		this.photoUri = photoUri;
		this.activity = activity;
		this.urlType = urlType;
	}

	@Override
	public int getCount() {
		return photoUri != null ? photoUri.length : 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		object = null;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = activity.getLayoutInflater().inflate(
				R.layout.item_listview_photo_show, null);
		TextView tvItemPhotoShowIndicator = (TextView) view
				.findViewById(R.id.tvItemPhotoShowIndicator);
		ImageView ivItemPhotoShow = (ImageView) view
				.findViewById(R.id.ivItemPhotoShow);
		RelativeLayout rlItemPhotoShow = (RelativeLayout) view
				.findViewById(R.id.rlItemPhotoShow);
		// 显示文字指示
		tvItemPhotoShowIndicator
				.setText((position + 1) + "/" + photoUri.length);
		// 显示图片
		if (urlType == PHOTO_URL_TYPE_NET) {
			// 绑定图片
			x.image().bind(ivItemPhotoShow,
					 photoUri[position], mImageOptions,
					new CommonCallback<Drawable>() {

						@Override
						public void onSuccess(Drawable arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onFinished() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onCancelled(CancelledException arg0) {
							// TODO Auto-generated method stub
						}
					});

		} else {
			// 绑定图片
			x.image().bind(ivItemPhotoShow, photoUri[position],
					mImageOptions, new CommonCallback<Drawable>() {

						@Override
						public void onSuccess(Drawable arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onFinished() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onCancelled(CancelledException arg0) {
							// TODO Auto-generated method stub
						}
					});
		}
		ivItemPhotoShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		rlItemPhotoShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		container.addView(view);
		return view;
	}
}
