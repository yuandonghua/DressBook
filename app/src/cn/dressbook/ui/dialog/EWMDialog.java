package cn.dressbook.ui.dialog;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description: 二维码对话框
 * @author:ydh
 * @data:2016-4-13上午9:50:43
 */
public class EWMDialog extends Dialog {
	private Context mContext;
	private ImageView gywm_iv;
	private TextView gywm_banbenhao;

	public EWMDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public EWMDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ewm_dialog);
		setCanceledOnTouchOutside(true);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		gywm_banbenhao = (TextView) findViewById(R.id.gywm_banbenhao);
		gywm_iv = (ImageView) findViewById(R.id.gywm_iv);
	}

	/**
	 * @description 设置二维码
	 */
	public void setEWM(String title, String ewm) {
		// 加载已存在的二维码
		x.image()
				.bind(gywm_iv,
						ManagerUtils.getInstance().getQrPath2(
								(Activity) mContext, ewm),
						ManagerUtils.getInstance().getImageOptions(
								ImageView.ScaleType.CENTER_INSIDE, false));
		gywm_banbenhao.setText(title + ":" + ewm);
//		LayoutParams lp = (LayoutParams) gywm_banbenhao.getLayoutParams();
//		lp.setMargins(0, -20, 0, 0);
//		gywm_banbenhao.setLayoutParams(lp);
	}

}
