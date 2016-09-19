package cn.dressbook.ui.fragment;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.CutHeadActivity;
import cn.dressbook.ui.DJSQActivity;
import cn.dressbook.ui.DianJiaGuanLiActivity;
import cn.dressbook.ui.JiChuZiLiaoActivity;
import cn.dressbook.ui.SystemMessageActivity;
import cn.dressbook.ui.ZhenDuanLTActivity;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.ZhangHaoGuanLiActivity;
import cn.dressbook.ui.WoDeDingDanActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.SetImageActivity;
import cn.dressbook.ui.SettingActivity;
import cn.dressbook.ui.YJTCActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.dialog.EWMDialog;
import cn.dressbook.ui.model.Wardrobe;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.WardrobeExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.sweetalert.SweetAlertDialog;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.UriUtils;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description:个人中心界面
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月16日 下午4:01:02
 */
@ContentView(R.layout.my_fragmen_layout)
public class MyFragment extends BaseFragment {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	/**
	 * 未读消息
	 */
	@ViewInject(R.id.xxzx_tv)
	private TextView xxzx_tv;
	/** 昵称的tv */
	@ViewInject(R.id.name_tv)
	private TextView name_tv;
	/** 手机号的tv */
	@ViewInject(R.id.phone_tv)
	private TextView phone_tv;
	/** 头像 */
	@ViewInject(R.id.circleimageview)
	private CircleImageView circleimageview;
	/** 我的订单的rl */
	@ViewInject(R.id.ll_ddzx)
	private RelativeLayout ll_ddzx;
	/** 资料的rl */
	@ViewInject(R.id.zl_rl)
	private RelativeLayout zl_rl;
	/** 诊断的rl */
	@ViewInject(R.id.ll_zdlt)
	private RelativeLayout ll_zdlt;
	/** 形象的rl */
	@ViewInject(R.id.xx_rl)
	private RelativeLayout xx_rl;
	/** 业绩提成 */
	@ViewInject(R.id.ll_yjtc)
	LinearLayout ll_yjtc;
	/** 设置的rl */
	@ViewInject(R.id.sz_rl)
	private RelativeLayout sz_rl;
	/**
	 * 店家管理
	 */
	@ViewInject(R.id.djgl_rl)
	private RelativeLayout djgl_rl;
	/**
	 * 签到
	 */
	@ViewInject(R.id.signin_tv)
	private TextView signin_tv;
	/**
	 * 签到增加的衣扣
	 */
	@ViewInject(R.id.signin_value)
	private TextView signin_value;
	/**
	 * 今日日期
	 */
	private String qdrq;
	private EWMDialog mEWMDialog;

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		createProgressBar(mContext);
		initView();
		initData();
		if (ManagerUtils.getInstance().isLogin(mContext)) {

			WardrobeExec.getInstance().getWardrobe(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					NetworkAsyncCommonDefines.GET_WARDROBE_S,
					NetworkAsyncCommonDefines.GET_WARDROBE_F);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 * 
	 * @Description TODO
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 有未读消息
		if (ManagerUtils.getInstance().getXxSize(getActivity()) > 0) {
			LogUtil.e("显示:"
					+ ManagerUtils.getInstance().getXxSize(getActivity()));
			xxzx_tv.setVisibility(View.VISIBLE);
			xxzx_tv.setText(ManagerUtils.getInstance().getXxSize(getActivity())
					+ "");
		} else {
			LogUtil.e("隐藏-----------------");
			// 没有未读消息
			xxzx_tv.setVisibility(View.GONE);
		}
		// SimpleDateFormat format = new SimpleDateFormat("MMdd",
		// Locale.getDefault());
		// Date date = new Date();
		// qdrq = format.format(date);
		// String qdrq2 = mSharedPreferenceUtils.getQianDaoRiQi(mContext);
		// String qdid = mSharedPreferenceUtils.getQianDaoId(mContext);
		// if (ManagerUtils.getInstance().getUser_id(mContext) != null) {
		// if (qdrq.equals(qdrq2)
		// && ManagerUtils.getInstance().getUser_id(mContext)
		// .equals(qdid)) {
		// // 已签到
		// signin_tv.setBackgroundResource(R.drawable.textview_bg_10);
		// signin_tv.setText("已签到");
		// signin_tv.setPadding(20, 6, 20, 6);
		// } else {
		// // 未签到
		// signin_tv.setBackgroundResource(R.drawable.textview_bg_1);
		// signin_tv.setText("签到");
		// signin_tv.setPadding(20, 6, 20, 6);
		// }
		// } else {
		// signin_tv.setText("签到");
		//
		// }
		if (ManagerUtils.getInstance().isLogin(mContext)) {
			// 设置衣保金
			/*
			 * ybj_tv.setText("可用" +
			 * mSharedPreferenceUtils.getYBJKeYong(mContext) + "元衣保金");
			 */
			if (name_tv != null) {
				if (ManagerUtils.getInstance().getUserName(mContext) == null
						|| "null".equals(ManagerUtils.getInstance()
								.getUserName(mContext))
						|| "穿衣典用户".equals(ManagerUtils.getInstance()
								.getUserName(mContext))) {
					name_tv.setText("未设置");
				} else {
					name_tv.setText(ManagerUtils.getInstance().getUserName(
							mContext));
				}
			}
			if (phone_tv != null) {
				phone_tv.setText(ManagerUtils.getInstance().getPhoneNum(
						mContext));
			}
			if (circleimageview != null
					&& ManagerUtils.getInstance().getUserHead(mContext) != null) {
				// 绑定图片
				x.image().bind(circleimageview,
						ManagerUtils.getInstance().getUserHead(mContext),
						mImageOptions, new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub
								circleimageview
										.setImageResource(R.drawable.un_login_in);
							}

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub
								circleimageview
										.setImageResource(R.drawable.un_login_in);
							}
						});
			} else {
				circleimageview.setImageResource(R.drawable.un_login_in);
			}
		} else {
			// ybj_tv.setText("");

			if (name_tv != null) {

				name_tv.setText("未登陆");
			}
			if (phone_tv != null) {
				phone_tv.setText(null);
			}
			if (circleimageview != null) {

				circleimageview.setImageResource(R.drawable.un_login_in);
			}
		}
		initData();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 * 
	 * @Description TODO
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
	}

	/**
	 * 
	 * @description
	 * @version
	 * @author
	 * @update 2013-12-6 上午11:08:33
	 */
	private void initView() {
		// TODO Auto-generated method stub
		String shenFen = ManagerUtils.getInstance().getShenFen(mContext);
		if (shenFen != null && (shenFen.contains("@店家@"))) {
			djgl_rl.setVisibility(View.VISIBLE);
		} else {
			djgl_rl.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * @description
	 * @version
	 * @author
	 * @update 2013-12-29 下午04:17:56
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.name_tv, R.id.xxzx_rl, R.id.signin_tv, R.id.circleimageview,
			R.id.zh_rl, R.id.ll_ddzx, R.id.zl_rl, R.id.ll_zdlt, R.id.xx_rl,
			R.id.sz_rl, R.id.ll_djsq, R.id.ll_yjtc, R.id.djgl_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//点击昵称
		case R.id.name_tv:
			if (ManagerUtils.getInstance().yjtc != null) {
				if (mEWMDialog == null) {
					mEWMDialog = new EWMDialog(getActivity());
				}
				mEWMDialog.show();
				mEWMDialog.setEWM("邀请码",ManagerUtils.getInstance().yjtc
						.getShareCode());
			} else {
				pbDialog.show();
				// 获取业绩提成列表
				YJTCExec.getInstance().getYJTCList(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_YJTC_S,
						NetworkAsyncCommonDefines.GET_YJTC_F);
			}
			break;
		// 点击消息中心
		case R.id.xxzx_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				Intent xtxx = new Intent(mContext, SystemMessageActivity.class);
				startActivity(xtxx);
			} else {
				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivity(loginIntent);
			}
			break;
		// 点击签到
		case R.id.signin_tv:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				if (isFinish()) {
					String yqd = signin_tv.getText().toString();
					if ("签到".equals(yqd)) {
						pbDialog.show();
						UserExec.getInstance()
								.signin(mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										NetworkAsyncCommonDefines.SIGNIN_S,
										NetworkAsyncCommonDefines.SIGNIN_F);
					} else {
						Toast.makeText(mContext, "今天已签到", Toast.LENGTH_SHORT)
								.show();
					}

				}
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
				pbDialog.dismiss();
			}
			break;
		// 点击头像
		case R.id.circleimageview:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				File headFolder = new File(PathCommonDefines.HEAD);
				if (!headFolder.exists()) {
					headFolder.mkdirs();
				}
				new SweetAlertDialog(mContext)
						.setHandler(mHandler)
						.setTitleText("请选择获取头像方式！")
						.setCancelText("打开相册")
						.setConfirmText("打开相机")
						.showCancelButton(true)
						.showConfirmButton(true)
						.setCancelClickListener(
								NetworkAsyncCommonDefines.SWEETALERTDIALOG_S,
								new SweetAlertDialog.OnSweetClickListener() {
									@Override
									public void onClick(SweetAlertDialog sDialog) {
										sDialog.setTitleText("正在打开!")
												.showCancelButton(false)
												.showConfirmButton(false)
												.setCancelClickListener(null)
												.setConfirmClickListener(null)
												.changeAlertType(
														SweetAlertDialog.SUCCESS_TYPE);

									}
								})
						.setConfirmClickListener(
								NetworkAsyncCommonDefines.SWEETALERTDIALOG_F,
								new SweetAlertDialog.OnSweetClickListener() {
									@Override
									public void onClick(SweetAlertDialog sDialog) {
										sDialog.setTitleText("正在打开!")
												.showCancelButton(false)
												.showConfirmButton(false)
												.setCancelClickListener(null)
												.setConfirmClickListener(null)
												.changeAlertType(
														SweetAlertDialog.SUCCESS_TYPE);
									}
								}).show();
			} else {
				pbDialog.show();
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				startActivity(loginActivity);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
				pbDialog.dismiss();
			}
			break;
		// 点击管理
		case R.id.zh_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				pbDialog.show();
				Intent myAccount = new Intent(mContext,
						ZhangHaoGuanLiActivity.class);
				startActivity(myAccount);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
				System.gc();
				pbDialog.dismiss();
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
				pbDialog.dismiss();
			}

			break;
		// 点击订单中心
		case R.id.ll_ddzx:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent myOrderActivity = new Intent(mContext,
						WoDeDingDanActivity.class);
				startActivity(myOrderActivity);
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
				pbDialog.dismiss();
			}
			break;
		// 点击基础资料
		case R.id.zl_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent myInfoActivity = new Intent(mContext,
						JiChuZiLiaoActivity.class);
				startActivity(myInfoActivity);
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
				pbDialog.dismiss();
			}

			break;
		// 点击诊断量体
		case R.id.ll_zdlt:
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				pbDialog.show();
				Intent bodyDataActivity = new Intent(mContext,
						ZhenDuanLTActivity.class);
				startActivity(bodyDataActivity);
				pbDialog.dismiss();
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
				pbDialog.dismiss();
			}

			break;

		// 点击形象
		case R.id.xx_rl:
			String sex = mSharedPreferenceUtils.getSex(mContext);
			String birthday = mSharedPreferenceUtils.getBirthday(mContext);
			String height = mSharedPreferenceUtils.getHeight(mContext);
			String Weight = mSharedPreferenceUtils.getWeight(mContext);

			if (ManagerUtils.getInstance().isLogin(mContext)) {
				if (sex.equals("未设置") || birthday.equals("未设置")
						|| height.equals("未设置") || Weight.equals("未设置")) {
					pbDialog.show();
					Toast.makeText(mContext, "请先设置资料", Toast.LENGTH_SHORT)
							.show();
					Intent myInfoActivity2 = new Intent(mContext,
							JiChuZiLiaoActivity.class);
					startActivity(myInfoActivity2);
					pbDialog.dismiss();
				} else {

					pbDialog.show();
					Intent paiZhaoYinDaoActivity = new Intent(mContext,
							SetImageActivity.class);
					startActivity(paiZhaoYinDaoActivity);
					pbDialog.dismiss();
				}
			} else {
				pbDialog.show();
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				startActivity(loginActivity);
				pbDialog.dismiss();
			}

			break;

		// 点击设置
		case R.id.sz_rl:
			pbDialog.show();
			Intent moreOperationActivity = new Intent(mContext,
					SettingActivity.class);
			startActivity(moreOperationActivity);

			pbDialog.dismiss();
			break;
		// 点击业绩提成
		case R.id.ll_yjtc:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				startActivity(new Intent(mContext, YJTCActivity.class));
			} else {
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				startActivity(loginActivity);
				pbDialog.dismiss();
			}

			break;
		// 点击店家收钱
		case R.id.ll_djsq:
			pbDialog.show();
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				startActivity(new Intent(mContext, DJSQActivity.class));
				pbDialog.dismiss();
			} else {
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				startActivity(loginActivity);
				pbDialog.dismiss();
			}

			break;
		// 点击店家管理
		case R.id.djgl_rl:
			pbDialog.show();
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				startActivity(new Intent(mContext, DianJiaGuanLiActivity.class));
				pbDialog.dismiss();

			} else {
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				startActivity(loginActivity);
				pbDialog.dismiss();
			}
			break;

		}
	}

	/**
	 * @description: 清除缓存
	 * @exception
	 */
	protected void clearCache() {
		// TODO Auto-generated method stub
		File file = new File(PathCommonDefines.JSON_FOLDER);
		FileSDCacher.deleteDirectory2(file);
		// 启动应用开发下载数据
		Intent downServiceIntent = new Intent(mContext,
				DownLoadingService.class);
		downServiceIntent.putExtra("id", 1);
		mContext.startService(downServiceIntent);
		mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLEAR_S);

	}

	public interface AboutUsOnClickListener {
		public void onClick(Dialog dialog);
	}

	/**
	 * @description:打开相册
	 */
	protected void openAlbum() {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		startActivityForResult(mIntent, NetworkAsyncCommonDefines.OPEN_ALBUM);
		// overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
	}

	private String suffix, path;

	/**
	 * @description:打开相机
	 */
	protected void openCamera() {
		// TODO Auto-generated method stub
		try {
			suffix = System.currentTimeMillis() + ".0";
			path = PathCommonDefines.HEAD + "/" + suffix;
			final Intent intent = getTakePickIntent(new File(path));
			startActivityForResult(intent,
					NetworkAsyncCommonDefines.OPEN_CAMERA);
		} catch (Exception e) {
			Toast.makeText(mContext, "没有找到", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * @description:获取图片的intent
	 */
	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 打开相机返回
		case NetworkAsyncCommonDefines.OPEN_CAMERA:
			try {
				Intent intent = new Intent(mContext, CutHeadActivity.class);
				intent.putExtra("path", path);
				startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		// 打开相册返回
		case NetworkAsyncCommonDefines.OPEN_ALBUM:
			try {

				if (data != null) {
					Uri uri = data.getData(); // 返回的是一个Uri
					if (uri != null) {
						String path = UriUtils.getInstance()
								.getImageAbsolutePath(getActivity(), uri);
						if (path == null || path.equals("")) {
							Toast.makeText(mContext, "没找到图片,请拍照",
									Toast.LENGTH_SHORT).show();
							return;
						}
						File s = new File(path);
						if (s != null && s.exists()) {
							// s = new File(path);
							// File t = new File(PathCommonDefines.HEAD,
							// "/head1.0");
							// if (FileSDCacher.copyFile(s, t)) {
							Intent intent = new Intent(mContext,
									CutHeadActivity.class);

							intent.putExtra("path", path);
							startActivity(intent);
							// }
						} else {
							Toast.makeText(mContext, "没找到图片,请拍照",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(mContext, "没找到图片,请拍照", Toast.LENGTH_SHORT)
							.show();

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			/* 获取业绩提成列表成功 */
			case NetworkAsyncCommonDefines.GET_YJTC_S:
				if (ManagerUtils.getInstance().yjtc != null) {
					if (mEWMDialog == null) {
						mEWMDialog = new EWMDialog(getActivity());
					}
					mEWMDialog.show();
					mEWMDialog.setEWM("邀请码",ManagerUtils.getInstance().yjtc
							.getShareCode());
				}
				pbDialog.dismiss();
				break;
			/* 获取业绩提成列表失败 */
			case NetworkAsyncCommonDefines.GET_YJTC_F:
				if (ManagerUtils.getInstance().yjtc != null) {
					if (mEWMDialog == null) {
						mEWMDialog = new EWMDialog(getActivity());
					}
					mEWMDialog.show();
					mEWMDialog.setEWM("邀请码",ManagerUtils.getInstance().yjtc
							.getShareCode());
				}
				pbDialog.dismiss();
				break;
			// 签到成功
			case NetworkAsyncCommonDefines.SIGNIN_S:

				Bundle resultBun = msg.getData();
				if (resultBun != null) {

					mSharedPreferenceUtils.setQianDaoRiQi(mContext, qdrq);
					mSharedPreferenceUtils.setQianDaoId(mContext, ManagerUtils
							.getInstance().getUser_id(mContext));
					// 已签到
					signin_tv.setBackgroundResource(R.drawable.textview_bg_10);
					signin_tv.setText("已签到");
					signin_tv.setPadding(20, 6, 20, 6);
					signin_value.setVisibility(View.VISIBLE);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							signin_value.setVisibility(View.GONE);
						}
					}, 2000);
					// 获取用户财富信息
					YJTCExec.getInstance().getYJTCList(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_S,
							NetworkAsyncCommonDefines.GET_USER_PROPERTY_F);

				}
				initData();
				pbDialog.dismiss();
				break;
			// 签到失败
			case NetworkAsyncCommonDefines.SIGNIN_F:
				pbDialog.dismiss();
				break;
			// 获取衣柜信息成功
			case NetworkAsyncCommonDefines.GET_WARDROBE_S:
				Bundle wBun = msg.getData();
				if (wBun != null) {
					Wardrobe w = wBun.getParcelable("wardrobe");
					if (w != null) {
						mSharedPreferenceUtils.setWardrobePhoto(mContext,
								w.getPhoto());
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
				break;
			// 获取衣柜信息失败
			case NetworkAsyncCommonDefines.GET_WARDROBE_F:

				break;

			// 打开相册
			case NetworkAsyncCommonDefines.SWEETALERTDIALOG_S:
				openAlbum();

				break;
			// 打开相机
			case NetworkAsyncCommonDefines.SWEETALERTDIALOG_F:
				openCamera();
				break;
			case 1:
				pbDialog.dismiss();

				break;

			}
		}
	};

	private static GoToXXGLFragmentListener mGoToXXGLFragmentListener;

	public static void setOnGoToXXGLFragmentListener(
			GoToXXGLFragmentListener goToXXGLFragmentListener) {
		mGoToXXGLFragmentListener = goToXXGLFragmentListener;
	}

	public interface GoToXXGLFragmentListener {
		void goTo(boolean isGoing);
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
