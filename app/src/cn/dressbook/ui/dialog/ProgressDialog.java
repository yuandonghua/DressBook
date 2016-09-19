package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import cn.dressbook.ui.R;

/**
 * @description: 进度条对话框
 * @author:袁东华
 * @time:2015-9-28下午9:16:40
 */
public class ProgressDialog extends Dialog {
	private Context mContext;
	private ProgressBar pbLoading;

	public ProgressDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public ProgressDialog(Context context) {
		super(context, R.style.Transparent_NoTitle);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.loading);
		initView();
		setCanceledOnTouchOutside(false);
	}

	private void initView() {
		// TODO Auto-generated method stub
		pbLoading = (ProgressBar) this.findViewById(R.id.pbLoading);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		if (pbLoading != null) {
			pbLoading.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		if (pbLoading != null) {

			pbLoading.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isShowing()) {

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
