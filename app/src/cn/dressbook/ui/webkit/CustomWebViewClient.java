package cn.dressbook.ui.webkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.R;
import cn.dressbook.ui.ShangPinXiangQingActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.EditText;

/**
 * Convenient extension of WebViewClient.
 */
public class CustomWebViewClient extends WebViewClient {
	public static final String URL_GOOGLE_MOBILE_VIEW = "http://www.google.com/gwt/x?u=%s";
	private ShangPinXiangQingActivity mMainActivity;
	public static final String URL_GOOGLE_MOBILE_VIEW_NO_FORMAT = "http://www.google.com/gwt/x?u=";

	public CustomWebViewClient(ShangPinXiangQingActivity mainActivity) {
		super();
		mMainActivity = mainActivity;
	}

	@Override
	// webview加载完成会调用这个方法
	public void onPageFinished(WebView view, String url) {
		((CustomWebView) view).notifyPageFinished();
		super.onPageFinished(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {

		((CustomWebView) view).notifyPageStarted();
		super.onPageStarted(view, url, favicon);

	}

	@Override
	public void onReceivedSslError(WebView view, final SslErrorHandler handler,
			SslError error) {

	}

	// 跳转方法 处理提速问题

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

		if (view.getHitTestResult() != null
				&& view.getHitTestResult().getType() == HitTestResult.EMAIL_TYPE) {
			mMainActivity.onMailTo(url);
			return true;

		} else {
//			if ((!url.startsWith(URL_GOOGLE_MOBILE_VIEW_NO_FORMAT))) {
//				String newUrl = String.format(URL_GOOGLE_MOBILE_VIEW, url);
//				LogUtil.e("newUrl:"+newUrl);
//				view.loadUrl(newUrl);
//				return true;
//			} else {

				return false;
			}
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view,
			final HttpAuthHandler handler, final String host, final String realm) {
		String username = null;
		String password = null;

		boolean reuseHttpAuthUsernamePassword = handler
				.useHttpAuthUsernamePassword();

		if (reuseHttpAuthUsernamePassword && view != null) {
			String[] credentials = view
					.getHttpAuthUsernamePassword(host, realm);
			if (credentials != null && credentials.length == 2) {
				username = credentials[0];
				password = credentials[1];
			}
		}

		if (username != null && password != null) {
			handler.proceed(username, password);
		} else {
		}
	}

	private boolean isExternalApplicationUrl(String url) {
		return url.startsWith("vnd.") || url.startsWith("rtsp://")
				|| url.startsWith("itms://") || url.startsWith("youku://")
				|| url.startsWith("xlscheme://") || url.startsWith("itpc://");
	}

	/*
	 * zhangxuedong 错误页面监听
	 * 
	 * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView,
	 * int, java.lang.String, java.lang.String)
	 */
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// view.stopLoading();
		// view.clearView();
		// mMainActivity.mCurrentWebView.stopLoading();
		// mMainActivity.mCurrentWebView.clearView();
		// mMainActivity.mCurrentWebView.removeAllViews();
		// mMainActivity.mCurrentWebView.loadUrl("file:///android_asset/errorpage/loaderror.html");
		// mMainActivity.mUrlEditText.setText(failingUrl);
		super.onReceivedError(view, errorCode, description, failingUrl);

	}

}
