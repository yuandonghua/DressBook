package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.alipay.android.phone.mrpc.core.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.Wardrobe;
import cn.dressbook.ui.net.LTSJExec;
import cn.dressbook.ui.net.WardrobeExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.SelectSexPopupWindow;

/**
 * @description: 基础资料
 * @author:袁东华
 * @time:2015-9-27上午10:22:31
 */
@ContentView(R.layout.myinfo)
public class JiChuZiLiaoActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = JiChuZiLiaoActivity.this;
	/** 标题 */
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

	@ViewInject(R.id.rl)
	private RelativeLayout rl;
	private SelectSexPopupWindow mSelectSexPopupWindow;
	private String wardrobeId, sex, birthday, height, weight;
	private int flag;
	@ViewInject(R.id.submit_tv)
	private TextView submit_tv;
	private String lastOper;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("基础资料");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		wardrobeId = mSharedPreferenceUtils.getWardrobeID(mContext);

		pbDialog.show();
		// 获取量体数据
		LTSJExec.getInstance().getLTSJList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_LTSJ_S_KH,
				NetworkAsyncCommonDefines.GET_LTSJ_F_KH);
		
		setData();

	}

	private void setData() {
		// TODO Auto-generated method stub
		sex = mSharedPreferenceUtils.getSex(mContext);
		birthday = mSharedPreferenceUtils.getBirthday(mContext);
		height = mSharedPreferenceUtils.getHeight(mContext);
		weight = mSharedPreferenceUtils.getWeight(mContext);
		if (sex.equals("未设置") || "".equals(sex)) {
			sex_tv.setText("未设置");
		} else {
			sex_tv.setText(sex);
		}
		if (birthday.equals("未设置") || "".equals(birthday)) {
			birthday_tv.setText("未设置");
		} else {
			birthday_tv.setText(birthday);
		}
		if (height.equals("未设置") || height.equals("")) {
			height_tv.setText("未设置");
		} else {

			height_tv.setText(height + "cm");
		}

		if (weight.equals("未设置") || weight.equals("")) {
			weight_tv.setText("未设置");
		} else {
			weight_tv.setText(weight + "kg");
		}
		if (sex.equals("未设置") || birthday.equals("未设置") || height.equals("未设置")
				|| weight.equals("未设置") || sex.equals("")
				|| birthday.equals("") || height.equals("")
				|| weight.equals("")) {
			submit_tv.setVisibility(View.VISIBLE);
		} else {
			submit_tv.setVisibility(View.GONE);
		}
	}

	@Event(value = { R.id.back_rl, R.id.sex_rl, R.id.birthday_rl,
			R.id.height_rl, R.id.weight_rl, R.id.submit_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 提交
		case R.id.submit_tv:
			if (isFinish()) {
				String sexFlag = "1";
				if (sex.equals("女")) {
					sexFlag = "2";
				} else if (sex.equals("男")) {
					sexFlag = "1";
				} else {
					Toast.makeText(mContext, "请设置性别", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (birthday.equals("未设置")) {
					Toast.makeText(mContext, "请设置生日", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (height.equals("未设置") || height.equals("0")) {
					Toast.makeText(mContext, "请设置身高", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (weight.equals("未设置") || weight.equals("0")) {
					Toast.makeText(mContext, "请设置体重", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (wardrobeId != null && !"-1".equals(wardrobeId)) {
					finish();
					break;
				}
				pbDialog.show();
				// 创建衣柜
				WardrobeExec.getInstance().createWardrobe(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						sexFlag, birthday, height, weight,
						NetworkAsyncCommonDefines.CREATE_WARDROBE_S,
						NetworkAsyncCommonDefines.CREATE_WARDROBE_F);
			}
			break;
		// 点击返回
		case R.id.back_rl:
			if (isFinish()) {

				finish();
			}
			break;

		// 点击性别
		case R.id.sex_rl:
			if (!"无".equals(lastOper)) {
				Toast.makeText(mContext, "已经被量体,不能修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			flag = 0;
			if (mSelectSexPopupWindow == null) {
				mSelectSexPopupWindow = new SelectSexPopupWindow(activity,
						itemsOnClick);
			}
			mSelectSexPopupWindow.setContentShow(flag);
			mSelectSexPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		// 点击生日
		case R.id.birthday_rl:
			if (!"无".equals(lastOper)) {
				Toast.makeText(mContext, "已经被量体,不能修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			flag = 1;
			if (mSelectSexPopupWindow == null) {
				mSelectSexPopupWindow = new SelectSexPopupWindow(activity,
						itemsOnClick);
			}
			mSelectSexPopupWindow.setContentShow(flag);
			mSelectSexPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		// 点击身高
		case R.id.height_rl:
			if (!"无".equals(lastOper)) {
				Toast.makeText(mContext, "已经被量体,不能修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			flag = 2;
			if (mSelectSexPopupWindow == null) {
				mSelectSexPopupWindow = new SelectSexPopupWindow(activity,
						itemsOnClick);
			}
			mSelectSexPopupWindow.setContentShow(flag);
			mSelectSexPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		// 点击体重
		case R.id.weight_rl:
			if (!"无".equals(lastOper)) {
				Toast.makeText(mContext, "已经被量体,不能修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			flag = 3;
			if (mSelectSexPopupWindow == null) {
				mSelectSexPopupWindow = new SelectSexPopupWindow(activity,
						itemsOnClick);
			}
			mSelectSexPopupWindow.setContentShow(flag);
			mSelectSexPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;

		default:
			break;
		}
	}

	String sexFlag = "1";
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			mSelectSexPopupWindow.dismiss();
			switch (v.getId()) {
			case R.id.cancle_tv:
				break;
			case R.id.ok_tv:
				String key = null;
				String value = null;
				switch (flag) {
				// 性别
				case 0:
					key = "sex";
					sex = mSelectSexPopupWindow.sexpicker.sex;
					sex_tv.setText(sex);

					if (sex.equals("男")) {
						value = "1";

					} else {
						value = "2";
					}

					break;
				// 生日
				case 1:
					key = "birthday";
					birthday = mSelectSexPopupWindow.birthdaypicker.year + "-"
							+ mSelectSexPopupWindow.birthdaypicker.month + "-"
							+ mSelectSexPopupWindow.birthdaypicker.day;
					birthday_tv.setText(birthday);
					value = birthday;
					break;
				// 身高
				case 2:
					key = "height";
					height = mSelectSexPopupWindow.heightpicker.height;
					height_tv.setText(height + "cm");
					value = height;
					break;
				// 体重
				case 3:
					key = "weight";
					weight = mSelectSexPopupWindow.weightpicker.weight;
					weight_tv.setText(weight + "kg");
					value = weight;
					break;
				default:
					break;
				}
				if (key != null && value != null) {

					if (wardrobeId != null) {
						String tryResult = mSharedPreferenceUtils
								.getTryResultSave(mContext);
						if (!"no".equals(tryResult)) {
							// 删除服务端图片
							Intent service = new Intent(mContext,
									DownLoadingService.class);
							service.putExtra("id",
									NetworkAsyncCommonDefines.DELETE_SY_SERVER);
							startService(service);
						}
						File tryon = new File(PathCommonDefines.WARDROBE_TRYON);
						FileSDCacher.deleteDirectory2(tryon);
						// 修改用户资料
						WardrobeExec
								.getInstance()
								.editWardrobe(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										wardrobeId,
										key,
										value,
										NetworkAsyncCommonDefines.EDIT_USERINFO_S,
										NetworkAsyncCommonDefines.EDIT_USERINFO_F);
					} else {

					}
				} else {
				}
				break;
			default:
				break;
			}

		}

	};

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取量体数据成功
			case NetworkAsyncCommonDefines.GET_LTSJ_S_KH:
				Bundle ltsjData = msg.getData();
				if (ltsjData != null) {
					lastOper = ltsjData.getString("lastOper");
					LogUtil.e("lastOper:" + lastOper);
					if (!"无".equals(lastOper)) {
						// 被量体过
						ArrayList<LiangTiShuJu> list = ltsjData
								.getParcelableArrayList("ltsj");
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								if ("性别".equals(list.get(i).getName())) {
									mSharedPreferenceUtils.setSex(mContext,
											list.get(i).getValue());
									sex = mSharedPreferenceUtils
											.getSex(mContext);
								} else if ("出生日期".equals(list.get(i).getName())) {
									mSharedPreferenceUtils.setBirthday(
											mContext, list.get(i).getValue());
									birthday = mSharedPreferenceUtils
											.getBirthday(mContext);
								} else if ("身高".equals(list.get(i).getName())) {
									mSharedPreferenceUtils.setHeight(mContext,
											list.get(i).getValue());
									height = mSharedPreferenceUtils
											.getHeight(mContext);
								} else if ("体重".equals(list.get(i).getName())) {
									mSharedPreferenceUtils.setWeight(mContext,
											list.get(i).getValue());
									weight = mSharedPreferenceUtils
											.getWeight(mContext);
								}
							}
							sex_tv.setText(sex);
							birthday_tv.setText(birthday);
							if (height.equals("未设置")) {
								height_tv.setText(height);
							} else {

								height_tv.setText(height + "cm");
							}

							if (weight.equals("未设置")) {
								weight_tv.setText(weight);

							} else {

								weight_tv.setText(weight + "kg");
							}
							if (sex.equals("未设置") | birthday.equals("未设置")
									| height.equals("未设置")
									| weight.equals("未设置")) {
								submit_tv.setVisibility(View.VISIBLE);
							} else {
								submit_tv.setVisibility(View.GONE);
							}

						}
					} else {
						// 没有被量体

					}

				}
				setData();
				pbDialog.dismiss();
				break;
			// 获取量体数据失败
			case NetworkAsyncCommonDefines.GET_LTSJ_F_KH:
				pbDialog.dismiss();
				break;
			// 修改用户资料成功
			case NetworkAsyncCommonDefines.EDIT_USERINFO_S:
				Bundle wData = msg.getData();
				if (wData != null) {
					String mid = wData.getString("mid");
					mSharedPreferenceUtils.setSex(mContext, sex);
					mSharedPreferenceUtils.setBirthday(mContext, birthday);
					mSharedPreferenceUtils.setHeight(mContext, height);
					mSharedPreferenceUtils.setWeight(mContext, weight);
					mSharedPreferenceUtils.setMid(mContext, mid);
					File file = new File(PathCommonDefines.JSON_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + "w_" + wardrobeId + ".txt");
					if (file.exists()) {
						file.delete();
					}
				}
				break;
			// 修改用户资料失败
			case NetworkAsyncCommonDefines.EDIT_USERINFO_F:
				break;
			// 创建衣柜成功
			case NetworkAsyncCommonDefines.CREATE_WARDROBE_S:
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					WardrobeExec.getInstance().getWardrobe(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							NetworkAsyncCommonDefines.GET_WARDROBE_S,
							NetworkAsyncCommonDefines.GET_WARDROBE_F);
				}

				break;
			// 创建衣柜失败
			case NetworkAsyncCommonDefines.CREATE_WARDROBE_F:
				pbDialog.dismiss();
				break;
			// 获取衣柜信息成功
			case NetworkAsyncCommonDefines.GET_WARDROBE_S:
				Bundle wBun = msg.getData();
				if (wBun != null) {
					Wardrobe w = wBun.getParcelable("wardrobe");
					if (w != null) {

						mSharedPreferenceUtils.setWardrobeID(mContext,
								w.getWardrobeId() + "");
						mSharedPreferenceUtils.setMid(mContext, w.getShap()
								+ "");
						if (w.getSex() == 1) {
							mSharedPreferenceUtils.setSex(mContext, "男");
						} else {

							mSharedPreferenceUtils.setSex(mContext, "女");
						}
						mSharedPreferenceUtils.setBirthday(mContext,
								w.getBirthday());
						mSharedPreferenceUtils.setHeight(mContext,
								w.getHeight() + "");
						mSharedPreferenceUtils.setWeight(mContext,
								w.getWeight() + "");
						mSharedPreferenceUtils.setChest(mContext, w.getChest()
								+ "");
						mSharedPreferenceUtils.setWaist(mContext,
								w.getWaistline() + "");
						mSharedPreferenceUtils.setHipline(mContext,
								w.getHipline() + "");
						mSharedPreferenceUtils.setShoulder(mContext,
								w.getJiankuan() + "");
						mSharedPreferenceUtils.setArm(mContext, w.getBichang()
								+ "");
						mSharedPreferenceUtils.setLeg(mContext, w.getTuiChang()
								+ "");
						mSharedPreferenceUtils.setNeck(mContext, w.getJingwei()
								+ "");
						mSharedPreferenceUtils.setWrist(mContext, w.getWanwei()
								+ "");
						mSharedPreferenceUtils.setFoot(mContext, w.getFoot());

					}
				}
				pbDialog.dismiss();
				finish();
				break;
			// 获取衣柜信息失败
			case NetworkAsyncCommonDefines.GET_WARDROBE_F:
				pbDialog.dismiss();
				break;
			}
		}

	};

}
