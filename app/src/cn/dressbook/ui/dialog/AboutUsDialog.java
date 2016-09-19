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
 * @description 关于我们
 * @author 袁东华
 * @date 2014-10-28 上午10:26:39
 */
public class AboutUsDialog extends Dialog {
	private Context mContext;
	private ImageView gywm_iv;
	private TextView gywm_banbenhao;

	public AboutUsDialog(Context context, int theme) {
		super(context, R.style.Translucent_NoTitle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public AboutUsDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.about_us_dialog);
		setCanceledOnTouchOutside(true);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		try {
			// 设置版本号
			gywm_banbenhao.setText("版本号："
					+ mContext.getPackageManager().getPackageInfo(
							mContext.getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/关于");
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		gywm_banbenhao = (TextView) findViewById(R.id.gywm_banbenhao);
		gywm_iv = (ImageView) findViewById(R.id.gywm_iv);
	}


}
