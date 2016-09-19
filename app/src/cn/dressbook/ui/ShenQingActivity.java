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
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.DressingExec;
import cn.dressbook.ui.view.MyWebView;

/**
 * @description: 申请顾问师
 * @author:袁东华
 * @time:2015-11-25下午5:49:13
 */
@ContentView(R.layout.diagnoseresult)
public class ShenQingActivity extends BaseActivity {
	@ViewInject(R.id.webview)
	private MyWebView webview;
	private Context mContext = ShenQingActivity.this;
	// 返回按钮
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	// 标题
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private String url = "http://admin.dressbook.cn/adviser/blogpost.html";

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		pbDialog.show();
		title_tv.setText("申请顾问师");
		webview = (MyWebView) findViewById(R.id.webview);
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
		webview.loadUrl(url);

	}

	@Override
	protected void initData() {
	}

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

}
