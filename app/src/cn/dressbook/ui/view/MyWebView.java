/**
 * 
 */
package cn.dressbook.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author 袁东华
 * 
 */
public class MyWebView extends WebView {
	private ScrollInterface mScrollInterface;
	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyWebView(Context context, AttributeSet attrs) {

		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if(mScrollInterface!=null){
			
			mScrollInterface.onSChanged(l, t, oldl, oldt);
		}
	}

	public void setOnCustomScroolChangeListener(ScrollInterface scro) {
		this.mScrollInterface = scro;
	}

	public interface ScrollInterface {

		public void onSChanged(int l, int t, int oldl, int oldt);
	}
}
