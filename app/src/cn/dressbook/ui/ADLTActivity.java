package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description: 诊断量体
 * @author:袁东华
 * @time:2015-9-21下午3:33:51
 */
@ContentView(R.layout.diagnosebody)
public class ADLTActivity extends BaseActivity {
	private Context mContext = ADLTActivity.this;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 体征数据 */
	@ViewInject(R.id.tzsj_rl)
	private RelativeLayout tzsj_rl;
	/** 生活照 */
	@ViewInject(R.id.shz_rl)
	private RelativeLayout shz_rl;
	/** 肤色诊断 */
	@ViewInject(R.id.fszd_rl)
	private RelativeLayout fszd_rl;
	/** 搭配建议 */
	@ViewInject(R.id.dpjy_rl)
	private RelativeLayout dpjy_rl;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("诊断量体");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
	}

	@Event({ R.id.back_rl, R.id.tzsj_rl, R.id.shz_rl, R.id.fszd_rl,
			R.id.dpjy_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击体征数据
		case R.id.tzsj_rl:
			Intent bodyDataActivity = new Intent(this, ZhenDuanLTActivity.class);
			startActivity(bodyDataActivity);
			break;
		// 点击生活照
		case R.id.shz_rl:
			Intent lifePhotosActivity = new Intent(this,
					ShengHuoZhaoActivity.class);
			startActivity(lifePhotosActivity);
			break;
		// 点击肤色诊断
		case R.id.fszd_rl:
			Intent zhuoZhuangCeShiActivity = new Intent(this,
					FuSeZhenDuanActivity.class);
			startActivity(zhuoZhuangCeShiActivity);
			break;
		// 点击搭配建议
		case R.id.dpjy_rl:
			Intent result = new Intent(this, DiagnoseResultActivity.class);
			startActivity(result);
			break;
		default:
			break;
		}
	}

}
