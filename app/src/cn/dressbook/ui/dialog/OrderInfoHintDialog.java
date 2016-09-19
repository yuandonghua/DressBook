package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 订单提示对话框
 * @author:袁东华
 * @time:2015-9-12下午3:41:42
 */
public class OrderInfoHintDialog extends Dialog implements
		View.OnClickListener {
	private Context mContext;
	private TextView wzdl, bzts;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();

	public OrderInfoHintDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public OrderInfoHintDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.orderinfohint_dialog);
		setCanceledOnTouchOutside(true);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
	}

	private void initView() {
		// TODO Auto-generated method stub
		wzdl = (TextView) findViewById(R.id.wzdl);
		wzdl.setOnClickListener(this);
		bzts = (TextView) findViewById(R.id.bzts);
		bzts.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击我知道了
		case R.id.wzdl:
			dismiss();
			break;
		// 点击不再提示
		case R.id.bzts:
			mSharedPreferenceUtils.setShowOrderHintDialog(mContext, false);
			dismiss();
			break;
		default:
			break;
		}
	}

}
