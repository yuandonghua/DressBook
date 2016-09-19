package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.ZhenDuanLTActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.LTJDAdapter;
import cn.dressbook.ui.adapter.WDSJAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.KHXX;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.User;
import cn.dressbook.ui.net.LTSJExec;
import cn.dressbook.ui.net.SMSExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.ToastUtils;
import cn.dressbook.ui.view.BirthdayPicker2;

/**
 * @description: 量体界面
 * @author:ydh
 * @data:2016-4-7下午4:49:49
 */
@ContentView(R.layout.fragment_ltsj)
public class LiangTiFragment extends BaseFragment implements OnClickListener {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.FIT_CENTER);
	private ImageOptions mImageOptionHead = ManagerUtils.getInstance()
			.getImageOptionsCircle(true);
	/** 量体展示的布局 */
	@ViewInject(R.id.ltAll_ll)
	private LinearLayout ltAll_ll;

	/**
	 * 上一个标题
	 */
	@ViewInject(R.id.lastTitle_tv)
	private TextView lastTitle_tv;
	/**
	 * 当前身体数据的标题
	 */
	@ViewInject(R.id.currentTitle_tv)
	private TextView currentTitle_tv;
	/**
	 * 下一个身体数据的标题
	 */
	@ViewInject(R.id.nextTitle_tv)
	private TextView nextTitle_tv;
	/**
	 * 上一个身体数据的内容
	 */
	@ViewInject(R.id.lastContent_tv)
	private TextView lastContent_tv;
	/**
	 * 当前身体数据的内容
	 */
	@ViewInject(R.id.currentContent_tv)
	private TextView currentContent_tv;
	/**
	 * 下一个身体数据的内容
	 */
	@ViewInject(R.id.nextContent_tv)
	private TextView nextContent_tv;
	/**
	 * 上一个身体数据的单位
	 */
	@ViewInject(R.id.lastDanWei_tv)
	private TextView lastDanWei_tv;
	/**
	 * 当前身体数据的单位
	 */
	@ViewInject(R.id.currentDanWei_tv)
	private TextView currentDanWei_tv;
	/**
	 * 下一个身体数据的单位
	 */
	@ViewInject(R.id.nextDanWei_tv)
	private TextView nextDanWei_tv;
	/**
	 * 选择键盘
	 */
	@ViewInject(R.id.first_ll)
	private LinearLayout first_ll;

	/**
	 * 生日键盘
	 */
	@ViewInject(R.id.second_ll)
	private LinearLayout second_ll;
	/**
	 * 生日选择器
	 */
	@ViewInject(R.id.birthdayPicker)
	private BirthdayPicker2 birthdayPicker;

	/**
	 * 数字键盘
	 */
	@ViewInject(R.id.third_ll)
	private LinearLayout third_ll;

	/**
	 * 结束按钮
	 */
	@ViewInject(R.id.jieShu_tv)
	private TextView jieShu_tv;
	/**
	 * 遮挡区域
	 */
	@ViewInject(R.id.zheDang_tv)
	private TextView zheDang_tv;

	/**
	 * 被量体人信息展示的View
	 */
	@ViewInject(R.id.user_ll)
	private LinearLayout user_ll;
	/**
	 * 被量体人的头像
	 */
	@ViewInject(R.id.userHead_iv)
	private ImageView userHead_iv;
	/**
	 * 被量体人的昵称
	 */
	@ViewInject(R.id.userName_tv)
	private TextView userName_tv;
	/**
	 * 被量体人的手机号
	 */
	@ViewInject(R.id.userPhone_tv)
	private TextView userPhone_tv;
	/**
	 * 量体进度
	 */
	@ViewInject(R.id.jinDu_tv)
	private TextView jinDu_tv;
	/**
	 * 提示图
	 */
	@ViewInject(R.id.tiShiTu_iv)
	private ImageView tiShiTu_iv;
	/**
	 * 提示文本
	 */
	@ViewInject(R.id.tiShi_tv)
	private TextView tiShi_tv;
	/**
	 * 打开侧滑菜单的开关
	 */
	@ViewInject(R.id.open_iv)
	private ImageView open_iv;
	/**
	 * 侧滑菜单的适配器
	 */
	private LTJDAdapter ltjdAdapter;
	/**
	 * 侧滑菜单的View
	 */
	private DrawerLayout drawerLayout;
	/**
	 * 侧滑菜单的左侧的View
	 */
	private LinearLayout ceHua_ll;
	/**
	 * 上一个按钮
	 */
	@ViewInject(R.id.last_iv)
	private ImageView last_iv;
	/**
	 * 下一个按钮
	 */
	@ViewInject(R.id.next_iv)
	private ImageView next_iv;
	/**
	 * 选择键盘的取消按钮
	 */
	@ViewInject(R.id.first_qx_tv)
	private TextView first_qx_tv;
	/**
	 * 选择键盘的保存按钮
	 */
	@ViewInject(R.id.first_bc_tv)
	private TextView first_bc_tv;
	/**
	 * 生日键盘的取消按钮
	 */
	@ViewInject(R.id.second_qx_tv)
	private TextView second_qx_tv;
	/**
	 * 数字键盘的数字1按钮
	 */
	@ViewInject(R.id.third_1_tv)
	private TextView third_1_tv;
	/**
	 * 数字键盘的数字2按钮
	 */
	@ViewInject(R.id.third_2_tv)
	private TextView third_2_tv;
	/**
	 * 数字键盘的数字3按钮
	 */
	@ViewInject(R.id.third_3_tv)
	private TextView third_3_tv;
	/**
	 * 数字键盘的数字4按钮
	 */
	@ViewInject(R.id.third_4_tv)
	private TextView third_4_tv;
	/**
	 * 数字键盘的数字5按钮
	 */
	@ViewInject(R.id.third_5_tv)
	private TextView third_5_tv;
	/**
	 * 数字键盘的数字6按钮
	 */
	@ViewInject(R.id.third_6_tv)
	private TextView third_6_tv;
	/**
	 * 数字键盘的数字7按钮
	 */
	@ViewInject(R.id.third_7_tv)
	private TextView third_7_tv;
	/**
	 * 数字键盘的数字8按钮
	 */
	@ViewInject(R.id.third_8_tv)
	private TextView third_8_tv;
	/**
	 * 数字键盘的数字9按钮
	 */
	@ViewInject(R.id.third_9_tv)
	private TextView third_9_tv;
	/**
	 * 数字键盘的数字0按钮
	 */
	@ViewInject(R.id.third_0_tv)
	private TextView third_0_tv;
	/**
	 * 数字键盘的数字.按钮
	 */
	@ViewInject(R.id.third_dian_tv)
	private TextView third_dian_tv;
	/**
	 * 生日键盘的保存按钮
	 */
	@ViewInject(R.id.second_bc_tv)
	private TextView second_bc_tv;
	/**
	 * 数字键盘的删除按钮
	 */
	@ViewInject(R.id.third_delete_iv)
	private ImageView third_delete_iv;
	/**
	 * 数字键盘的取消按钮
	 */
	@ViewInject(R.id.third_qx_tv)
	private TextView third_qx_tv;
	/**
	 * 数字键盘的保存按钮
	 */
	@ViewInject(R.id.third_bc_tv)
	private TextView third_bc_tv;
	/**
	 * 出生日期
	 */
	private String year, month, day;
	/**
	 * 被量体用户
	 */
	private User user;
	private SharedPreferenceUtils mPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/**
	 * 当前的输入参数
	 */
	private String currentInputValue = "";

	public LiangTiFragment(final DrawerLayout drawerLayout2,
			final LinearLayout ceHua_ll2, LTJDAdapter ltjdAdapter2) {
		this.drawerLayout = drawerLayout2;
		this.ceHua_ll = ceHua_ll2;
		this.ltjdAdapter = ltjdAdapter2;
		// 点击侧滑菜单的条目
		ltjdAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				LogUtil.e("position:" + position);
				currentIndex = position;
				drawerLayout.closeDrawer(ceHua_ll);
				loadShenTiData();
			}
		});
	}

	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	protected void lazyLoad() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			initData();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initData() {

		// zheDang_tv.getParent().requestDisallowInterceptTouchEvent(true);
		last_iv.setOnClickListener(this);
		next_iv.setOnClickListener(this);
		first_1_tv.setOnClickListener(this);
		first_2_tv.setOnClickListener(this);
		first_3_tv.setOnClickListener(this);
		first_4_tv.setOnClickListener(this);
		first_5_tv.setOnClickListener(this);
		first_6_tv.setOnClickListener(this);
		first_7_tv.setOnClickListener(this);
		first_8_tv.setOnClickListener(this);
		first_9_tv.setOnClickListener(this);
		first_qx_tv.setOnClickListener(this);
		first_bc_tv.setOnClickListener(this);
		second_qx_tv.setOnClickListener(this);
		second_bc_tv.setOnClickListener(this);
		third_1_tv.setOnClickListener(this);
		third_2_tv.setOnClickListener(this);
		third_3_tv.setOnClickListener(this);
		third_4_tv.setOnClickListener(this);
		third_5_tv.setOnClickListener(this);
		third_6_tv.setOnClickListener(this);
		third_7_tv.setOnClickListener(this);
		third_8_tv.setOnClickListener(this);
		third_9_tv.setOnClickListener(this);
		third_0_tv.setOnClickListener(this);
		third_dian_tv.setOnClickListener(this);
		third_delete_iv.setOnClickListener(this);
		third_bc_tv.setOnClickListener(this);
		third_qx_tv.setOnClickListener(this);
		jieShu_tv.setOnClickListener(this);
		zheDang_tv.setOnClickListener(this);
		open_iv.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			switch (v.getId()) {
			// 点击上一个按钮
			case R.id.last_iv:
				if (!"".equals(currentInputValue)) {
					Toast.makeText(mContext, "请点击保存按钮", Toast.LENGTH_SHORT)
							.show();
					break;
				} else {
					if ("data".equals(currentTX.getCls())) {
						// 如果是出生日期,当前的测量值和本次的修改值一样,说明本次没有修改可以,进入下一个
						if (!"".equals(currentTX.getCeliangValue())
								&& !currentTX.getCeliangValue().equals(
										birthdayPicker.year + "-"
												+ birthdayPicker.month + "-"
												+ birthdayPicker.day)) {
							Toast.makeText(mContext, "请点击保存按钮",
									Toast.LENGTH_SHORT).show();
							break;
						}
					}
				}
				clickLast();
				break;
			// 点击下一个按钮
			case R.id.next_iv:
				if (!"".equals(currentInputValue)) {
					LogUtil.e("currentInputValue:" + currentInputValue);
					Toast.makeText(mContext, "请点击保存按钮", Toast.LENGTH_SHORT)
							.show();
					break;
				} else {
					if (!"".equals(currentTX.getCeliangValue())
							&& "data".equals(currentTX.getCls())) {
						// 如果是出生日期,当前的测量值和本次的修改值一样,说明本次没有修改可以,进入下一个
						if (!currentTX.getCeliangValue().equals(
								birthdayPicker.year + "-"
										+ birthdayPicker.month + "-"
										+ birthdayPicker.day)) {
							Toast.makeText(mContext, "请点击保存按钮",
									Toast.LENGTH_SHORT).show();
							break;
						}
					}
				}
				clickNext();
				break;
			// 点击选项A(男)
			case R.id.first_1_tv:
				setCurrentInputValue(0);
				break;
			// 点击选项B(女)
			case R.id.first_2_tv:
				setCurrentInputValue(1);
				break;
			// 点击选项C
			case R.id.first_3_tv:
				setCurrentInputValue(2);
				break;
			// 点击选项D
			case R.id.first_4_tv:
				setCurrentInputValue(3);
				break;
			// 点击选项E
			case R.id.first_5_tv:
				setCurrentInputValue(4);
				break;
			// 点击选项F
			case R.id.first_6_tv:
				setCurrentInputValue(5);
				break;
			// 点击选项G
			case R.id.first_7_tv:
				setCurrentInputValue(6);
				break;
			// 点击选项H
			case R.id.first_8_tv:
				setCurrentInputValue(7);
				break;
			// 点击选项I
			case R.id.first_9_tv:
				setCurrentInputValue(8);
				break;
			// 点击第一个取消(选择键盘的取消)
			case R.id.first_qx_tv:
				currentInputValue = "";
				currentContent_tv.setText("待测/" + currentTX.getValue());
				break;
			// 点击第一个保存(选择键盘的保存)
			case R.id.first_bc_tv:
				// 告诉activity,正在量体
				if (xiuGaiLTSJListener != null) {
					xiuGaiLTSJListener.onXiuGai(true);
				}
				if ("".equals(currentInputValue)) {
					Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				mLTSJList.get(currentIndex).setCeliangValue(currentInputValue);
				currentInputValue = "";
				clickNext();
				break;
			// 点击第二个取消(生日键盘的取消)
			case R.id.second_qx_tv:
				currentInputValue = "";
				currentContent_tv.setText("待测/" + currentTX.getValue());
				break;
			// 点击第二个保存(生日键盘的保存)
			case R.id.second_bc_tv:
				// 告诉activity,正在量体
				if (xiuGaiLTSJListener != null) {
					xiuGaiLTSJListener.onXiuGai(true);
				}
				currentInputValue = birthdayPicker.year + "-"
						+ birthdayPicker.month + "-" + birthdayPicker.day;
				if ("".equals(currentInputValue)) {
					currentContent_tv.setText(currentTX.getValue() + "/"
							+ currentTX.getValue());
					mLTSJList.get(currentIndex).setCeliangValue(
							currentTX.getValue());
				} else {
					currentContent_tv.setText(currentInputValue + "/"
							+ currentTX.getValue());
					mLTSJList.get(currentIndex).setCeliangValue(
							currentInputValue);
				}
				currentInputValue = "";
				clickNext();
				break;
			// 点击1
			case R.id.third_1_tv:
				setCurrentInputValue("1");
				break;
			// 点击2
			case R.id.third_2_tv:
				setCurrentInputValue("2");
				break;
			// 点击3
			case R.id.third_3_tv:
				setCurrentInputValue("3");
				break;
			// 点击4
			case R.id.third_4_tv:
				setCurrentInputValue("4");
				break;
			// 点击5
			case R.id.third_5_tv:
				setCurrentInputValue("5");
				break;
			// 点击6
			case R.id.third_6_tv:
				setCurrentInputValue("6");
				break;
			// 点击7
			case R.id.third_7_tv:
				setCurrentInputValue("7");
				break;
			// 点击8
			case R.id.third_8_tv:
				setCurrentInputValue("8");
				break;
			// 点击9
			case R.id.third_9_tv:
				setCurrentInputValue("9");
				break;
			// 点击点
			case R.id.third_dian_tv:
				setCurrentInputValue(".");
				break;
			// 点击0
			case R.id.third_0_tv:
				setCurrentInputValue("0");
				break;
			// 点击删除按钮
			case R.id.third_delete_iv:
				if (!"".equals(currentInputValue)) {

					currentInputValue = currentInputValue.substring(0,
							currentInputValue.length() - 1);
					currentContent_tv.setText(currentInputValue + "/"
							+ currentTX.getValue());
				}
				break;
			// 点击第三个取消按钮(数字键盘的取消按钮)
			case R.id.third_qx_tv:
				currentInputValue = "";
				currentContent_tv.setText("待测/" + currentTX.getValue());
				break;
			// 点击第三个保存按钮(数字键盘的保存按钮)
			case R.id.third_bc_tv:
				// 告诉activity,正在量体
				if (xiuGaiLTSJListener != null) {
					xiuGaiLTSJListener.onXiuGai(true);
				}
				if ("".equals(currentInputValue)) {
					Toast.makeText(mContext, "请输入参数", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				mLTSJList.get(currentIndex).setCeliangValue(currentInputValue);
				currentInputValue = "";
				clickNext();
				break;
			// 点击结束按钮
			case R.id.jieShu_tv:
				clickJieShu();

				break;
			// 点击遮挡
			case R.id.zheDang_tv:
				Toast.makeText(mContext, "您不是量体师,不能修改数据", Toast.LENGTH_SHORT)
						.show();
				break;
			// 点击打开侧滑菜单
			case R.id.open_iv:
				drawerLayout.openDrawer(ceHua_ll);
				break;

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 点击结束
	 */
	public void clickJieShu() {
		// TODO Auto-generated method stub
		if (!"".equals(currentInputValue)) {
			Toast.makeText(mContext, "请点击保存按钮", Toast.LENGTH_SHORT).show();
			return;
		} else if ("data".equals(currentTX.getCls())) {
			// 如果是出生日期,当前的测量值和本次的修改值一样,说明本次没有修改可以,进入下一个
			if (!"".equals(currentTX.getCeliangValue())
					&& !currentTX.getCeliangValue().equals(
							birthdayPicker.year + "-" + birthdayPicker.month
									+ "-" + birthdayPicker.day)) {
				Toast.makeText(mContext, "请点击保存按钮", Toast.LENGTH_SHORT).show();
				return;
			} else {

			}
		}

		String ltsj = "";
		for (int i = 0; i < mLTSJList.size(); i++) {
			LiangTiShuJu shuJu = mLTSJList.get(i);
			if (!"".equals(shuJu.getCeliangValue())) {
				ltsj += shuJu.getId() + ":" + shuJu.getName() + ":"
						+ shuJu.getCeliangValue() + "@";
			}
		}
		if ("".equals(ltsj)) {
			// 告诉activity,量体结束了
			if (xiuGaiLTSJListener != null) {
				xiuGaiLTSJListener.onXiuGai(false);
			}
			return;
		}
		pbDialog.show();
		ltsj = ltsj.substring(0, ltsj.length() - 1);
		// 上传数据
		LTSJExec.getInstance().upLoad(mHandler,
				ManagerUtils.getInstance().getUser_id(getActivity()),
				mUser.getId(), ltsj, NetworkAsyncCommonDefines.UPLOAD_LTSJ_S,
				NetworkAsyncCommonDefines.UPLOAD_LTSJ_F);

	}

	/**
	 * 数值键盘,当前的输入值
	 * 
	 * @param string
	 */
	private void setCurrentInputValue(String value) {
		// TODO Auto-generated method stub
		// 告诉activity,正在量体
		if (xiuGaiLTSJListener != null) {
			xiuGaiLTSJListener.onXiuGai(true);
		}
		currentInputValue += value;
		currentContent_tv.setText(currentInputValue + "/"
				+ currentTX.getValue());

	}

	private synchronized void setCurrentInputValue(final int i) {
		// 告诉activity,正在量体
		if (xiuGaiLTSJListener != null) {
			xiuGaiLTSJListener.onXiuGai(true);
		}
		// mHandler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		if (currentTX.getPrevalArr() != null
				&& currentTX.getPrevalArr().size() > i) {
			currentInputValue = currentTX.getPrevalArr().get(i);
			currentContent_tv.setText(currentInputValue + "/"
					+ currentTX.getValue());
		}
		// }
		// });

	}

	/**
	 * 点击上一个
	 */
	private void clickLast() {
		// TODO Auto-generated method stub
		// 当前体型是第二个
		if (currentIndex == 1) {
			currentIndex -= 1;
			getShenTiData(total - 1, currentIndex, currentIndex + 1);
		} else if (currentIndex == 0) {
			// 当前体型是第一个
			currentIndex = total - 1;
			getShenTiData(currentIndex - 1, currentIndex, 0);
		} else {
			// 当前体型是其他情况
			currentIndex -= 1;
			getShenTiData(currentIndex - 1, currentIndex, currentIndex + 1);
		}
		updateDataAndUI();
	}

	/**
	 * 点击下一个
	 */
	private void clickNext() {
		// TODO Auto-generated method stub
		// 当前体型是最后一个
		if (currentIndex == total - 1) {
			currentIndex = 0;
			getShenTiData(total - 1, currentIndex, currentIndex + 1);
		} else if (currentIndex == total - 2) {
			// 当前体型是倒数第二个
			currentIndex += 1;
			getShenTiData(currentIndex - 1, currentIndex, 0);
		} else {
			// 当前体型是其他情况
			currentIndex += 1;
			getShenTiData(currentIndex - 1, currentIndex, currentIndex + 1);
		}
		updateDataAndUI();
	}

	/**
	 * 更新侧滑菜单数据,选择器数据,提示数据,进度数据
	 */
	private void updateDataAndUI() {
		setShenTiData();
		setTiShiData();
		setJinDu();
		setJianPanXianShi();
		loadDrawerLayoutData();
	}

	/**
	 * 浏览的量体数据用户自己的或者是客户的
	 */
	private ArrayList<LiangTiShuJu> mLTSJList;

	/**
	 * 初始化量体师view
	 */
	public void initLiangTiShiView() {
		// TODO Auto-generated method stub

	}

	// 判断是否点击进入量体
	private boolean isJinRuLT = false;
	// 判断量体师的量体类型
	private boolean isLTziji = false;

	@SuppressLint("ResourceAsColor")
	/**
	 * 当前输入的参数
	 */
	private String currentInputCS = "";
	private int shuruIndex = 0;
	private boolean isshurudian = false, isshuruyiwei = false;

	private int lenth;

	/**
	 * 身体数据的总数
	 */
	private int total;
	/**
	 * 当前身体数据所在的索引
	 */
	private int currentIndex = 0;

	/**
	 * 当前体型
	 */
	private LiangTiShuJu currentTX;
	/**
	 * 上个体型
	 */
	private LiangTiShuJu lastTX;
	/**
	 * 下个体型
	 */
	private LiangTiShuJu nextTX;
	@SuppressLint("ResourceAsColor")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			// 上传量体数据成功
			case NetworkAsyncCommonDefines.UPLOAD_LTSJ_S:
				Toast.makeText(getActivity(), "量体结束", Toast.LENGTH_SHORT)
						.show();
				if (mLTSJList != null && mLTSJList.size() > 0) {

					for (int i = 0; i < mLTSJList.size(); i++) {
						LiangTiShuJu ltsj = mLTSJList.get(i);
						if ("性别".equals(ltsj.getName())
								&& !"".equals(ltsj.getCeliangValue())) {
							mSharedPreferenceUtils.setSex(getActivity(),
									ltsj.getCeliangValue());
						}
						if ("出生日期".equals(ltsj.getName())
								&& !"".equals(ltsj.getCeliangValue())) {
							mSharedPreferenceUtils.setBirthday(getActivity(),
									ltsj.getCeliangValue());
						}
						if ("身高".equals(ltsj.getName())
								&& !"".equals(ltsj.getCeliangValue())) {
							mSharedPreferenceUtils.setHeight(getActivity(),
									ltsj.getCeliangValue());
						}
						if ("体重".equals(ltsj.getName())
								&& !"".equals(ltsj.getCeliangValue())) {
							mSharedPreferenceUtils.setWeight(getActivity(),
									ltsj.getCeliangValue());
						}
					}
				}
				// 获取量体数据列表
				mLTSJList.clear();
				currentIndex = 0;
				ltAll_ll.setVisibility(View.GONE);
				zheDang_tv.setVisibility(View.GONE);
				user = null;
				pbDialog.dismiss();
				// 告诉activity,量体结束了
				if (xiuGaiLTSJListener != null) {
					xiuGaiLTSJListener.onXiuGai(false);
				}
				break;
			// 上传量体数据失败
			case NetworkAsyncCommonDefines.UPLOAD_LTSJ_F:
				Toast.makeText(getActivity(), "上传量体数据失败", Toast.LENGTH_SHORT)
						.show();
				pbDialog.dismiss();
				break;

			}
		};
	};

	private User mUser;

	/**
	 * 1.在验证手机号对话框中点击确定按钮 2.验证手机号,及获取手机号对应的用户的基本信息
	 * 3.调用此方法,设置被量体用户的基本信息(头像,昵称,手机号)
	 * 
	 * @param mUser
	 */
	public void setUserBaseInfo(User mUser) {
		// TODO Auto-generated method stub
		this.mUser = mUser;
		// 显示量体界面的View
		setShowView(true);
		if (mUser != null) {
			// 显示客户的基本信息的view
			user_ll.setVisibility(View.VISIBLE);
			// 设置用户头像
			x.image().bind(userHead_iv, mUser.getAvatar(), mImageOptionHead);
			// 设置用户手机号
			userPhone_tv.setText(mUser.getPhone());
			// 设置用户昵称
			userName_tv.setText(mUser.getName());
		}
	}

	/**
	 * 设置量体界面view是否显示
	 * 
	 * @param boo
	 */
	public void setShowView(boolean boo) {
		if (boo) {
			// 显示量体界面的view
			ltAll_ll.setVisibility(View.VISIBLE);
		} else {
			// 隐藏量体界面的view
			ltAll_ll.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置其他被量体人的量体数据信息(或者此手机号用户是量体师自己)
	 * 
	 * @param beiLiangTiRenData
	 */
	public void setBeiLiangTiData(ArrayList<LiangTiShuJu> beiLiangTiRenData) {
		// TODO Auto-generated method stub
		isJinRuLT = true;
		mLTSJList = beiLiangTiRenData;
		currentIndex = 0;
		// if (total == 0) {
		total = mLTSJList.size();
		// }
		setViewShow(true);
		loadShenTiData();
	}

	/**
	 * 设置身体选择器对应的身体数据,设置对应的提示信息,设置对应的键盘,设置进度
	 */
	private void loadShenTiData() {
		// TODO Auto-generated method stub
		getShenTiData(total - 1, currentIndex, currentIndex + 1);
		updateDataAndUI();
	}

	/**
	 * 设置进度
	 */
	private void setJinDu() {
		// TODO Auto-generated method stub
		jinDu_tv.setText("<" + (currentIndex + 1) + "/" + total + ">");
	}

	/**
	 * 选择键盘
	 */
	@ViewInject(R.id.optionJP)
	private View optionJP;
	/**
	 * 生日键盘
	 */
	@ViewInject(R.id.dateJP)
	private View dateJP;
	/**
	 * 数字键盘
	 */
	@ViewInject(R.id.valueJP)
	private View valueJP;

	/**
	 * 设置显示相应的键盘
	 */
	private void setJianPanXianShi() {
		// TODO Auto-generated method stub
		switch (currentTX.getCls()) {
		// 选择键盘
		case "option":
			optionJP.setVisibility(View.VISIBLE);
			dateJP.setVisibility(View.GONE);
			valueJP.setVisibility(View.GONE);
			setOptionJPValue();
			break;
		// 生日键盘
		case "data":
			optionJP.setVisibility(View.GONE);
			dateJP.setVisibility(View.VISIBLE);
			valueJP.setVisibility(View.GONE);
			setDateJPValue();
			break;
		// 数字键盘
		case "value":
			optionJP.setVisibility(View.GONE);
			dateJP.setVisibility(View.GONE);
			valueJP.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置生日键盘的值
	 */
	private void setDateJPValue() {
		// TODO Auto-generated method stub
		if (!"".equals(currentTX.getCeliangValue())) {
			birthdayPicker.setData(currentTX.getCeliangValue());
			year = birthdayPicker.year;
			month = birthdayPicker.month;
			day = birthdayPicker.day;
		} else if (!"".equals(currentTX.getValue())) {
			birthdayPicker.setData(currentTX.getValue());
			year = birthdayPicker.year;
			month = birthdayPicker.month;
			day = birthdayPicker.day;
		}
	}

	/**
	 * 选项A
	 */
	@ViewInject(R.id.first_1_tv)
	private TextView first_1_tv;
	/**
	 * 选项B
	 */
	@ViewInject(R.id.first_2_tv)
	private TextView first_2_tv;
	/**
	 * 选项C
	 */
	@ViewInject(R.id.first_3_tv)
	private TextView first_3_tv;
	/**
	 * 选项D
	 */
	@ViewInject(R.id.first_4_tv)
	private TextView first_4_tv;
	/**
	 * 选项E
	 */
	@ViewInject(R.id.first_5_tv)
	private TextView first_5_tv;
	/**
	 * 选项F
	 */
	@ViewInject(R.id.first_6_tv)
	private TextView first_6_tv;
	/**
	 * 选项G
	 */
	@ViewInject(R.id.first_7_tv)
	private TextView first_7_tv;
	/**
	 * 选项H
	 */
	@ViewInject(R.id.first_8_tv)
	private TextView first_8_tv;
	/**
	 * 选项I
	 */
	@ViewInject(R.id.first_9_tv)
	private TextView first_9_tv;

	/**
	 * 设置选择键盘的选项
	 */
	private void setOptionJPValue() {
		// TODO Auto-generated method stub
		if (currentTX.getPrevalArr() != null) {
			// 设置对应值
			if (currentTX.getPrevalArr().size() > 0) {
				first_1_tv.setText(currentTX.getPrevalArr().get(0));
				if (currentTX.getPrevalArr().size() > 1) {
					first_2_tv.setText(currentTX.getPrevalArr().get(1));
					if (currentTX.getPrevalArr().size() > 2) {
						first_3_tv.setText(currentTX.getPrevalArr().get(2));
						if (currentTX.getPrevalArr().size() > 3) {
							first_4_tv.setText(currentTX.getPrevalArr().get(3));
							if (currentTX.getPrevalArr().size() > 4) {
								first_5_tv.setText(currentTX.getPrevalArr()
										.get(4));
								if (currentTX.getPrevalArr().size() > 5) {
									first_6_tv.setText(currentTX.getPrevalArr()
											.get(5));
									if (currentTX.getPrevalArr().size() > 6) {
										first_7_tv.setText(currentTX
												.getPrevalArr().get(6));
										if (currentTX.getPrevalArr().size() > 7) {
											first_8_tv.setText(currentTX
													.getPrevalArr().get(7));
											if (currentTX.getPrevalArr().size() > 8) {
												first_9_tv.setText(currentTX
														.getPrevalArr().get(8));
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// 清除对应值
			if (currentTX.getPrevalArr().size() < 9) {
				first_9_tv.setText("");
				if (currentTX.getPrevalArr().size() < 8) {
					first_8_tv.setText("");
					if (currentTX.getPrevalArr().size() < 7) {
						first_7_tv.setText("");
						if (currentTX.getPrevalArr().size() < 6) {
							first_6_tv.setText("");
							if (currentTX.getPrevalArr().size() < 5) {
								first_5_tv.setText("");
								if (currentTX.getPrevalArr().size() < 4) {
									first_4_tv.setText("");
									if (currentTX.getPrevalArr().size() < 3) {
										first_3_tv.setText("");
										if (currentTX.getPrevalArr().size() < 2) {
											first_2_tv.setText("");
											if (currentTX.getPrevalArr().size() < 1) {
												first_1_tv.setText("");

											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 设置身体数据的数据
	 */
	private void setShenTiData() {
		// TODO Auto-generated method stub
		// 设置上一个身体数据的标题
		lastTitle_tv.setText(lastTX.getName());
		if (!"".equals(lastTX.getCeliangValue())) {
			// 上一个身体数据测量过
			lastContent_tv.setText(lastTX.getCeliangValue() + "/"
					+ lastTX.getValue());
		} else {
			// 上一个身体数据没有测量过
			lastContent_tv.setText("待测" + "/" + lastTX.getValue());
		}
		// 设置上一个身体数据的单位
		lastDanWei_tv.setText(lastTX.getUnit());

		// 设置当前身体数据的标题
		currentTitle_tv.setText(currentTX.getName());
		if (!"".equals(currentTX.getCeliangValue())) {
			// 当前身体数据测量过
			currentContent_tv.setText(currentTX.getCeliangValue() + "/"
					+ currentTX.getValue());
		} else {
			// 当前身体数据没有测量过
			currentContent_tv.setText("待测" + "/" + currentTX.getValue());
		}
		// 设置当前身体数据的单位
		currentDanWei_tv.setText(currentTX.getUnit());

		// 设置下一个身体数据的标题
		nextTitle_tv.setText(nextTX.getName());
		if (!"".equals(nextTX.getCeliangValue())) {
			// 下一个身体数据测量过
			nextContent_tv.setText(nextTX.getCeliangValue() + "/"
					+ nextTX.getValue());
		} else {
			// 下一个身体数据没有测量过
			nextContent_tv.setText("待测" + "/" + nextTX.getValue());
		}
		// 设置下一个身体数据的单位
		nextDanWei_tv.setText(nextTX.getUnit());

	}

	/**
	 * 设置每个身体部位的提示信息(图片和文字)
	 */
	private void setTiShiData() {
		// TODO Auto-generated method stub
		if ("".equals(currentTX.getPic())) {
			x.image().bind(tiShiTu_iv, null, mImageOptions);
		} else {
			x.image()
					.bind(tiShiTu_iv,
							PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ currentTX.getPic(), mImageOptions);
		}
		tiShi_tv.setText(currentTX.getDescr());
	}

	/**
	 * 获取选择前(点击上一个,下一个按钮前)的数据
	 * 
	 * @param j
	 * @param currentIndex2
	 * @param i
	 */
	private void getShenTiData(int lastIndex, int currentIndex, int nextIndex) {
		// TODO Auto-generated method stub
		LogUtil.e("lastIndex:" + lastIndex + " currentIndex:" + currentIndex
				+ " nextIndex:" + nextIndex);
		currentTX = mLTSJList.get(currentIndex);
		nextTX = mLTSJList.get(nextIndex);
		lastTX = mLTSJList.get(lastIndex);
	}

	/**
	 * 设置显示的View
	 * 
	 * @param b
	 */
	private void setViewShow(boolean b) {
		// TODO Auto-generated method stub
		if (b) {
			// 遮挡区域显示
			zheDang_tv.setVisibility(View.GONE);
			// 显示打开侧滑菜单按钮
			open_iv.setVisibility(View.VISIBLE);
			// 显示结束按钮
			jieShu_tv.setVisibility(View.VISIBLE);
		} else {
			// 隐藏遮挡区域
			zheDang_tv.setVisibility(View.VISIBLE);
			// 隐藏侧滑菜单按钮
			open_iv.setVisibility(View.GONE);
			// 隐藏结束按钮
			jieShu_tv.setVisibility(View.GONE);
		}
	}

	/**
	 * 加载侧滑菜单,给他设置量体数据
	 */
	private void loadDrawerLayoutData() {
		// TODO Auto-generated method stub
		if (ltjdAdapter == null) {
			ltjdAdapter = new LTJDAdapter(getActivity());
		}
		ltjdAdapter.setCurrentIndex(currentIndex);
		ltjdAdapter.setData(mLTSJList);
		ltjdAdapter.notifyDataSetChanged();
	}

	private XiuGaiLTSJListener xiuGaiLTSJListener;

	public void setXiuGaiLTSJListener(XiuGaiLTSJListener xiuGaiLTSJListener) {
		this.xiuGaiLTSJListener = xiuGaiLTSJListener;
	}

	/**
	 * @description: 修改量体数据监听
	 * @author:ydh
	 * @data:2016-4-8上午11:56:38
	 */
	public interface XiuGaiLTSJListener {
		void onXiuGai(boolean b);
	}

	/**
	 * 如果用户不是量体师直接进入此方法,不弹出手机号验证对话框
	 * 
	 * @param wdLTSJList
	 */
	public void initFeiLiangTiShiView(ArrayList<LiangTiShuJu> wdLTSJList) {
		// TODO Auto-generated method stub
		mLTSJList = wdLTSJList;
		if (total == 0) {
			total = mLTSJList.size();
		}
		setViewShow(false);
		loadShenTiData();
	}
}
