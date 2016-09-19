/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.PhotoShowActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.bean.MeitanBeanArticlePhotosUrl;

/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-16 上午11:45:04
 * @since
 * @version
 */
@SuppressLint("InflateParams")
public class ArticlePhotosAdapter extends BaseAdapter {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	/**
	 * TODO
	 */
	private Activity activity;
	/**
	 * TODO
	 */
	private ArrayList<String> data;

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午12:22:23
	 */
	public ArticlePhotosAdapter(Activity activity,
			List<MeitanBeanArticlePhotosUrl> data2) {
		this.data = new ArrayList<String>();
		if (data2 != null) {

			for (MeitanBeanArticlePhotosUrl url : data2) {
				this.data.add(url.getUrl());
			}
		}
		this.activity = activity;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data!=null?data.size():0;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public String getItem(int position) {
		return data.get(position);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.item_gridview_article_photos, null);
			holder.ivItemArticlePhotos = (ImageView) convertView
					.findViewById(R.id.ivItemArticlePhotos);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (mImageOptions != null) {
			x.image().bind(holder.ivItemArticlePhotos, data.get(position),
					mImageOptions, new CustomBitmapLoadCallBack(holder));
		} else {
			holder.ivItemArticlePhotos.setImageResource(R.drawable.pic_grey);
		}
		holder.ivItemArticlePhotos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, PhotoShowActivity.class);
				intent.putExtra("SHOW_WHICH", position);
				String[] urls=new String[data.size()];
				for(int i=0;i<data.size();i++){
					urls[i]=data.get(i);
				}
				intent.putExtra("PHOTO_URI_DATA", urls);
				activity.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView ivItemArticlePhotos;
	}

	public class CustomBitmapLoadCallBack implements
			Callback.ProgressCallback<Drawable> {
		private final ViewHolder holder;

		public CustomBitmapLoadCallBack(ViewHolder holder) {
			this.holder = holder;
		}

		@Override
		public void onWaiting() {

			// this.holder.imgPb.setProgress(0);
		}

		@Override
		public void onStarted() {

		}

		@Override
		public void onLoading(long total, long current, boolean isDownloading) {
			// this.holder.imgPb.setProgress((int) (current * 100 / total));
		}

		@Override
		public void onSuccess(Drawable result) {

			// this.holder.imgPb.setProgress(100);
		}

		@Override
		public void onError(Throwable ex, boolean isOnCallback) {
		}

		@Override
		public void onCancelled(CancelledException cex) {

		}

		@Override
		public void onFinished() {

		}
	}
}
