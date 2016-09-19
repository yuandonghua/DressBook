package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.dressbook.ui.R;

/**
 * @description 提现对话框
 * @author 袁东华
 * @time 2015-12-10下午3:52:08
 */
public class TiXianDialog extends Dialog implements OnClickListener {
	private Context mContext;
	/**
	 * 联系客服
	 */
	private TextView call_cs;

	public TiXianDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public TiXianDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.joinyb_dialog);
		setCanceledOnTouchOutside(true);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		call_cs = (TextView) findViewById(R.id.call_cs);
		call_cs.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.call_cs) {
			Intent phoneIntent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + "010-60208599"));
			mContext.startActivity(phoneIntent);
		}
	}

}
