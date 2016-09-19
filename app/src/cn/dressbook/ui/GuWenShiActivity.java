package cn.dressbook.ui;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import cn.dressbook.ui.fragment.counselor.FragmentFactory;
import cn.dressbook.ui.fragment.counselor.RecommendFragment;
/**
 * 顾问师
 */
@ContentView(R.layout.activity_couselor)
public class GuWenShiActivity extends FragmentActivity {

	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	@ViewInject(R.id.title_tv)
	TextView titleTv;
	/**
	 * 今日推荐下的红线
	 */
	@ViewInject(R.id.recommend_red_line)
	TextView recommendRedLine;
	/**
	 * 顾问师下的红线
	 */
	@ViewInject(R.id.counselor_red_line)
	TextView counselorRedLine;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private boolean isRecommend = false, isCounselor = false;

	public void recommend(View view) {
		switchState(view);
	}

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		super.onCreate(arg0);
		x.view().inject(this);
		operate_tv.setVisibility(View.VISIBLE);
		operate_tv.setText("申请");
		titleTv.setVisibility(View.VISIBLE);
		titleTv.setText("顾问师");
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		transaction.replace(R.id.main_fl, new RecommendFragment());
		transaction.commit();
		isRecommend = true;
	}

	@Event({R.id.back_rl, R.id.operate_tv, R.id.recomm_ll, R.id.counselor })
	private void OnClick(View v) {
		switch (v.getId()) {
		// 点击申请
		case R.id.operate_tv:
			startActivity(new Intent(GuWenShiActivity.this,
					ShenQingActivity.class));
			break;
		// 点击来今日推荐
		case R.id.recomm_ll:
			if (isRecommend)
				return;
			recommendRedLine.setVisibility(View.VISIBLE);
			counselorRedLine.setVisibility(View.INVISIBLE);
			FragmentTransaction transaction2 = manager.beginTransaction();
			transaction2.replace(R.id.main_fl, FragmentFactory.getFragment(0));
			transaction2.commit();
			isRecommend = true;
			isCounselor = false;
			break;
		// 点击了顾问师
		case R.id.counselor:
			if (isCounselor)
				return;
			recommendRedLine.setVisibility(View.INVISIBLE);
			counselorRedLine.setVisibility(View.VISIBLE);
			FragmentTransaction transaction3 = manager.beginTransaction();
			transaction3.replace(R.id.main_fl, FragmentFactory.getFragment(1));
			transaction3.commit();
			isRecommend = false;
			isCounselor = true;
			break;
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 根据点击位置来更改状态
	 * 
	 * @param v
	 */
	private void switchState(View v) {
		switch (v.getId()) {

		}
	}

}
