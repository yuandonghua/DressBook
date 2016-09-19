package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;

/**
 * @description: 确认发布新需求的对话框
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-4 下午1:15:24
 */
public class ConfirmRequirementDialog extends Dialog implements
		View.OnClickListener {
	private Context mContext;
	/**
	 * 发布按钮
	 */
	private TextView confirm_tv;
	private Handler mHandler;



	public ConfirmRequirementDialog(Context context, Handler mHandler) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.mHandler = mHandler;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tijiaodingdan_dialog);
		setCanceledOnTouchOutside(true);
		initView();
	}

	/**
	 * @description: 初始化View
	 * @exception
	 */
	private void initView() {
		// TODO Auto-generated method stub
		confirm_tv = (TextView) findViewById(R.id.confirm_tv);
		confirm_tv.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击发布
		case R.id.confirm_tv:
			mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CONFIRM_REQUIREMENT);
			break;

		}
	}

}
