
package cn.dressbook.ui.webkit;

import cn.dressbook.ui.R;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;


public class CustomWebChromeClient extends WebChromeClient{
	
	private static final String TAG = "CustomWebChromeClient";
	
	private ShangPinXiangQingActivity mMainActivity = null;
	
	private Bitmap mDefaultVideoPoster = null;
	private View mVideoProgressView = null;
	
	
	private CustomViewCallback mCustomViewCallback;
	private int mOriginalOrientation = 1;
	
	public boolean isUseNew = true;
	
	
	public CustomWebChromeClient (ShangPinXiangQingActivity mainActivity){
		super();
		mMainActivity = mainActivity;
	}

	

	

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
//		Log.e(TAG, "onProgressChanged()===newProgress==="+newProgress); 			

		 
		 
		 
	}
	
 
	 
	
	
	
	
	
	
	@Override
	public void onRequestFocus(WebView view) {
		super.onRequestFocus(view);
		Log.e(TAG, "onRequestFocus()====");
	}


	@Override
	public void onCloseWindow(WebView window) {
		super.onCloseWindow(window);
		Log.e(TAG, "onCloseWindow()====");
	}




	@Override
	public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//		Log.e(TAG, "onJsAlert()===");
		new AlertDialog.Builder(mMainActivity)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				result.confirm();
			}
		})
		.setCancelable(false)
		.create()
		.show();

		return true;
	}

	@Override
	public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
		new AlertDialog.Builder(mMainActivity)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, 
				new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) {
				result.confirm();
			}
		})
		.setNegativeButton(android.R.string.cancel, 
				new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) {
				result.cancel();
			}
		})
		.create()
		.show();

		return true;
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//		Log.e(TAG, "onJsPrompt()===");
		

        new AlertDialog.Builder(mMainActivity)
            .setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
            .setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            result.cancel();
                        }
                    })
            .setOnCancelListener(
                    new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            result.cancel();
                        }
                    })
            .show();
        
        return true;

	}		
	

}
