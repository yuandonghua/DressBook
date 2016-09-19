/**
 *@name ToastUtils.java
 *@description
 *@author 袁东华
 *@data 2014-9-30下午3:55:16
 */
package cn.dressbook.ui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @description 自定义toast
 * @author 袁东华
 * @date 2014-9-30 下午3:55:16
 */
public class ToastUtils {
	// private GifView mGifView = null;
	// private GifDrawable drawableGif = null;
	private static Context mContext;
	private static ToastUtils mToastUtils;
	private Toast mToast;

	/**
	 *
	 */
	private ToastUtils() {
		// TODO Auto-generated constructor stub
	}

	public static synchronized ToastUtils getInstance() {
		if (mToastUtils == null) {
			mToastUtils = new ToastUtils();

		}
		return mToastUtils;

	}

	public void showToast(Context mContext, String text, int time) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, time);
		} else {
			mToast.setText(text);
			mToast.setDuration(time);
		}
		mToast.show();
	}

	public void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	// public void onBackPressed() {
	// cancelToast();
	// super.onBackPressed();
	// }

	// /**
	// * toast信息
	// */
	// public void showToast(Context context, LayoutInflater inflater,
	// int imageID, String content, int bgColor, int contentColor,
	// boolean hasImage) {
	// mContext = context;
	// View layout = inflater.inflate(R.layout.toast_layout, null);
	// TextView text = (TextView) layout.findViewById(R.id.toast_text);
	//
	// // 设置背景色
	// layout.setBackgroundColor(bgColor);
	// // 设置文本色
	// text.setTextColor(contentColor);
	// if (hasImage) {
	// RelativeLayout imageRL = (RelativeLayout) layout
	// .findViewById(R.id.toast_image_rl);
	// imageRL.setVisibility(View.VISIBLE);
	// ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
	// image.setBackgroundDrawable(context.getResources().getDrawable(imageID));
	// } else {
	// }
	//
	// text.setText(content);
	// if (mToast == null) {
	// mToast = new Toast(mContext);
	//
	// }
	// mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	// mToast.setDuration(Toast.LENGTH_LONG);
	// mToast.setView(layout);
	// mToast.show();
	//
	// }

	// public void newGifView() {
	// destroyGif();
	// destroyDrawable();
	// mGifView = new GifView(mContext);
	// LayoutParams l = new LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// mGifView.setLayoutParams(l);
	// mGifView.setScaleType(ScaleType.FIT_CENTER);
	// }
	//
	// public void destroyGif() {
	// if (mGifView != null) {
	// mGifView.destroy();
	// }
	// mGifView = null;
	// }
	//
	// public void destroyDrawable() {
	// if (drawableGif != null) {
	// drawableGif.destroy();
	// }
	// drawableGif = null;
	// }
}
