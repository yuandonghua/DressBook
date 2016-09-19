package cn.dressbook.ui.adapter;

import java.io.File;
import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;

public class TryOnInfo2Adapter extends PagerAdapter {
	private ArrayList<String> mList;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, false);
	private Handler mHandler;

	public TryOnInfo2Adapter(Handler handler) {
		super();

		mHandler = handler;
	}

	public void setData(ArrayList<String> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final View view = LayoutInflater.from(container.getContext()).inflate(
				R.layout.tryoninfo2_item, container, false);
		final ImageView imageview = (ImageView) view
				.findViewById(R.id.imageview);
		if (mList != null && mList.size() > position) {
			// 绑定图片
			x.image().bind(imageview, mList.get(position), mImageOptions,
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
			imageview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.ONCLICK_TRYON_IMAGE);

				}
			});
			TextView share_tv = (TextView) view.findViewById(R.id.share_tv);
			TextView delete_tv = (TextView) view.findViewById(R.id.delete_tv);
			// 点击分享
			share_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.START_FENXIANG_S);

				}
			});
			// 点击删除
			delete_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mList != null && mList.size() > position) {

						String path = mList.get(position);
						if (path.contains("_")) {
							String str[] = path.split("_");
							String path1 = str[0] + ".a";
							File file1 = new File(mList.get(position));
							if (file1.exists()) {
								file1.delete();
							}
							File file2 = new File(path1);
							if (file2.exists()) {
								file2.delete();
							}
							File file3 = new File(path1.replace(".a", ".b"));
							if (file3.exists()) {
								file3.delete();
							}
							File file4 = new File(path1.replace(".a", ".c"));
							if (file4.exists()) {
								file4.delete();
							}
						} else {
							File file1 = new File(mList.get(position));
							if (file1.exists()) {
								file1.delete();
							}
							File file2 = new File(mList.get(position).replace(
									".a", ".b"));
							if (file2.exists()) {
								file2.delete();
							}
							File file3 = new File(mList.get(position).replace(
									".a", ".c"));
							if (file3.exists()) {
								file3.delete();
							}
						}

						imageview.setImageBitmap(null);
						mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DELETE_TRYON_S);
					}
				}
			});
			view.setTag("" + position);
			container.addView(view);
		}
		return view;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (object != null && object instanceof View) {
			container.removeView((View) object);
		}
	}

	@Override
	public int getItemPosition(Object object) {
		if (object instanceof View) {
			return Integer.valueOf(((View) object).getTag().toString());
		}
		return super.getItemPosition(object);
	}
}
