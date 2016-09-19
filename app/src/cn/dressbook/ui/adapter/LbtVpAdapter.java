package cn.dressbook.ui.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.WangYeActivity;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.GuangGao;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @description 广告适配器
 * @author 袁东华
 * @date 2016-2-23
 */
public class LbtVpAdapter extends PagerAdapter {
	private List<GuangGao> list;
	private Context mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();

	public LbtVpAdapter(Context context) {

		this.mContext = context;
	}

	public void setData(List<GuangGao> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list != null && list.size() > 0 ? Integer.MAX_VALUE : 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (list != null && list.size() > 0) {

			ImageView channelView = (ImageView) LayoutInflater.from(mContext)
					.inflate(R.layout.item_lbt_vp, container, false);
			int newPosition = position % list.size();
			container.addView(channelView);
			final GuangGao gg = list.get(newPosition);
			x.image().bind(channelView, gg.getPic(), mImageOptions,
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
			// 点击广告
			channelView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ManagerUtils.getInstance().isLogin(mContext)) {
						if (!"".equals(gg.getUrl())) {

							Intent productIntent = new Intent(mContext,
									WangYeActivity.class);
							productIntent.putExtra("title", gg.getTitle());
							productIntent.putExtra("url", gg.getUrl());
							mContext.startActivity(productIntent);
						}
					} else {
						Intent loginIntent = new Intent(mContext,
								LoginActivity.class);
						mContext.startActivity(loginIntent);
					}
				}
			});
			return channelView;
		}
		return null;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	public interface ImageClick {
		void onImageClick(int position);
	}
}
