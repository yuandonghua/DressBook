package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import cn.dressbook.ui.R;

/**
 * @description 分享获取更多方案
 * @author 袁东华
 * @date 2014-8-22 下午10:10:30
 */
public class FenXiangDialog extends Dialog implements
		View.OnClickListener {
	private Context mContext;
	private TextView fenxiang_button,fenxiang_content;
	private Handler mHandler;
	private int mFlag,mPosition;

	public FenXiangDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public FenXiangDialog(Context context, Handler handler, int flag,int position) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mPosition=position;
		mContext = context;
		mHandler = handler;
		mFlag = flag;
		setCanceledOnTouchOutside(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Dialog#setCanceledOnTouchOutside(boolean)
	 * 
	 * @Description TODO
	 */
	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		// TODO Auto-generated method stub
		super.setCanceledOnTouchOutside(cancel);
		mHandler.sendEmptyMessage(141);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fenxiang_dialog_layout);
		setCanceledOnTouchOutside(true);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		fenxiang_content=(TextView) findViewById(R.id.fenxiang_content);
		if(mPosition==1){
			fenxiang_content.setText("小典再给您穿100套");
		}else if(mPosition==2){
			fenxiang_content.setText("是小典贵宾啦2500套！否则小典不干了...");
		}
		fenxiang_button = (TextView) this.findViewById(R.id.fenxiang_button);
		fenxiang_button.setOnClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * 
	 * @Description TODO
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.fenxiang_button) {
			dismiss();
			mHandler.sendEmptyMessage(mFlag);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		if(mPosition==1){
			fenxiang_content.setText("小典再给您穿100套");
		}else if(mPosition==2){
			fenxiang_content.setText("是小典贵宾啦2500套！否则小典不干了...");
		}
		
	}
	

}
