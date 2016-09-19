package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.LTJDAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.OrdinaryCommonDefines;
import cn.dressbook.ui.dialog.ProgressDialog;
import cn.dressbook.ui.fragment.LiangTiFragment;
import cn.dressbook.ui.fragment.LiangTiFragment.XiuGaiLTSJListener;
import cn.dressbook.ui.fragment.WoDeShuJuFragment;
import cn.dressbook.ui.fragment.WoDeShuJuFragment.RefreshLTS;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.User;
import cn.dressbook.ui.net.LTSJExec;
import cn.dressbook.ui.net.SMSExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.ToastUtils;
import cn.dressbook.ui.viewpager.RollingControlViewPager;

/**
 * @description: 诊断量体
 * @author:ydh
 * @data:2016-4-8上午9:34:56
 */
@ContentView(R.layout.activity_ltsj)
public class ZhenDuanLTActivity extends AppCompatActivity {
	private Activity activity = ZhenDuanLTActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	public ProgressDialog pbDialog;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 侧滑菜单的View
	 */
	@ViewInject(R.id.drawerLayout)
	private DrawerLayout drawerLayout;
	/**
	 * 侧滑菜单左侧的view
	 */
	@ViewInject(R.id.ceHua_ll)
	private LinearLayout ceHua_ll;
	/**
	 * 侧滑菜单的量体数据目录的RecyclerView
	 */
	@ViewInject(R.id.ltMenuRecyclerView)
	private RecyclerView ltMenuRecyclerView;
	/**
	 * 侧滑菜单的适配器
	 */
	private LTJDAdapter ltjdAdapter;

	private boolean isLts = false, isLtkh = false, isLting = false;
	/**
	 * 盛放两个界面的ViewPager的适配器
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	/**
	 * 盛放两个界面的ViewPager
	 */
	@ViewInject(R.id.container)
	private RollingControlViewPager mViewPager;
	/**
	 * 两个界面的标题
	 */
	@ViewInject(R.id.tabs)
	private TabLayout tabLayout;
	/**
	 * 我的数据界面
	 */
	private WoDeShuJuFragment woDeShuJuFragment;
	/**
	 * 量体界面
	 */
	private LiangTiFragment liangTiFragment;
	/**
	 * 量体师的名字
	 */
	@ViewInject(R.id.lts_tv)
	private TextView lts_tv;
	/**
	 * 量体时间
	 */
	@ViewInject(R.id.ltsj_tv)
	private TextView ltsj_tv;
	/**
	 * 量体师信息展示的View
	 */
	@ViewInject(R.id.lts_rl)
	private RelativeLayout lts_rl;
	/**
	 * 是否是量体师
	 */
	private boolean isLTS = false;
	/**
	 * 用户是否修改量体数据
	 */
	private boolean isXiuGai = false;
	/**
	 * 发完验证码后是否修改了手机号
	 */
	private boolean isXiuGaiSJH = false;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(activity);
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		liangTiFragment.setXiuGaiLTSJListener(new XiuGaiLTSJListener() {

			@Override
			public void onXiuGai(boolean b) {
				// TODO Auto-generated method stub
				isXiuGai = b;
				mViewPager.setCanScroll(!isXiuGai);
				if (!isXiuGai) {
					mViewPager.setCurrentItem(0);
					woDeShuJuFragment.initData();
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	protected void initView() {
		pbDialog = createProgressBar(activity);
		ltMenuRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
		ltjdAdapter = new LTJDAdapter(activity);
		ltMenuRecyclerView.setAdapter(ltjdAdapter);
		ltMenuRecyclerView.setHasFixedSize(true);
		ltMenuRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
		// 添加分割线
		ltMenuRecyclerView
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						activity)
						.color(getResources().getColor(R.color.zhuye_bg))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider5)).margin(0, 0).build());
		woDeShuJuFragment = new WoDeShuJuFragment();
		liangTiFragment = new LiangTiFragment(drawerLayout, ceHua_ll,
				ltjdAdapter);
		// 设置量体师
		woDeShuJuFragment.setRefreshLTS(new RefreshLTS() {

			@Override
			public void onRefresh(String lastOper, String lastOpTime) {
				// TODO Auto-generated method stub
				lts_tv.setText("量体师:" + lastOper);
				ltsj_tv.setText("量体时间:" + lastOpTime);
			}
		});
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager.setAdapter(mSectionsPagerAdapter);

		tabLayout.setupWithViewPager(mViewPager);
		// 滑动界面
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					showLTSView(true);
					liangTiFragment.setShowView(false);
					openDrawerLayout(false);
				} else {
					showLTSView(false);
					clickLiangTi();
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				// panDuanShiFouXiuGai(isXiuGai);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {

			@Override
			public void onTabUnselected(Tab arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					// 如果没有修改量体数据,可以切换界面
					if (!isXiuGai) {
						mViewPager.setCurrentItem(arg0.getPosition());
					} else {
						// 点击了我的数据的tab
						if (arg0.getPosition() == 0) {
							panDuanShiFouXiuGai(isXiuGai);
						} else {

						}

					}
				} else {

				}
			}

			@Override
			public void onTabReselected(Tab arg0) {
				// TODO Auto-generated method stub

			}
		});

		openDrawerLayout(false);
		drawerLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// LogUtil.e("onTouch-------------");
				// if (!(isLting && isLtkh && isLting)) {
				// drawerLayout.closeDrawer(ceHua_ll);
				// }
				return false;
			}
		});
	}

	/**
	 * 判断用户是否修改量体数据,处理是否弹出保存数据对话框
	 * 
	 * @param b
	 */
	private void panDuanShiFouXiuGai(boolean b) {
		isXiuGai = b;
		// 修改了数据,保存后才能滑动到我的数据界面
		if (isXiuGai) {
			tanChuSaveDialog();
		} else {
			// 没有修改数据,可以直接滑动到我的数据界面
		}
	};

	/**
	 * 弹出保存量体数据对话框
	 */
	private void tanChuSaveDialog() {
		// TODO Auto-generated method stub
		if (saveDialog == null) {
			initSaveDialog();
		}
		saveDialog.show();
	}

	/**
	 * 保存量体数据提示对话框
	 */
	private AlertDialog saveDialog;
	/**
	 * 保存量体数据提示对话框的view
	 */
	private View saveDialogLayout;

	/**
	 * 初始化保存量体数据对话框
	 */
	private void initSaveDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		saveDialogLayout = View.inflate(activity, R.layout.save_ltsj_dialog,
				null);
		builder.setView(saveDialogLayout).setCancelable(false);
		saveDialog = builder.create();
		initSaveDialogView();
	}

	/**
	 * 初始化保存量体数据对话框的控件
	 */
	private void initSaveDialogView() {
		// TODO Auto-generated method stub
		// 点击取消
		saveDialogLayout.findViewById(R.id.tv_cancle).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						saveDialog.dismiss();
						tabLayout.getTabAt(1).select();
					}
				});
		// 点击确定
		saveDialogLayout.findViewById(R.id.tv_sure).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						liangTiFragment.clickJieShu();
						saveDialog.dismiss();
					}
				});
	}

	/**
	 * 是否显示量体师和量体时间的view
	 * 
	 * @param b
	 */
	protected void showLTSView(boolean b) {
		// TODO Auto-generated method stub
		if (b) {
			// 展示量体师信息
			lts_rl.setVisibility(View.VISIBLE);
		} else {
			// 隐藏量体师信息
			lts_rl.setVisibility(View.GONE);
		}

	}

	/**
	 * 是否打开侧滑功能
	 * 
	 * @param b
	 */
	private void openDrawerLayout(boolean b) {
		// TODO Auto-generated method stub
		if (b) {
			// 打开侧滑功能
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		} else {
			// 关闭侧滑功能
			drawerLayout
					.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		}

	}

	/**
	 * 点击量体
	 */
	protected void clickLiangTi() {
		// TODO Auto-generated method stub
		// 如果我是量体师,进入这里
		if (isLTS) {
			// 弹出被量体人电话验证对话框
			TanChuDialog();
		} else {
			openDrawerLayout(false);
			mUser = new User();
			mUser.setId(ManagerUtils.getInstance().getUser_id(activity));
			mUser.setName(ManagerUtils.getInstance().getUserName(activity));
			mUser.setPhone(ManagerUtils.getInstance().getPhoneNum(activity));
			mUser.setAvatar(ManagerUtils.getInstance().getUserHead(activity));
			// 设置客户头像,昵称,手机号
			liangTiFragment.setUserBaseInfo(mUser);
			// 如果我不是量体师,进入这里
			liangTiFragment.initFeiLiangTiShiView(woDeShuJuFragment
					.getWdLTSJList());
		}
	}

	/**
	 * 弹出对话框
	 */
	private void TanChuDialog() {
		// TODO Auto-generated method stub
		if (phoneDialog == null) {
			initDialog();
		} else {
			et_number.setText("");
			et_yzm.setText("");
		}
		phoneDialog.show();
	}

	private AlertDialog phoneDialog;
	private View phoneDialogLayout;

	/**
	 * 初始化被量体人电话验证对话框
	 */
	private void initDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		phoneDialogLayout = View.inflate(activity, R.layout.item_dialog_lt,
				null);
		builder.setView(phoneDialogLayout).setCancelable(false);
		phoneDialog = builder.create();
		initPhoneDialogView();

	}

	/**
	 * 获取验证码按钮
	 */
	private TextView tv_hqyzm;
	/**
	 * 获取验证码的时间
	 */
	private int time = 60;
	/**
	 * 获取的被量体人的手机号
	 */
	private String mPhoneNumber;
	private EditText et_number;
	private EditText et_yzm;

	/**
	 * 初始化被量体人电话验证对话框的view
	 */
	private void initPhoneDialogView() {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView) phoneDialogLayout
				.findViewById(R.id.tv_title);
		tvTitle.setText("量体客户");
		et_number = (EditText) phoneDialogLayout.findViewById(R.id.et_number);
		et_yzm = (EditText) phoneDialogLayout.findViewById(R.id.et_yzm);
		tv_hqyzm = (TextView) phoneDialogLayout.findViewById(R.id.tv_hqyzm);
		et_number.setText("");
		et_yzm.setText("");
		time = 0;
		updateClockUI();
		// 监听手机号变化
		et_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				tv_hqyzm.setEnabled(true);
				if ("".equals(s.toString()) || s.toString().length() != 11) {
					isXiuGaiSJH = true;
					time = 0;
					updateClockUI();
				} else if (s.toString().length() == 11
						&& s.toString().equals(mPhoneNumber)) {
					isXiuGaiSJH = false;
				}
			}
		});
		// 点击取消
		phoneDialogLayout.findViewById(R.id.tv_cancle).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						phoneDialog.dismiss();
						// 点击取消,返回到我的数据界面
						mViewPager.setCurrentItem(0);
					}
				});
		// 点击确定
		phoneDialogLayout.findViewById(R.id.tv_sure).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						mPhoneNumber = et_number.getText().toString();
						String mYZM = et_yzm.getText().toString();
						if (mPhoneNumber != null) {

							mPhoneNumber = mPhoneNumber.trim().replaceAll(
									"\\s", "");
							mPhoneNumber = mPhoneNumber.replaceAll("\t", "");
							mPhoneNumber = mPhoneNumber.replaceAll("\r", "");
							mPhoneNumber = mPhoneNumber.replaceAll("\n", "");
						}
						if (mYZM != null) {
							mYZM = mYZM.trim().replaceAll("\\s", "");
							mYZM = mYZM.replaceAll("\t", "");
							mYZM = mYZM.replaceAll("\r", "");
							mYZM = mYZM.replaceAll("\n", "");
						}
						if (mPhoneNumber == null || "".equals(mPhoneNumber)) {
							Toast.makeText(activity, "请输入手机号",
									Toast.LENGTH_SHORT).show();
						} else if (mPhoneNumber.length() != 11) {
							Toast.makeText(activity, "请输入正确的手机号",
									Toast.LENGTH_SHORT).show();
						} else if (mYZM == null || "".equals(mYZM)) {
							Toast.makeText(activity, "请输入验证码",
									Toast.LENGTH_SHORT).show();
						} else if (!mYZM.equals(mSharedPreferenceUtils
								.getYZM(activity))
								&& !mYZM.equals(OrdinaryCommonDefines.MOREN_YZM)) {
							ToastUtils.getInstance().showToast(activity,
									"输入的验证码不正确", 200);
						} else if (!mYZM
								.equals(OrdinaryCommonDefines.MOREN_YZM)
								&& isXiuGaiSJH) {
							Toast.makeText(activity, "请获取验证码",
									Toast.LENGTH_SHORT).show();
						} else {
							pbDialog.show();
							// 获取被量体人的量体数据
							// 点击了确定按钮
							pbDialog.show();
							// 设置客户头像,昵称,手机号
							// 获取被量体人的量体数据,验证被量体人是否存在,验证量体师是否有资格创建用户,创建用户
							LTSJExec.getInstance().getLTSJList(
									mHandler,
									mPhoneNumber,
									ManagerUtils.getInstance().getUser_id(
											activity),
									NetworkAsyncCommonDefines.GET_LTSJ_S_KH,
									NetworkAsyncCommonDefines.GET_LTSJ_F_KH);

						}
					}
				});
		// 点击获取验证码
		tv_hqyzm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPhoneNumber = et_number.getText().toString();
				if (mPhoneNumber == null || "".equals(mPhoneNumber)) {
					Toast.makeText(activity, "请输入手机号", Toast.LENGTH_SHORT)
							.show();
				} else if (mPhoneNumber.length() != 11) {
					Toast.makeText(activity, "请输入正确的手机号", Toast.LENGTH_SHORT)
							.show();
				} else {
					gettingYZM = true;
					// 发送验证码
					// 正在获取验证码
					if (gettingYZM) {
						pbDialog.show();
						sj_yzm = getCheckNum();
						mSharedPreferenceUtils.setYZM(activity, sj_yzm);
						isXiuGaiSJH = false;
						// 发送验证码
						SMSExec.getInstance().faSongYanZhengMa(mHandler,
								mPhoneNumber, sj_yzm,
								NetworkAsyncCommonDefines.FASONGYANZHENGMA_S,
								NetworkAsyncCommonDefines.FASONGYANZHENGMA_F);
					}

				}
			}
		});
	}

	/**
	 * 正在获取验证码
	 */
	private boolean gettingYZM;
	/**
	 * 时间是否结束
	 */
	private boolean paused = false;

	/**
	 * 更新获取验证码按钮的UI效果
	 */
	private void updateClockUI() {
		// TODO Auto-generated method stub
		tv_hqyzm.setText(time + "秒");
		if (time == 0) {
			paused = true;
			tv_hqyzm.setBackgroundResource(R.drawable.diysure);
			tv_hqyzm.setText("点击获取");
			tv_hqyzm.setEnabled(true);
		}
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			if (isXiuGai) {
				panDuanShiFouXiuGai(isXiuGai);
			} else {
				finish();
			}
			break;

		default:
			break;
		}
	}

	public void setLts(boolean isLts) {
		this.isLts = isLts;
	}

	public void setLtkh(boolean isLtkh) {
		this.isLtkh = isLtkh;
	}

	public void setLting(boolean isLting) {
		this.isLting = isLting;
	}

	protected void initData() {
		try {

			title_tv.setText("诊断量体");
			if (ManagerUtils.getInstance().getShenFen(activity)
					.contains("@量体师@")) {
				isLTS = true;
			} else {
				isLTS = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				// 我的数据界面
				return woDeShuJuFragment;
			} else {
				// 量体界面
				return liangTiFragment;
			}

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "我的数据";
			case 1:
				return "量体";
			}
			return null;
		}
	}

	public ProgressDialog createProgressBar(Activity activity) {
		if (pbDialog == null) {
			pbDialog = new ProgressDialog(activity);
		}

		return pbDialog;
	}

	/**
	 * 根据手机号获取的用户信息
	 */
	private User mUser;
	/**
	 * 生成的随机验证码
	 */
	private String sj_yzm = "";

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	public static String getCheckNum() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append((int) (Math.random() * 10));
		}
		String YZM = sb.toString();
		return YZM;
	}

	private Handler uiHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (!paused) {
					paused = false;
					time--;
					updateClockUI();
					uiHandler.sendEmptyMessageDelayed(1, 1000);
				} else {
					paused = true;
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
			// 发送验证码成功
			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_S:
				gettingYZM = false;
				pbDialog.dismiss();
				paused = false;
				time = 180;
				tv_hqyzm.setBackgroundResource(R.drawable.diycancle);
				tv_hqyzm.setEnabled(false);
				uiHandler.sendEmptyMessageDelayed(1, 1000);
				updateClockUI();
				break;
			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_F:
				gettingYZM = false;
				pbDialog.dismiss();
				Toast.makeText(activity, "请求发送失败，请重新发送", Toast.LENGTH_SHORT)
						.show();
				break;
			// 获取被量体人的量体数据成功
			case NetworkAsyncCommonDefines.GET_LTSJ_S_KH:
				Bundle data = msg.getData();
				if (data != null) {

					ArrayList<LiangTiShuJu> beiLiangTiRenData = data
							.getParcelableArrayList("ltsj");
					mUser = data.getParcelable("user");
					liangTiFragment.setUserBaseInfo(mUser);
					liangTiFragment.setBeiLiangTiData(beiLiangTiRenData);
					openDrawerLayout(true);
					pbDialog.dismiss();
					phoneDialog.dismiss();
				}
				break;
			// 获取被量体人的量体数据失败
			case NetworkAsyncCommonDefines.GET_LTSJ_F_KH:
				phoneDialog.dismiss();
				// 返回到我得数据界面
				break;

			default:
				break;
			}
		}

	};

}
