package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.ui.view.MyWebView;

/**
 * @description: 招商活动界面
 * @author:袁东华
 * @time:下午2:35:36
 */
@ContentView(R.layout.aixin_layout)
public class ZhaoShangActivity extends BaseActivity {
	private Context mContext = ZhaoShangActivity.this;
	private MyWebView webview;
	private String mUrl = "http://admin.dressbook.cn/zutils/vipbao_page/s2.html";
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("招商");
		webview = (MyWebView) findViewById(R.id.webview);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		mUrl = intent.getStringExtra("url");
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

		mWebSettings.setSupportZoom(true);

		// 设置默认缩放方式尺寸是far

		mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);

		// 设置出现缩放工具

		mWebSettings.setBuiltInZoomControls(true);

		webview.loadUrl(mUrl);
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);

				pbDialog.show();
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

	@Event(value = { R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;

		default:
			break;
		}
	}

}
