package cn.dressbook.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.LifePhoto;
import cn.dressbook.ui.model.PL;
import cn.dressbook.ui.net.LifePhotosExec;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.utils.BitmapTool;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.ImageUtils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.UriUtils;
import cn.dressbook.ui.view.SelectPLPopupWindow;

/**
 * @description: 发布需求界面
 * @author:袁东华
 * @time:2015-8-10下午3:30:29
 */
@ContentView(R.layout.publishrequirement_layout)
public class PublishRequirementActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, false);
	private Context mContext = PublishRequirementActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private ArrayList<LifePhoto> mList;
	private ArrayList<PL> nanList = new ArrayList<PL>();
	private ArrayList<PL> nvList = new ArrayList<PL>();
	private String day = "0.5";
	private ArrayList<String> list;
	private String category_id = "";
	private int sex;
	private boolean isZD;
	private String pl = "";

	/**
	 * 选中的场合
	 */
	private String mOccation;
	/**
	 * 价位
	 */
	private String mPrice;
	/**
	 * 选中的分类
	 */
	private String mCategory = "1";

	/**
	 * 点击的图片位置
	 */
	private int mAlbumPosition = 1;
	private String mUrl1, mUrl2, mUrl3;
	/**
	 * 返回
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 职场
	 */
	@ViewInject(R.id.zc_rl)
	private RelativeLayout zc_rl;
	/**
	 * 度假
	 */
	@ViewInject(R.id.dj_rl)
	private RelativeLayout dj_rl;
	/**
	 * 商务
	 */
	@ViewInject(R.id.sw_rl)
	private RelativeLayout sw_rl;
	/**
	 * 运动
	 */
	@ViewInject(R.id.yd_rl)
	private RelativeLayout yd_rl;
	/**
	 * 休闲聚会
	 */
	@ViewInject(R.id.xx_rl)
	private RelativeLayout xx_rl;
	/**
	 * 价位
	 */
	@ViewInject(R.id.jw1_tv)
	private TextView jw1_tv;
	@ViewInject(R.id.jw2_tv)
	private TextView jw2_tv;
	@ViewInject(R.id.jw3_tv)
	private TextView jw3_tv;
	@ViewInject(R.id.jw4_tv)
	private TextView jw4_tv;
	@ViewInject(R.id.jw5_tv)
	private TextView jw5_tv;
	/**
	 * 品类
	 */
	@ViewInject(R.id.pl_value)
	private TextView pl_value;
	@ViewInject(R.id.rl)
	private RelativeLayout rl;
	private SelectPLPopupWindow mSelectPLPopupWindow;
	@ViewInject(R.id.shz1_iv)
	private ImageView shz1_iv;
	@ViewInject(R.id.shz2_iv)
	private ImageView shz2_iv;
	@ViewInject(R.id.shz3_iv)
	private ImageView shz3_iv;
	@ViewInject(R.id.zjsj_value)
	private TextView zjsj_value;
	/**
	 * 推荐时间进度条
	 */
	@ViewInject(R.id.zjsj_sb)
	private SeekBar zjsj_sb;
	/**
	 * 一句话
	 */
	@ViewInject(R.id.ly_et)
	private EditText ly_et;
	/**
	 * 推荐
	 */
	@ViewInject(R.id.tj_rg)
	private RadioGroup tj_rg;
	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;
	/**
	 * 指定人数
	 */
	@ViewInject(R.id.num_tv)
	private TextView num_tv;
	/**
	 * 指定顾问推荐
	 */
	@ViewInject(R.id.zdtj_rb)
	private RadioButton zdtj_rb;
	/**
	 * 自由推荐
	 */
	@ViewInject(R.id.zygw_rb)
	private RadioButton zygw_rb;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("发布需求");
		zjsj_sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				switch (progress) {
				case 0:
					day = "0.5";
					zjsj_value.setText(day + "天");
					break;
				case 1:
					day = "1";
					zjsj_value.setText(day + "天");
					break;
				case 2:
					day = "1.5";
					zjsj_value.setText(day + "天");
					break;
				case 3:
					day = "2";
					zjsj_value.setText(day + "天");
					break;
				default:
					break;
				}

			}
		});
		// 点击指定顾问
		zdtj_rb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isZD = true;
				zdtj_rb.setChecked(true);
				Intent findAdviserActivity = new Intent(mContext,
						FindAdviserActivity.class);
				startActivity(findAdviserActivity);
			}
		});
		zygw_rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					isZD = false;
					list = null;
					num_tv.setText("");
				}
			}

		});

		ly_et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager.isActive()) {
					scrollView.scrollTo(0, ly_et.getBottom());
				}
			}
		});
		ly_et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputMethodManager.isActive()) {
					scrollView.scrollTo(0, ly_et.getBottom());
				}
			}
		});

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		String sexName = mSharedPreferenceUtils.getSex(mContext);
		if (sexName.equals("男")) {
			sex = 1;
		} else if (sexName.equals("女")) {
			sex = 2;
		}
		RequirementExec.getInstance().getPL(mHandler,
				NetworkAsyncCommonDefines.GET_PL_S,
				NetworkAsyncCommonDefines.GET_PL_F);
		String wardrobe_id = mSharedPreferenceUtils.getWardrobeID(mContext);
		if (wardrobe_id != null) {

			// 获取生活照
			LifePhotosExec.getInstance().getLifePhoto(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					wardrobe_id, NetworkAsyncCommonDefines.GET_LIFEPHOTO_S,
					NetworkAsyncCommonDefines.GET_LIFEPHOTO_F);
		}
	}

	/**
	 * 获取指定顾问师id
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		list = ManagerUtils.getInstance().getAdviserIdList();
		if (list != null && list.size() > 0) {
			num_tv.setText("(" + list.size() + "人)");
			zdtj_rb.setChecked(true);
		} else {
			num_tv.setText("");
			zygw_rb.setChecked(true);
		}
	}

	@Event({ R.id.submit_tv, R.id.back_rl, R.id.zc_rl, R.id.dj_rl, R.id.sw_rl,
			R.id.yd_rl, R.id.xx_rl, R.id.jw1_tv, R.id.jw2_tv, R.id.jw3_tv,
			R.id.jw4_tv, R.id.jw5_tv, R.id.pl_value, R.id.shz1_iv,
			R.id.shz2_iv, R.id.shz3_iv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击提交
		case R.id.submit_tv:
			if (isFinish()) {

				if (mOccation == null || "".equals(mOccation)) {
					Toast.makeText(mContext, "请选择穿着场合", Toast.LENGTH_SHORT)
							.show();

					return;
				}
				if (pl == null || "".equals(pl)) {
					Toast.makeText(mContext, "请选择品类", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (mPrice == null) {
					Toast.makeText(mContext, "请选择价位", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				String supplement = ly_et.getText().toString();
				if (supplement != null && !"".equals(supplement)) {
					supplement = supplement.trim().replaceAll("\\s", "");
					supplement = supplement.replaceAll("\t", "");
					supplement = supplement.replaceAll("\r", "");
					supplement = supplement.replaceAll("\n", "");
				} else {
					Toast.makeText(mContext, "请写一句话", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if ((mUrl1 == null || "".equals(mUrl1))
						&& (url1 == null || "".equals(url1))) {
					Toast.makeText(mContext, "请添加第一张照片", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if ((mUrl2 == null || "".equals(mUrl2))
						&& (url2 == null || "".equals(url2))) {
					Toast.makeText(mContext, "请添加第二张照片", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if ((mUrl3 == null || "".equals(mUrl3))
						&& (url3 == null || "".equals(url3))) {
					Toast.makeText(mContext, "请添加第三张照片", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				String adviserId = "";
				if (isZD) {
					if (list == null || list.size() == 0) {
						Toast.makeText(mContext, "请指定顾问师", Toast.LENGTH_SHORT)
								.show();
						return;
					} else {
						for (int i = 0; i < list.size(); i++) {
							adviserId += list.get(i) + ",";
						}
						adviserId = adviserId.substring(0,
								adviserId.length() - 1);
					}
				} else {
					adviserId = null;
				}
				pbDialog.show();
				RequirementExec.getInstance().publishRequirement(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						mOccation, pl, mPrice, sex + "", supplement, day,
						adviserId, category_id, mUrl1, mUrl2, mUrl3,
						NetworkAsyncCommonDefines.PUBLISH_REQUIREMENT_S,
						NetworkAsyncCommonDefines.PUBLISH_REQUIREMENT_F);
			} else {
				Toast.makeText(mContext, "正在处理请稍后", Toast.LENGTH_SHORT).show();
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击图片
		case R.id.shz1_iv:
			xiangCe(1);
			break;
		// 点击图片
		case R.id.shz2_iv:
			xiangCe(2);
			break;
		// 点击图片
		case R.id.shz3_iv:
			xiangCe(3);
			break;
		// 点击职场
		case R.id.zc_rl:
			mOccation = "职场";
			clearOccationState(1);
			zc_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击度假
		case R.id.dj_rl:
			mOccation = "度假";
			clearOccationState(2);
			dj_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击商务
		case R.id.sw_rl:
			mOccation = "商务";
			clearOccationState(3);
			sw_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击运动
		case R.id.yd_rl:
			mOccation = "运动";
			clearOccationState(4);
			yd_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击休闲聚会
		case R.id.xx_rl:
			mOccation = "休闲聚会";
			clearOccationState(5);
			xx_rl.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价格
		case R.id.jw1_tv:
			mPrice = "0-100";
			clearJWState(1);
			jw1_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价格
		case R.id.jw2_tv:
			mPrice = "100-200";
			clearJWState(2);
			jw2_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价格
		case R.id.jw3_tv:
			mPrice = "200-500";
			clearJWState(3);
			jw3_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价格
		case R.id.jw4_tv:
			mPrice = "500-1000";
			clearJWState(4);
			jw4_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击价格
		case R.id.jw5_tv:
			mPrice = "1000-0";
			clearJWState(5);
			jw5_tv.setBackgroundResource(R.drawable.textview_bg_6);
			break;
		// 点击品类
		case R.id.pl_value:
			if (mSelectPLPopupWindow == null) {
				mSelectPLPopupWindow = new SelectPLPopupWindow(
						PublishRequirementActivity.this, mHandler);
			}
			mSelectPLPopupWindow.setData(nanList, nvList, sex);
			mSelectPLPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;

		}

	}

	/**
	 * @description:清除价位状态
	 */
	private void clearJWState(int i) {
		// TODO Auto-generated method stub
		jw1_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw2_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw3_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw4_tv.setBackgroundResource(R.drawable.textview_bg_5);
		jw5_tv.setBackgroundResource(R.drawable.textview_bg_5);
	}

	/**
	 * @description:清除场合状态
	 */
	private void clearOccationState(int i) {
		// TODO Auto-generated method stub
		zc_rl.setBackgroundDrawable(null);
		dj_rl.setBackgroundDrawable(null);
		sw_rl.setBackgroundDrawable(null);
		yd_rl.setBackgroundDrawable(null);
		xx_rl.setBackgroundDrawable(null);
	}

	/**
	 * @description:打开相册
	 */
	private void xiangCe(int i) {
		// TODO Auto-generated method stub
		mAlbumPosition = i;
		// 从相簿中获得照片
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		startActivityForResult(mIntent, NetworkAsyncCommonDefines.ENTER_ALBUM);
		// overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		// 进入相册返回
		case NetworkAsyncCommonDefines.ENTER_ALBUM:
			if (data != null) {
				Uri uri = data.getData(); // 返回的是一个Uri
				if (uri != null) {
					String path = UriUtils.getInstance().getImageAbsolutePath(
							this, uri);
					if (path == null || path.equals("")) {
						Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					File s = new File(path);
					if (s != null && s.exists()) {
						pbDialog.dismiss();
						switch (mAlbumPosition) {
						case 1:
							mUrl1 = PathCommonDefines.PAIZHAO
									+ "/requirement1.jpg";
							mUrl1=ImageUtils.dealImage(path, mUrl1);
							LogUtil.e("缩放后的图片路径:"+mUrl1);
							// 绑定图片
							x.image().bind(shz1_iv, mUrl1, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											url1 = null;
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub
											url1 = null;
										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											url1 = null;
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											url1 = null;
										}
									});

							break;
						case 2:
							mUrl2 = PathCommonDefines.PAIZHAO
									+ "/requirement2.jpg";
							mUrl2=ImageUtils.dealImage(path, mUrl2);
							// 绑定图片
							x.image().bind(shz2_iv, mUrl2, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											url2 = null;
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub
											url2 = null;
										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											url2 = null;
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											url2 = null;
										}
									});

							break;
						case 3:
							mUrl3 = PathCommonDefines.PAIZHAO
									+ "/requirement3.jpg";
							mUrl3=ImageUtils.dealImage(path, mUrl3);
							// 绑定图片
							x.image().bind(shz3_iv, mUrl3, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											url3 = null;
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub
											url3 = null;
										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											url3 = null;
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											url3 = null;
										}
									});
							break;
						default:
							break;
						}

					} else {
						Toast.makeText(mContext, "没找到图片,请拍照",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(mContext, "没找到图片,请拍照", Toast.LENGTH_SHORT)
						.show();

			}

			break;
		}
	}

	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 选择品类
			case NetworkAsyncCommonDefines.SELECT_PL:
				mSelectPLPopupWindow.dismiss();
				sex = mSelectPLPopupWindow.sex;
				int position1 = mSelectPLPopupWindow.position1;
				ArrayList<String> position2 = mSelectPLPopupWindow.position2;
				if (sex == 1 && nanList != null) {
					pl = nanList.get(position1).getId();
					if (position2 != null && position2.size() > 0) {
						String str = "男装-" + nanList.get(position1).getName()
								+ "-";
						for (int i = 0; i < position2.size(); i++) {
							category_id += nanList.get(position1).getXls()
									.get(Integer.parseInt(position2.get(i)))
									.getId()
									+ ",";
							str += nanList.get(position1).getXls()
									.get(Integer.parseInt(position2.get(i)))
									.getName()
									+ "-";
						}
						category_id = category_id.substring(0,
								category_id.length() - 1);
						str = str.substring(0, str.length() - 1);
						pl_value.setText(str);
					} else {
						// category_id = nanList.get(position1).getId();
						pl_value.setText("男装-"
								+ nanList.get(position1).getName());
					}
				}
				if (sex == 2 && nvList != null) {
					pl = nvList.get(position1).getId();
					if (position2 != null && position2.size() > 0) {
						String str = "女装-" + nvList.get(position1).getName()
								+ "-";
						for (int i = 0; i < position2.size(); i++) {
							category_id += nvList.get(position1).getXls()
									.get(Integer.parseInt(position2.get(i)))
									.getId()
									+ ",";
							str += nvList.get(position1).getXls()
									.get(Integer.parseInt(position2.get(i)))
									.getName()
									+ "-";
						}
						category_id = category_id.substring(0,
								category_id.length() - 1);
						str = str.substring(0, str.length() - 1);
						pl_value.setText(str);
					} else {
						// category_id = nvList.get(position1).getId();
						pl_value.setText("女装-"
								+ nvList.get(position1).getName());
					}

				}
				break;
			// 提交需求成功
			case NetworkAsyncCommonDefines.PUBLISH_REQUIREMENT_S:
				pbDialog.dismiss();
				FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.PAIZHAO));
				Toast.makeText(mContext, "提交成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			// 提交需求失败
			case NetworkAsyncCommonDefines.PUBLISH_REQUIREMENT_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "提交失败", Toast.LENGTH_LONG).show();
				break;
			// 获取服装分类成功
			case NetworkAsyncCommonDefines.GET_PL_S:
				Bundle data = msg.getData();
				if (data != null) {
					nanList = data.getParcelableArrayList("nan");
					nvList = data.getParcelableArrayList("nv");
					if (nanList != null) {
					}
				}
				break;
			// 获取服装分类失败
			case NetworkAsyncCommonDefines.GET_PL_F:
				break;
			// 获取生活照成功
			case NetworkAsyncCommonDefines.GET_LIFEPHOTO_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					mList = listData.getParcelableArrayList("list");
					setData();

				} else {
				}
				break;
			// 获取生活照失败
			case NetworkAsyncCommonDefines.GET_LIFEPHOTO_F:

				break;
			default:
				break;
			}
		}

	};
	private String url1, url2, url3;

	public void setData() {
		if (mList != null && mList.size() > 0) {

			switch (mList.size()) {
			case 1:
				url1 = mList.get(0).getPic();
				url2 = null;
				url3 = null;
				break;
			case 2:
				url1 = mList.get(0).getPic();
				url2 = mList.get(1).getPic();
				url3 = null;
				break;
			case 3:
				url1 = mList.get(0).getPic();
				url2 = mList.get(1).getPic();
				url3 = mList.get(2).getPic();
				break;
			}
			if (url1 == null) {
				shz1_iv.setImageResource(R.drawable.addimage_iv);
			} else {
				// 绑定图片
				x.image().bind(shz1_iv, url1, mImageOptions,
						new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method stub
								mUrl1 = null;
							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub
								mUrl1 = null;
							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub
								mUrl1 = null;
								shz1_iv.setImageResource(R.drawable.addimage_iv);
							}

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub
								mUrl1 = null;
							}
						});
			}
			if (url2 == null) {
				shz2_iv.setImageResource(R.drawable.addimage_iv);
			} else {

				// 绑定图片
				x.image().bind(shz2_iv, url2, mImageOptions,
						new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method stub
								mUrl2 = null;
							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub
								mUrl2 = null;
							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub
								mUrl2 = null;
								shz2_iv.setImageResource(R.drawable.addimage_iv);
							}

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub
								mUrl2 = null;
							}
						});
			}
			if (url3 == null) {
				shz3_iv.setImageResource(R.drawable.addimage_iv);
			} else {
				// 绑定图片
				x.image().bind(shz3_iv, url3, mImageOptions,
						new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method stub
								mUrl3 = null;
							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub
								mUrl3 = null;
							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub
								mUrl3 = null;
								shz3_iv.setImageResource(R.drawable.addimage_iv);
							}

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub
								mUrl3 = null;
							}
						});
			}
		} else {
			shz1_iv.setImageResource(R.drawable.addimage_iv);
			shz2_iv.setImageResource(R.drawable.addimage_iv);
			shz3_iv.setImageResource(R.drawable.addimage_iv);
			url1 = null;
			url2 = null;
			url3 = null;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
