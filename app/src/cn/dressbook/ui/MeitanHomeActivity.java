package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.dressbook.ui.base.MeitanFragmentActivity;
import cn.dressbook.ui.fragment.WoDeGuanZhuFragment;
import cn.dressbook.ui.fragment.SheQuGuangChangFragment;

/**
 * @description: 美谈主界面
 * @author:袁东华
 * @time:2015-10-26下午2:15:15
 */
@ContentView(R.layout.activity_meitan_home)
public class MeitanHomeActivity extends BaseFragmentActivity {
	/**
	 * 发博文
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private TextView tvMeitanHomeFocusLabel;
	private TextView tvMeitanHomeSquareLabel;
	private TextView tvMeitanHomeFocusIndicator;
	private TextView tvMeitanHomeSquareIndicator;

	private WoDeGuanZhuFragment fragmentFocus;
	private SheQuGuangChangFragment fragmentSquare;

	private FragmentManager fragmentManager;

	public static final int FOCUS = 0;
	public static final int SQUARE = 1;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("美谈");
		operate_tv.setText("发博文");
		operate_tv.setVisibility(View.VISIBLE);
		tvMeitanHomeFocusLabel = (TextView) findViewById(R.id.tvMeitanHomeFocusLabel);
		tvMeitanHomeSquareLabel = (TextView) findViewById(R.id.tvMeitanHomeSquareLabel);
		tvMeitanHomeFocusIndicator = (TextView) findViewById(R.id.tvMeitanHomeFocusIndicator);
		tvMeitanHomeSquareIndicator = (TextView) findViewById(R.id.tvMeitanHomeSquareIndicator);
		setListener();
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		// fragment管理器
		fragmentManager = getSupportFragmentManager();
		performTask();
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击发博文
		case R.id.operate_tv:
			operate_tv.setFocusable(false);
			startActivity(new Intent(MeitanHomeActivity.this,
					FaBoWenActivity.class));
			break;
		}
	}

	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				// 点击我的关注
				case R.id.tvMeitanHomeFocusLabel:
					setFragmentShowMeitanHome(FOCUS);
					break;
				// 点击社区广场
				case R.id.tvMeitanHomeSquareLabel:
					setFragmentShowMeitanHome(SQUARE);
					break;
				}
			}
		};
		tvMeitanHomeFocusLabel.setOnClickListener(l);
		tvMeitanHomeSquareLabel.setOnClickListener(l);
	}

	protected void performTask() {
		// 初始选择我的关注界面
		setFragmentShowMeitanHome(FOCUS);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-24 上午11:54:48
	 * @see MeitanFragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午1:25:48
	 * @see MeitanFragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		operate_tv.setFocusable(true);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午9:52:36
	 * @param index
	 *            选择哪个fragment
	 * @see
	 */
	private void setFragmentShowMeitanHome(int index) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 隐藏所有的fragment
		if (fragmentFocus != null) {
			transaction.hide(fragmentFocus);
		}
		if (fragmentSquare != null) {
			transaction.hide(fragmentSquare);
		}
		// 根据选择的tab做出相应的界面变化
		switch (index) {
		case FOCUS:
			tvMeitanHomeFocusIndicator.setBackgroundColor(getResources()
					.getColor(R.color.main_red));
			tvMeitanHomeSquareIndicator.setBackgroundColor(getResources()
					.getColor(R.color.main_background_grey));
			break;
		case SQUARE:
			tvMeitanHomeFocusIndicator.setBackgroundColor(getResources()
					.getColor(R.color.main_background_grey));
			tvMeitanHomeSquareIndicator.setBackgroundColor(getResources()
					.getColor(R.color.main_red));
			break;
		}
		// fragment显示
		switch (index) {
		case FOCUS:
			// fragment存在就显示，不存在就创建
			if (fragmentFocus == null) {
				fragmentFocus = new WoDeGuanZhuFragment();
				transaction.add(R.id.flMeitanHomeContainer, fragmentFocus);
			} else {
				transaction.show(fragmentFocus);
			}
			break;
		case SQUARE:
			// fragment存在就显示，不存在就创建
			if (fragmentSquare == null) {
				fragmentSquare = new SheQuGuangChangFragment();
				transaction.add(R.id.flMeitanHomeContainer, fragmentSquare);
			} else {
				transaction.show(fragmentSquare);
			}
			break;
		}
		// 提交事务
		transaction.commit();
	}

}
