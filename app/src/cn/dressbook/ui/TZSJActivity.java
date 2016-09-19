package cn.dressbook.ui;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.utils.SharedPreferenceUtils;


/**
 * @description: 体征数据
 * @author:袁东华
 * @time:2015-9-21下午5:15:24
 */
@ContentView(R.layout.bodydata)
public class TZSJActivity extends BaseActivity {
	private Context mContext = TZSJActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 性别 */
	@ViewInject(R.id.sex_rl)
	private RelativeLayout sex_rl;
	@ViewInject(R.id.sex_tv)
	private TextView sex_tv;

	/** 生日 */
	@ViewInject(R.id.birthday_rl)
	private RelativeLayout birthday_rl;
	@ViewInject(R.id.birthday_tv)
	private TextView birthday_tv;

	/** 身高 */
	@ViewInject(R.id.height_rl)
	private RelativeLayout height_rl;
	@ViewInject(R.id.height_tv)
	private TextView height_tv;

	/** 体重 */
	@ViewInject(R.id.weight_rl)
	private RelativeLayout weight_rl;
	@ViewInject(R.id.weight_tv)
	private TextView weight_tv;

	/** 胸围 */
	@ViewInject(R.id.chest_rl)
	private RelativeLayout chest_rl;
	@ViewInject(R.id.chest_tv)
	private TextView chest_tv;

	/** 腰围 */
	@ViewInject(R.id.waist_rl)
	private RelativeLayout waist_rl;
	@ViewInject(R.id.waist_tv)
	private TextView waist_tv;
	/** 臀围 */
	@ViewInject(R.id.hipline_rl)
	private RelativeLayout hipline_rl;
	@ViewInject(R.id.hipline_tv)
	private TextView hipline_tv;

	/** 肩宽 */
	@ViewInject(R.id.shoulder_rl)
	private RelativeLayout shoulder_rl;
	@ViewInject(R.id.shoulder_tv)
	private TextView shoulder_tv;

	/** 臂长 */
	@ViewInject(R.id.arm_rl)
	private RelativeLayout arm_rl;
	@ViewInject(R.id.arm_tv)
	private TextView arm_tv;

	/** 腿长 */
	@ViewInject(R.id.leg_rl)
	private RelativeLayout leg_rl;
	@ViewInject(R.id.leg_tv)
	private TextView leg_tv;

	/** 颈围 */
	@ViewInject(R.id.neck_rl)
	private RelativeLayout neck_rl;
	@ViewInject(R.id.neck_tv)
	private TextView neck_tv;

	/** 腕围 */
	@ViewInject(R.id.wrist_rl)
	private RelativeLayout wrist_rl;
	@ViewInject(R.id.wrist_tv)
	private TextView wrist_tv;

	/** 脚长 */
	@ViewInject(R.id.foot_rl)
	private RelativeLayout foot_rl;
	@ViewInject(R.id.foot_tv)
	private TextView foot_tv;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("体征数据");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			sex_tv.setText(mSharedPreferenceUtils.getSex(mContext));
			birthday_tv.setText(mSharedPreferenceUtils.getBirthday(mContext));
			//身高
			String height = mSharedPreferenceUtils.getHeight(mContext);
			if (height.equals("未设置")||height.equals("0")) {
				height_tv.setText("未设置");
			} else {
				height_tv.setText(height + "cm");
			}
			//体重
			String weight = mSharedPreferenceUtils.getWeight(mContext);
			if (weight.equals("未设置")||weight.equals("0")) {
				weight_tv.setText("未设置");
			} else {
				weight_tv.setText(weight + "kg");
			}
			//胸围
			String chest = mSharedPreferenceUtils.getChest(mContext);
			if (chest.equals("未设置")||chest.equals("0")) {
				chest_tv.setText("未设置");
			} else {
				chest_tv.setText(chest + "cm");
			}
			//腰围
			String waist = mSharedPreferenceUtils.getWaist(mContext);
			if (waist.equals("未设置")||waist.equals("0")) {
				waist_tv.setText("未设置");
			} else {
				waist_tv.setText(waist + "cm");
			}
			//臀围
			String hipline = mSharedPreferenceUtils.getHipline(mContext);
			if (hipline.equals("未设置")||hipline.equals("0")) {
				hipline_tv.setText("未设置");
			} else {
				hipline_tv.setText(hipline + "cm");
			}
			//肩宽
			String shoulder = mSharedPreferenceUtils.getShoulder(mContext);
			if (shoulder.equals("未设置")||shoulder.equals("0")) {
				shoulder_tv.setText("未设置");
			} else {
				shoulder_tv.setText(shoulder + "cm");
			}
			//臂长
			String arm = mSharedPreferenceUtils.getArm(mContext);
			if (arm.equals("未设置")||arm.equals("0")) {
				arm_tv.setText("未设置");
			} else {
				arm_tv.setText(arm + "cm");
			}
			//腿长
			String leg = mSharedPreferenceUtils.getLeg(mContext);
			if (leg.equals("未设置")||leg.equals("0")) {
				leg_tv.setText("未设置");
			} else {
				leg_tv.setText(leg + "cm");
			}
			//颈围
			String neck = mSharedPreferenceUtils.getNeck(mContext);
			if (neck.equals("未设置")||neck.equals("0")) {
				neck_tv.setText("未设置");
			} else {
				neck_tv.setText(neck + "cm");
			}
			//腕围
			String wrist = mSharedPreferenceUtils.getWrist(mContext);
			if (wrist.equals("未设置")||wrist.equals("0")) {
				wrist_tv.setText("未设置");
			} else {
				wrist_tv.setText(wrist + "cm");
			}
			//脚长
			String foot = mSharedPreferenceUtils.getFoot(mContext);
			if (foot.equals("未设置")||foot.equals("0")) {
				foot_tv.setText("未设置");
			} else {
				foot_tv.setText(foot + "cm");
			}
		} catch (Exception e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
	}

	@Event({ R.id.back_rl, R.id.chest_rl, R.id.waist_rl, R.id.hipline_rl,
			R.id.shoulder_rl, R.id.arm_rl, R.id.leg_rl, R.id.neck_rl,
			R.id.wrist_rl, R.id.foot_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击胸围
		case R.id.chest_rl:
			Intent chestIntent = new Intent(this, SetBodyDataActivity.class);
			chestIntent.putExtra("type", "胸围");
			startActivity(chestIntent);
			break;
		// 点击腰围
		case R.id.waist_rl:
			Intent waistIntent = new Intent(this, SetBodyDataActivity.class);
			waistIntent.putExtra("type", "腰围");
			startActivity(waistIntent);
			break;
		// 点击臀围
		case R.id.hipline_rl:
			Intent hiplineIntent = new Intent(this, SetBodyDataActivity.class);
			hiplineIntent.putExtra("type", "臀围");
			startActivity(hiplineIntent);
			break;
		// 点击肩宽
		case R.id.shoulder_rl:
			Intent shoulderIntent = new Intent(this, SetBodyDataActivity.class);
			shoulderIntent.putExtra("type", "肩宽");
			startActivity(shoulderIntent);
			break;
		// 点击臂长
		case R.id.arm_rl:
			Intent armIntent = new Intent(this, SetBodyDataActivity.class);
			armIntent.putExtra("type", "臂长");
			startActivity(armIntent);
			break;
		// 点击腿长
		case R.id.leg_rl:
			Intent legIntent = new Intent(this, SetBodyDataActivity.class);
			legIntent.putExtra("type", "腿长");
			startActivity(legIntent);
			break;
		// 点击颈围
		case R.id.neck_rl:
			Intent neckIntent = new Intent(this, SetBodyDataActivity.class);
			neckIntent.putExtra("type", "颈围");
			startActivity(neckIntent);
			break;
		// 点击腕围
		case R.id.wrist_rl:
			Intent wristIntent = new Intent(this, SetBodyDataActivity.class);
			wristIntent.putExtra("type", "腕围");
			startActivity(wristIntent);
			break;
		// 点击脚长
		case R.id.foot_rl:
			Intent footIntent = new Intent(this, SetBodyDataActivity.class);
			footIntent.putExtra("type", "脚长");
			startActivity(footIntent);
			break;

		default:
			break;
		}
	}

}
