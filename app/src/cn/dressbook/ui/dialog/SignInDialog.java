package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.dressbook.ui.R;

/**
 * @description:签到对话框
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月20日 下午2:59:26
 */
public class SignInDialog extends Dialog implements OnClickListener {
	private Context mContext;
	private TextView signin_hint_1;
	private TextView signin_hint_2;
	private TextView signin_hint_3;

	public SignInDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public SignInDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.signin_dialog);
		setCanceledOnTouchOutside(true);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		signin_hint_1 = (TextView) findViewById(R.id.signin_hint_1);
		signin_hint_2 = (TextView) findViewById(R.id.signin_hint_2);
		signin_hint_3 = (TextView) findViewById(R.id.signin_hint_3);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setCanceledOnTouchOutside(true);
	}

	public void setData1() {
		signin_hint_1.setVisibility(View.VISIBLE);
		signin_hint_2.setVisibility(View.VISIBLE);
		signin_hint_3.setVisibility(View.GONE);
	};
	public void setData2() {
		signin_hint_1.setVisibility(View.GONE);
		signin_hint_2.setVisibility(View.GONE);
		signin_hint_3.setVisibility(View.VISIBLE);
	};
}
