/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import cn.dressbook.ui.R;


/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-11-2 下午4:35:41
 * @since
 * @version
 */
public class JoinSuccessDialog {

	private Dialog dialog;
	private ImageView ivLayoutJoinSuccessClose;
	private Button btnLayoutJoinSuccessSure;

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-11-2 下午4:39:09
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public JoinSuccessDialog(Context context) {
		// 创建dialog
		int v = Build.VERSION.SDK_INT;
		if (v >= 11) {
			dialog = new AlertDialog.Builder(context, R.style.AixinjuanyiDialog)
					.create();
		} else {
			dialog = new AlertDialog.Builder(context).create();
		}
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.show();
		// 自定义布局
		Window window = dialog.getWindow();
		window.setContentView(R.layout.layout_join_success_dialog);
		ivLayoutJoinSuccessClose = (ImageView) window
				.findViewById(R.id.ivLayoutJoinSuccessClose);
		btnLayoutJoinSuccessSure = (Button) window
				.findViewById(R.id.btnLayoutJoinSuccessSure);
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		};
		ivLayoutJoinSuccessClose.setOnClickListener(l);
		btnLayoutJoinSuccessSure.setOnClickListener(l);
	}
}
