package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

/**
 * @description 店家管理界面
 * @author 袁东华
 * @date 2016-3-14
 */
@ContentView(R.layout.djgl)
public class DianJiaGuanLiActivity extends BaseActivity {
	@ViewInject(R.id.title_tv)
	private TextView title_tv;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("老板管店");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.back_rl, R.id.ysjl_rl, R.id.yg_rl, R.id.dp_rl })
	private void onClick(View v) {
		pbDialog.show();
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			pbDialog.dismiss();
			finish();
			break;
		// 点击营收记录
		case R.id.ysjl_rl:
			startActivity(new Intent(activity, YingShouYiJiLuActivity.class));
			pbDialog.dismiss();

			break;
		// 点击员工
		case R.id.yg_rl:
			startActivity(new Intent(activity, YuanGongActivity.class));
			pbDialog.dismiss();
			break;
		// 点击店铺
		case R.id.dp_rl:
			startActivity(new Intent(activity, DianPuListActivity.class));
			pbDialog.dismiss();
			break;
		default:
			break;
		}
	}
}
