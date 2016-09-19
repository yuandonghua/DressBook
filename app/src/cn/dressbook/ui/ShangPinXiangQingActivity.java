package cn.dressbook.ui;

import java.io.File;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.webkit.CustomWebChromeClient;
import cn.dressbook.ui.webkit.CustomWebView;
import cn.dressbook.ui.webkit.CustomWebViewClient;

/**
 * @description:商品详情
 * @author:袁东华
 * @time:2015-12-7上午11:02:07
 */
@ContentView(R.layout.productlink2)
public class ShangPinXiangQingActivity extends BaseActivity {
	private Context mContext = ShangPinXiangQingActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private int gmsl = 1;
	private boolean pngFlag, xmlFlag, headFlag, headMaskFlag, neckMaskFlag;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 购物车
	 */
	@ViewInject(R.id.tv_3)
	private TextView tv_3;
	@ViewInject(R.id.webview)
	private CustomWebView mCurrentWebView;
	private AttireScheme mAttireScheme;
	/**
	 * 试衣
	 */
	@ViewInject(R.id.tv_1)
	private TextView tv_1;
	/**
	 * 加入购物车
	 */
	@ViewInject(R.id.tv_2)
	private TextView tv_2;
	/**
	 * 加入购物车
	 */
	@ViewInject(R.id.ll2)
	private LinearLayout ll2;
	/**
	 * 尺码
	 */
	@ViewInject(R.id.cm_et)
	private EditText cm_et;
	/**
	 * 颜色
	 */
	@ViewInject(R.id.ys_et)
	private EditText ys_et;
	/**
	 * 购买数量
	 */
	@ViewInject(R.id.gmsl_et)
	private EditText gmsl_et;
	/**
	 * 减号
	 */
	@ViewInject(R.id.minus_iv)
	private ImageView minus_iv;
	/**
	 * 加号
	 */
	@ViewInject(R.id.plus_iv)
	private ImageView plus_iv;
	/**
	 * 价格
	 */
	@ViewInject(R.id.price_tv)
	private TextView price_tv;
	/**
	 * 尺码
	 */
	@ViewInject(R.id.cm_tv)
	private TextView cm_tv;
	/**
	 * 颜色
	 */
	@ViewInject(R.id.ys_tv)
	private TextView ys_tv;
	/**
	 * 关闭按钮
	 */
	@ViewInject(R.id.close_iv)
	private ImageView close_iv;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		initIntent();
		title_tv.setText("商品详情");
		cm_tv.setText("尺 码:");
		ys_tv.setText("颜 色:");
		ys_et.setHint("输入尺码");
		cm_et.setHint("输入颜色");
		if (mAttireScheme != null) {
			price_tv.setText("￥" + mAttireScheme.getShop_price());
		}
		// webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		// WebSettings mWebSettings = webview.getSettings();
		// mWebSettings.setDomStorageEnabled(true);
		// mWebSettings.setUseWideViewPort(true);
		// mWebSettings.setLoadWithOverviewMode(true);
		// mWebSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// // WebView自适应屏幕大小
		// mWebSettings.setDefaultTextEncodingName("UTF-8");
		// mWebSettings.setLoadsImagesAutomatically(true);
		// // 设置可以支持缩放
		//
		// mWebSettings.setSupportZoom(true);
		//
		// // 设置默认缩放方式尺寸是far
		//
		// mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		//
		// // 设置出现缩放工具
		//
		// mWebSettings.setBuiltInZoomControls(true);
		// pbDialog.show();
		// webview.requestFocus();
		if (mAttireScheme != null) {
			mCurrentWebView.loadUrl(mAttireScheme.getTbkLink());
		}
		mCurrentWebView.setWebViewClient(new CustomWebViewClient(this));
		mCurrentWebView.setWebChromeClient(new CustomWebChromeClient(this));
	}

	private void initIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		mAttireScheme = intent.getParcelableExtra("AttireScheme");
	}

	@Override
	protected void initData() throws Exception {
	}

	@Event({ R.id.close_iv, R.id.back_rl, R.id.tv_1, R.id.tv_2, R.id.tv_3,
			R.id.minus_iv, R.id.plus_iv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击关闭按钮
		case R.id.close_iv:
			ll2.setVisibility(View.GONE);
			tv_2.setText("加入购物车");
			break;
		// 点击减号
		case R.id.minus_iv:
			if (gmsl > 1) {
				gmsl--;
			} else {
				Toast.makeText(mContext, "至少购买一件", Toast.LENGTH_SHORT).show();
			}
			gmsl_et.setText(gmsl + "");
			break;
		// 点击加号
		case R.id.plus_iv:
			if (gmsl < 100) {

				gmsl++;
			} else {
				Toast.makeText(mContext, "一次限购100件", Toast.LENGTH_SHORT).show();
			}
			gmsl_et.setText(gmsl + "");
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击购物车
		case R.id.tv_3:
			ll2.setVisibility(View.GONE);
			tv_2.setText("加入购物车");
			Intent shoppingIntent = new Intent(mContext,
					ShoppingCartActivity.class);
			startActivity(shoppingIntent);
			break;
		// 点击试衣
		case R.id.tv_1:
			if (isFinish()) {
				ll2.setVisibility(View.GONE);
				tv_2.setText("加入购物车");
				String str2 = tv_1.getText().toString();
				if ("试衣".equals(str2)) {
					if (mAttireScheme != null
							&& "yes".equals(mAttireScheme.getCan_try())) {
						pbDialog.show();
						tryOn();
					} else {
						Toast.makeText(mContext, "此款服装不能试衣", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
			break;
		// 点击加入购物车
		case R.id.tv_2:
			if (isFinish()) {
				String str1 = tv_2.getText().toString();
				if ("加入购物车".equals(str1)) {
					tv_2.setText("确定");
					ll2.setVisibility(View.VISIBLE);
				} else if ("确定".equals(str1)) {
					// 点击确定
					tv_2.setText("加入购物车");
					String cm = cm_et.getText().toString();
					String ys = ys_et.getText().toString();
					if (ys != null
							&& ys.equals(ManagerUtils.getInstance().getUser_id(
									mContext))) {
						Toast.makeText(mContext, "无法邀请自己", Toast.LENGTH_SHORT)
								.show();
						break;
					}
					pbDialog.show();
					// 广告商品
					if ("2".equals(mAttireScheme.getFor_vip())) {

						OrderExec
								.getInstance()
								.addShoppingCart(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										mAttireScheme.getAttireId(),
										mAttireScheme.getDesc(),
										gmsl + "",
										mAttireScheme.getShop_price() + "",
										mAttireScheme.getReferrer(),
										cm,
										null,
										ys,
										NetworkAsyncCommonDefines.ADD_SHOPPINGCART_S,
										NetworkAsyncCommonDefines.ADD_SHOPPINGCART_F);
					} else {
						OrderExec
								.getInstance()
								.addShoppingCart(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										mAttireScheme.getAttireId(),
										mAttireScheme.getDesc(),
										gmsl + "",
										mAttireScheme.getShop_price() + "",
										mAttireScheme.getReferrer(),
										cm,
										ys,
										null,
										NetworkAsyncCommonDefines.ADD_SHOPPINGCART_S,
										NetworkAsyncCommonDefines.ADD_SHOPPINGCART_F);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 下载试衣形象照成功
			case NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_S:
				pbDialog.dismiss();
				String url = PathCommonDefines.WARDROBE_TRYON + "/"
						+ mAttireScheme.getAttireId() + ".a";
				Intent tryOnInfoActivity2 = new Intent(mContext,
						TryOnInfoActivity.class);
				tryOnInfoActivity2.putExtra("path", url);
				startActivity(tryOnInfoActivity2);
				break;
			// 下载试衣形象照失败
			case NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_F:
				downloadFlag = true;
				tryOn();
				break;
			// 下载neckmask成功
			case NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_S:
				neckMaskFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag) {
					dealHead();
				}
				break;
			// 下载neckmask失败
			case NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_F:

				break;
			// 下载headmask成功
			case NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_S:
				headMaskFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag) {
					dealHead();
				}
				break;
			// 下载headmask失败
			case NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_F:

				break;
			// 下载头像成功
			case NetworkAsyncCommonDefines.DOWNLOAD_HEAD_S:
				headFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag) {
					dealHead();
				}
				break;
			// 下载头像失败
			case NetworkAsyncCommonDefines.DOWNLOAD_HEAD_F:
				Toast.makeText(mContext, "头像下载失败-----", Toast.LENGTH_SHORT)
						.show();
				pbDialog.dismiss();
				break;
			// 加入购物车成功
			case NetworkAsyncCommonDefines.ADD_SHOPPINGCART_S:
				Bundle shoppingBun = msg.getData();
				if (shoppingBun != null) {
					String recode = shoppingBun.getString("recode");
					String redesc = shoppingBun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				pbDialog.dismiss();
				ll2.setVisibility(View.GONE);
				break;
			// 加入购物车失败
			case NetworkAsyncCommonDefines.ADD_SHOPPINGCART_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				ll2.setVisibility(View.GONE);
				break;
			// 下载png图片成功
			case NetworkAsyncCommonDefines.DOWNLOAD_PNG_S:
				pngFlag = true;
				if (pngFlag && xmlFlag) {
					compoundImage();
				}
				break;
			// 下载png图片失败
			case NetworkAsyncCommonDefines.DOWNLOAD_PNG_F:

				break;
			// 下载xml文件成功
			case NetworkAsyncCommonDefines.DOWNLOAD_XML_S:
				xmlFlag = true;
				if (pngFlag && xmlFlag) {
					compoundImage();
				}
				break;
			// 下载xml文件失败
			case NetworkAsyncCommonDefines.DOWNLOAD_XML_F:

				break;
			// 合成成功
			case NetworkAsyncCommonDefines.COMPOUND_S:
				pbDialog.dismiss();
				Intent tryOnInfoActivity = new Intent(mContext,
						TryOnInfoActivity.class);
				String imageUrl = PathCommonDefines.WARDROBE_TRYON + "/"
						+ mAttireScheme.getAttireId() + ".a";
				tryOnInfoActivity.putExtra("path", imageUrl);
				startActivity(tryOnInfoActivity);
				break;
			// 合成失败
			case NetworkAsyncCommonDefines.COMPOUND_F:
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_NO_FACE:
				Toast.makeText(getApplicationContext(), "面部太暗,没有找到面部,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_NO_BODY:
				Toast.makeText(getApplicationContext(), "没有找到符合的身体,请重新提交数据",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_SMALL_HEAD:
				Toast.makeText(getApplicationContext(), "头部太小,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_TOP_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离上边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_LEFT_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离左边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_RIGHT_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离右边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_bottom_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离下边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_DIM_HEAD:
				Toast.makeText(getApplicationContext(), "头部太模糊,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD:
				Toast.makeText(getApplicationContext(), "头部处理错误,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;

			}
		}
	};
	/**
	 * 是否下载试衣文件失败
	 */
	private boolean downloadFlag = false;

	/**
	 * @description:试穿
	 */
	private void tryOn() {
		// TODO Auto-generated method stub
		File tryOnFolder = new File(PathCommonDefines.WARDROBE_TRYON);
		if (!tryOnFolder.exists()) {
			tryOnFolder.mkdirs();
		}
		String imageUrl = PathCommonDefines.WARDROBE_TRYON + "/"
				+ mAttireScheme.getAttireId() + ".a";
		File xxFile = new File(imageUrl);
		// 本地存在
		if (xxFile.exists()) {
			pbDialog.dismiss();
			// 合成成功
			mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.COMPOUND_S);
			return;
		}
		// 如果此用户在服务端存储试衣文件
		String tryResult = mSharedPreferenceUtils.getTryResultSave(mContext);
		if (!"no".equals(tryResult) && !downloadFlag) {
			String url = tryResult + "/" + mAttireScheme.getAttireId() + ".a";
			DownloadExec.getInstance().downloadFile(mHandler, url, imageUrl,
					NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_S,
					NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_F);
		} else {
			downloadFlag = false;
			// 试衣头像是否存在
			File head = new File(PathCommonDefines.WARDROBE_HEAD + "/head.0");
			if (!head.exists()) {
				String photo = mSharedPreferenceUtils
						.getWardrobePhoto(mContext);
				if (photo.endsWith("null")) {
					Toast.makeText(mContext, "请设置形象", Toast.LENGTH_SHORT)
							.show();
					checkInfo();
				} else {
					// 开始下载头像
					DownloadExec.getInstance().downloadFile(mHandler,
							photo + "/head.0",
							PathCommonDefines.WARDROBE_HEAD + "/head.0",
							NetworkAsyncCommonDefines.DOWNLOAD_HEAD_S,
							NetworkAsyncCommonDefines.DOWNLOAD_HEAD_F);
					// 开始下载headmask
					DownloadExec.getInstance().downloadFile(
							mHandler,
							photo + "/head.0maskhead.png",
							PathCommonDefines.WARDROBE_HEAD
									+ "/head.0maskhead.png",
							NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_S,
							NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_F);
					// 开始下载neckmask
					DownloadExec.getInstance().downloadFile(
							mHandler,
							photo + "/head.0maskfaceneck.png",
							PathCommonDefines.WARDROBE_HEAD
									+ "/head.0maskfaceneck.png",
							NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_S,
							NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_F);
				}
			} else {
				// 本地存在合成形象素材(头像和mask)
				String pngPath = PathCommonDefines.WARDROBE_MOTE + "/"
						+ mAttireScheme.getAttireId() + ".png";

				File pngFile = new File(pngPath);

				String xmlPath = PathCommonDefines.WARDROBE_MOTE + "/"
						+ mAttireScheme.getAttireId() + ".png.xml";

				File xmlFile = new File(pngPath);
				// 本地存在模特素材
				if (pngFile.exists() && xmlFile.exists()) {
					// 开始合成图片
					compoundImage();
				} else {
					// 模特png图片不存在
					if (!pngFile.exists()) {
						// 开始下载png图片
						DownloadExec.getInstance().downloadFile(mHandler,
								mAttireScheme.getModPic(), pngPath,
								NetworkAsyncCommonDefines.DOWNLOAD_PNG_S,
								NetworkAsyncCommonDefines.DOWNLOAD_PNG_F);
					} else {
						pngFlag = true;
					}
					// 模特xml文件不存在
					if (!xmlFile.exists()) {
						// 开始下载xml图片
						DownloadExec.getInstance().downloadFile(mHandler,
								mAttireScheme.getModPic() + ".xml", xmlPath,
								NetworkAsyncCommonDefines.DOWNLOAD_XML_S,
								NetworkAsyncCommonDefines.DOWNLOAD_XML_F);
					} else {
						xmlFlag = true;
					}
				}
			}
		}
	}

	/**
	 * @description:检查信息(头像,资料)
	 */
	private void checkInfo() {
		// TODO Auto-generated method stub
		String sex = mSharedPreferenceUtils.getSex(mContext);
		String birthday = mSharedPreferenceUtils.getBirthday(mContext);
		String height = mSharedPreferenceUtils.getHeight(mContext);
		String Weight = mSharedPreferenceUtils.getWeight(mContext);

		if (ManagerUtils.getInstance().isLogin(mContext)) {
			if (sex.equals("未设置") || birthday.equals("未设置")
					|| height.equals("未设置") || Weight.equals("未设置")) {
				Toast.makeText(mContext, "请先设置资料", Toast.LENGTH_SHORT).show();
				Intent myInfoActivity2 = new Intent(mContext,
						JiChuZiLiaoActivity.class);
				startActivity(myInfoActivity2);
				pbDialog.dismiss();
			} else {

				Intent paiZhaoYinDaoActivity = new Intent(mContext,
						SetImageActivity.class);
				startActivity(paiZhaoYinDaoActivity);
				pbDialog.dismiss();
			}
		} else {
			Intent loginActivity = new Intent(mContext, LoginActivity.class);
			startActivity(loginActivity);
			pbDialog.dismiss();
		}
	}

	/**
	 * @description:处理头像
	 */
	private void dealHead() {
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						try {
							final long time1 = System.currentTimeMillis();
							final int result = (int) DetectionBasedTracker
									.nativeMattingHead("wardrobe/head/head.0");
							long time2 = System.currentTimeMillis();
							switch (result) {
							// 头像处理成功
							case 0:
								compoundImage();
								break;
							// 没有找到面部
							case 1:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_FACE);
								break;
							// 没有找到身体
							case 2:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_BODY);
								break;
							// 头部太小
							case 3:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_SMALL_HEAD);
								break;
							// 头部距离上边框太近
							case 4:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_TOP_HEAD);
								break;
							// 头部距离左边框太近
							case 5:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_LEFT_HEAD);
								break;
							// 头部距离右边框太近
							case 6:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_RIGHT_HEAD);
								break;
							// 头部距离下边框太近
							case 7:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_bottom_HEAD);
								break;
							// 头部模糊
							case 8:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_DIM_HEAD);
								break;
							// 其他错误
							case 9:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
								break;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
	}

	/**
	 * @description:合成形象
	 */
	private void compoundImage() {
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						long time1 = System.currentTimeMillis();

						int result = (int) DetectionBasedTracker
								.nativeMergeBody(
										"wardrobe/head/head.0",
										"wardrobe/mote/"
												+ mAttireScheme.getAttireId()
												+ ".png", "wardrobe/tryOn/"
												+ mAttireScheme.getAttireId()
												+ ".jpeg");
						long time2 = System.currentTimeMillis();
						if (result == 0) {
							File a = new File(PathCommonDefines.WARDROBE_TRYON
									+ "/" + mAttireScheme.getAttireId()
									+ ".jpeg");
							if (a != null && a.exists()) {

								a.renameTo(new File(
										PathCommonDefines.WARDROBE_TRYON + "/"
												+ mAttireScheme.getAttireId()
												+ ".a"));
							}
							File b = new File(PathCommonDefines.WARDROBE_TRYON
									+ "/" + mAttireScheme.getAttireId()
									+ "b.png");
							if (b != null && b.exists()) {

								b.renameTo(new File(
										PathCommonDefines.WARDROBE_TRYON + "/"
												+ mAttireScheme.getAttireId()
												+ ".b"));
							}
							File c = new File(PathCommonDefines.WARDROBE_TRYON
									+ "/" + mAttireScheme.getAttireId()
									+ "s.png");
							if (c != null && c.exists()) {

								c.renameTo(new File(
										PathCommonDefines.WARDROBE_TRYON + "/"
												+ mAttireScheme.getAttireId()
												+ ".c"));
							}
							// 合成成功
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.COMPOUND_S);
						} else {
							switch (result) {
							// 没有找到面部
							case 1:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_FACE);
								break;
							// 没有找到身体
							case 2:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_BODY);
								break;
							// 头部太小
							case 3:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_SMALL_HEAD);
								break;
							// 头部距离上边框太近
							case 4:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_TOP_HEAD);
								break;
							// 头部距离左边框太近
							case 5:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_LEFT_HEAD);
								break;
							// 头部距离右边框太近
							case 6:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_RIGHT_HEAD);
								break;
							// 头部距离下边框太近
							case 7:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_bottom_HEAD);
								break;
							// 头部模糊
							case 8:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_DIM_HEAD);
								break;
							// 其他错误
							case 9:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
								break;
							}
						}
					}
				});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mCurrentWebView.doOnResume();
	}

	public void onMailTo(String url) {
		Intent sendMail = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(sendMail);
	}
}
