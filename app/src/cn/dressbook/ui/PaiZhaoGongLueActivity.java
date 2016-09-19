/**
 *@description
 *@name PaiZhaoGongLueActivity.java
 *@author 袁东华
 *@data 2014-10-28下午4:13:24
 */
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @description: 拍照攻略
 * @author:袁东华
 * @time:2015-9-25下午4:39:12
 */
@ContentView(R.layout.paizhaogonglue)
public class PaiZhaoGongLueActivity extends BaseActivity  {
	@ViewInject(R.id.pzgl_wv)
	private WebView pzgl_wv;;
	private Context mContext;
	private int xx_id;
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

	private String mUrl = "http://mp.weixin.qq.com/s?__biz=MjM5ODU5MTc0Nw==&mid=225209632&idx=1&sn=59f7e45475d3a276bdba9949b38152f6#rd";

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		title_tv.setText("拍照攻略");
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


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("拍照攻略");
		pzgl_wv = (WebView) findViewById(R.id.pzgl_wv);
		pzgl_wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		WebSettings mWebSettings = pzgl_wv.getSettings();
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setSupportZoom(true);
		mWebSettings.setDomStorageEnabled(true);

		mWebSettings.setUseWideViewPort(true);
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
		pzgl_wv.loadUrl(mUrl);
		pzgl_wv.setWebViewClient(new WebViewClient() {

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

}
