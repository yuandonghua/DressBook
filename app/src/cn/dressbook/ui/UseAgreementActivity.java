package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.view.MyWebView;

/**
 * @description: 穿衣典网络使用协议
 * @author:袁东华
 * @time:2015-9-2下午5:30:06
 */
@ContentView(R.layout.useagreement)
public class UseAgreementActivity extends BaseActivity  {
	private Context mContext = UseAgreementActivity.this;
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.webview)
	private MyWebView webview;
	private String mUrl = PathCommonDefines.SERVER_IMAGE_ADDRESS+"/zutils/wangluoshiyongxieyi.html";


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("用户使用协议");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
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
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return false;
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(mContext, "加载失败", Toast.LENGTH_LONG).show();
			}

			// 在加载页面结束时响应
			public void onPageFinished(WebView view, String url) {
			}
		});
	}

	@Event(R.id.back_rl)
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
