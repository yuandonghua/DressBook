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
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.bean.AixinjuanyiBeanProjectProgress;
import cn.dressbook.ui.utils.SU;
import cn.dressbook.ui.utils.ScreenUtils;

/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-31 下午1:33:18
 * @since
 * @version
 */
@SuppressLint("InflateParams")
public class ProjectProgressAdapter extends BaseAdapter {
	private Activity activity;
	private List<AixinjuanyiBeanProjectProgress> data = new ArrayList<AixinjuanyiBeanProjectProgress>();
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午1:35:01
	 */
	public ProjectProgressAdapter(Activity activity,
			List<AixinjuanyiBeanProjectProgress> data) {
		this.activity = activity;
		this.data = data;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午1:33:18
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午1:33:18
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public AixinjuanyiBeanProjectProgress getItem(int position) {
		return data.get(position);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午1:33:18
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return data.get(position).hashCode();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午1:33:18
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.item_listview_project_progress, null);
			holder.tvItemProjectProgressDate = (TextView) convertView
					.findViewById(R.id.tvItemProjectProgressDate);
			holder.tvItemProjectProgressTitle = (TextView) convertView
					.findViewById(R.id.tvItemProjectProgressTitle);
			holder.tvItemProjectProgressContent = (TextView) convertView
					.findViewById(R.id.tvItemProjectProgressContent);
			holder.ivItemProjectProgressPhoto = (ImageView) convertView
					.findViewById(R.id.ivItemProjectProgressPhoto);
			holder.vItemVerticalLine = convertView
					.findViewById(R.id.vItemVerticalLine);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AixinjuanyiBeanProjectProgress progress = data.get(position);
		holder.tvItemProjectProgressDate.setText(progress.getOccTimeShow());
		holder.tvItemProjectProgressTitle.setText(progress.getTitle());
		holder.tvItemProjectProgressContent.setText(progress.getContent());
		if (SU.n(progress.getPic())) {
			holder.ivItemProjectProgressPhoto.setVisibility(View.VISIBLE);
			// 绑定图片
			x.image().bind(holder.ivItemProjectProgressPhoto,
					progress.getPic(), mImageOptions,
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
			holder.ivItemProjectProgressPhoto.setVisibility(View.GONE);
		}
		try {
			convertView.measure(0, 0);
			if (ScreenUtils.pxToDp(activity, convertView.getMeasuredHeight()) > 320) {
				// 如果高度超过一定的值，让线条高度充满item
				int w = (int) ScreenUtils.dpToPx(activity, 0.5f);
				if (w <= 1) {
					w = 1;
				}
				holder.vItemVerticalLine
						.setLayoutParams(new RelativeLayout.LayoutParams(w,
								convertView.getMeasuredHeight()));
			}
		} catch (Exception e) {
		}
		return convertView;
	}

	/**
	 * TODO 设置 data 的值
	 */
	public void setData(List<AixinjuanyiBeanProjectProgress> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-10-31 下午1:38:30
	 * @since
	 * @version
	 */
	private class ViewHolder {
		private TextView tvItemProjectProgressDate;
		private TextView tvItemProjectProgressTitle;
		private TextView tvItemProjectProgressContent;
		private ImageView ivItemProjectProgressPhoto;
		private View vItemVerticalLine;
	}
}
