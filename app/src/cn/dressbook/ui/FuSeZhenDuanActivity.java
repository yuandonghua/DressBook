/**
 *@name PaiZhaoGongLueActivity.java
 *@description
 *@author 袁东华
 *@data 2014-10-28下午4:13:24
 */
package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.MyWebView;

/**
 * @description: 肤色诊断
 * @author:袁东华
 * @time:2015-9-25下午3:37:28
 */
@ContentView(R.layout.zhuozhuangceshi_layout)
public class FuSeZhenDuanActivity extends BaseActivity {
	@ViewInject(R.id.webview)
	private MyWebView webview;
	private Context mContext = FuSeZhenDuanActivity.this;
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private String mCeShiJieGuo;

	@Event(R.id.back_rl)
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.back_rl:
			if (isFinish()) {
				finish();
			} else {
			}
			break;
		}
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		pbDialog.show();
		title_tv.setText("肤色测试");
		webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		WebSettings mWebSettings = webview.getSettings();
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setDomStorageEnabled(true);

		mWebSettings.setUseWideViewPort(false);
		mWebSettings.setLoadWithOverviewMode(true);
		mWebSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// WebView自适应屏幕大小
		mWebSettings.setDefaultTextEncodingName("UTF-8");
		mWebSettings.setLoadsImagesAutomatically(true);
		// 设置可以支持缩放

		mWebSettings.setSupportZoom(false);

		// 设置默认缩放方式尺寸是far

		mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);

		// 设置出现缩放工具

		mWebSettings.setBuiltInZoomControls(false);

		if (ManagerUtils.getInstance().getUser_id(mContext) != null) {

			// 测试
			webview.loadUrl(PathCommonDefines.SERVER_ADDRESS+"/quizzes/colorTest.html?userId="
					+ ManagerUtils.getInstance().getUser_id(mContext));

		} else {
		}
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);

			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return false;
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(mContext, "加载失败", Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
			}

			// 在加载页面结束时响应
			public void onPageFinished(WebView view, String url) {
				pbDialog.dismiss();
			}
		});

	}

	@Override
	protected void initData() {
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Bundle color = msg.getData();
				if (color != null) {
					mCeShiJieGuo = color.getString("color_result");
					mSharedPreferenceUtils.setCeShiJieGuo(mContext,
							mCeShiJieGuo);
					webview.loadUrl(mCeShiJieGuo);
				}
				break;

			case -1:
				break;

			case 2:
				Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();

				pbDialog.dismiss();
				break;
			case -2:
				pbDialog.dismiss();
				break;
			}

		}

	};
}
