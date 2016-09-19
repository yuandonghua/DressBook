package cn.dressbook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import cn.dressbook.ui.R;

/**
 * @description: 图片提示对话框
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-15 下午3:39:27
 */
public class ImageHintDialog extends Dialog implements
		View.OnClickListener {
	private Context mContext;
	/**
	 * 说明提示
	 */
	private ImageView imagehint_iv;
	/**
	 * 明白按钮
	 */
	private ImageView clear_iv;

	public ImageHintDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub

	}

	public ImageHintDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.setContentView(R.layout.imagehint_dialog);
		// setCanceledOnTouchOutside(true);
		initView();
	}

	public void initData(int resource) {
		// TODO Auto-generated method stub
		if (imagehint_iv != null) {
			imagehint_iv.setImageResource(resource);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		imagehint_iv = (ImageView) findViewById(R.id.imagehint_iv);
		// imagehint_iv.setOnClickListener(this);
		clear_iv = (ImageView) findViewById(R.id.clear_iv);
		clear_iv.setOnClickListener(this);
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
		switch (v.getId()) {
		// 点击明白
		case R.id.clear_iv:
			dismiss();
			break;
		// 点击图片
		// case R.id.imagehint_iv:
		// break;
		default:
			break;
		}
	}
}
