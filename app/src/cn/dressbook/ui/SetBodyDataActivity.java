package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.WardrobeExec;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.SetBodyDataPicker;


/**
 * @description: 设置身体特征
 * @author:袁东华
 * @time:2015-10-21下午6:25:56
 */
@ContentView(R.layout.setbodydata)
public class SetBodyDataActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = SetBodyDataActivity.this;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 保存 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/** 图片 */
	@ViewInject(R.id.type_iv)
	private ImageView type_iv;
	private String mType;
	private String mValue;
	@ViewInject(R.id.setbodydataicker)
	private SetBodyDataPicker mSetBodyDataPicker;
	private String wardrobeId;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("体征数据");
		operate_tv.setText("保存");
		operate_tv.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		wardrobeId = mSharedPreferenceUtils.getWardrobeID(mContext);
		initIntent();
		title_tv.setText(mType);
		mSetBodyDataPicker.setType(mType);
		switch (mType) {
		case "胸围":
			type_iv.setImageResource(R.drawable.chest_src_1);
			break;
		case "腰围":
			type_iv.setImageResource(R.drawable.waist_src_1);
			break;
		case "臀围":
			type_iv.setImageResource(R.drawable.hipline_src_1);
			break;
		case "肩宽":
			type_iv.setImageResource(R.drawable.shoulder_src_1);
			break;
		case "臂长":
			type_iv.setImageResource(R.drawable.arm_src_1);
			break;
		case "腿长":
			type_iv.setImageResource(R.drawable.leg_src_1);
			break;
		case "颈围":
			type_iv.setImageResource(R.drawable.neck_src_1);
			break;
		case "腕围":
			type_iv.setImageResource(R.drawable.wrist_src_1);
			break;
		case "脚长":
			type_iv.setImageResource(R.drawable.foot_src_1);
			break;

		default:
			break;
		}
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			mType = intent.getStringExtra("type");
		}
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击保存
		case R.id.operate_tv:
			saveData();
			finish();
			break;

		default:
			break;
		}
	}

	private void saveData() {
		// TODO Auto-generated method stub
		String key = null;
		String value = null;
		switch (mType) {
		case "胸围":
			key = "chest";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setChest(mContext, mSetBodyDataPicker.data);
			break;
		case "腰围":
			key = "waistline";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setWaist(mContext, mSetBodyDataPicker.data);
			break;
		case "臀围":
			key = "hipline";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils
					.setHipline(mContext, mSetBodyDataPicker.data);
			break;
		case "肩宽":
			key = "jiankuan";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setShoulder(mContext,
					mSetBodyDataPicker.data);
			break;
		case "臂长":
			key = "bichang";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setArm(mContext, mSetBodyDataPicker.data);
			break;
		case "腿长":
			key = "tuichang";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setLeg(mContext, mSetBodyDataPicker.data);
			break;
		case "颈围":
			key = "jingwei";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setNeck(mContext, mSetBodyDataPicker.data);
			break;
		case "腕围":
			key = "wanwei";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setWrist(mContext, mSetBodyDataPicker.data);
			break;
		case "脚长":
			key = "xiema";
			value = mSetBodyDataPicker.data;
			mSharedPreferenceUtils.setFoot(mContext, mSetBodyDataPicker.data);
			break;

		default:
			break;
		}
		if (key != null && value != null) {

			if (wardrobeId != null) {
				// 修改用户资料
				WardrobeExec.getInstance().editWardrobe(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext), wardrobeId,
						key, value, NetworkAsyncCommonDefines.EDIT_USERINFO_S,
						NetworkAsyncCommonDefines.EDIT_USERINFO_F);
			} else {
			}
		} else {
		}
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
		};
	};

}
