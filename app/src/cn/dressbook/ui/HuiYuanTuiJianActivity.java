package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.alibaba.sdk.android.callback.CallbackContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.YBHYAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.GuangGao;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.tb.TaoBaoUI;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 衣保会员
 * @author:袁东华
 * @time:2015-9-26下午7:20:34
 */
@ContentView(R.layout.ybhy)
public class HuiYuanTuiJianActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/**
	 * 商品view
	 */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	private Context mContext = HuiYuanTuiJianActivity.this;
	private YBHYAdapter mYBHYAdapter;
	private ArrayList<AttireScheme> mList;
	private boolean pngFlag, xmlFlag, headFlag, headMaskFlag, neckMaskFlag;
	private AttireScheme mAttireScheme;
	@ViewInject(R.id.hint_ll)
	private LinearLayout hint_ll;
	private List<AttireScheme> listSchemes;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("会员推荐");
		recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
		// 添加分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						this)
						.color(getResources().getColor(R.color.touming))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider5))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin)).build());
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		mYBHYAdapter = new YBHYAdapter(mContext, mHandler);
		// mYBHYAdapter.setData(mstjList, state);
		recyclerview.setAdapter(mYBHYAdapter);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				SchemeExec.getInstance().getYBHYFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_YBHY_S,
						NetworkAsyncCommonDefines.GET_YBHY_F);
			}
		});
		swiperefreshlayout.setRefreshing(true);
		pbDialog.show();

		String sexFlag = mSharedPreferenceUtils.getSex(mContext);
		String sex = "";
		if ("女".equals(sexFlag)) {
			sex = "2";
		} else {
			sex = "1";
		}
		// 获取广告信息
		SchemeExec.getInstance().getGGInfo(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), sex,
				NetworkAsyncCommonDefines.GET_GG_INFO_S,
				NetworkAsyncCommonDefines.GET_GG_INFO_F);
		// 获取衣保会员列表
		SchemeExec.getInstance().getYBHYFromServer(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_YBHY_S,
				NetworkAsyncCommonDefines.GET_YBHY_F);
	}

	@Event({ R.id.shoppingcart_iv, R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 点击购物车
		case R.id.shoppingcart_iv:

			pbDialog.show();
			Intent shoppingCartActivity = new Intent(mContext,
					ShoppingCartActivity.class);
			mContext.startActivity(shoppingCartActivity);
			((Activity) mContext).overridePendingTransition(R.anim.back_enter,
					R.anim.anim_exit);
			pbDialog.dismiss();
			break;
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取衣保会员成功
			case NetworkAsyncCommonDefines.GET_YBHY_S:

				Bundle data = msg.getData();
				if (data != null) {
					mList = data.getParcelableArrayList("list");
					if (ManagerUtils.getInstance().getShenFen(activity)
							.contains("@会员@")) {
						if (mList != null && mList.size() > 0) {
							mList.add(0, null);
							hint_ll.setVisibility(View.GONE);
						} else {
							if (mList == null) {
								mList = new ArrayList<AttireScheme>();
							}
							mList.add(0, null);
							hint_ll.setVisibility(View.VISIBLE);
							String html = "  专业着装顾问将为你量身推荐适合你的服装，请耐心等待。";
							// html +=
							// "详情请查看<font color='#247ce0'><b><big><u>衣保银行介绍</u></big></b>。</font><br>";
							CharSequence charSequence = Html.fromHtml(html);
							hint_tv.setText(charSequence);
							// 该语句在设置后必加，不然没有任何效果
							hint_tv.setMovementMethod(LinkMovementMethod
									.getInstance());
						}
					}

				}
				mYBHYAdapter.setData(mList);
				mYBHYAdapter.notifyDataSetChanged();
				swiperefreshlayout.setRefreshing(false);
				pbDialog.dismiss();
				break;
			// 获取衣保会员失败
			case NetworkAsyncCommonDefines.GET_YBHY_F:
				pbDialog.dismiss();
				break;
			// 加载淘宝商品
			case NetworkAsyncCommonDefines.LOAD_TB_SHANGPIN:
				Bundle tbData = msg.getData();
				if (tbData != null) {
					String openiid = tbData.getString("openiid");
					TaoBaoUI.getInstance().showItemDetailPage(
							HuiYuanTuiJianActivity.this, openiid);
				}
				break;
			// 下载试衣形象照成功
			case NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_S:
				pbDialog.dismiss();
				String url = PathCommonDefines.WARDROBE_TRYON + "/"
						+ mAttireScheme.getAttireId() + ".a";
				Intent tryOnInfoActivity2 = new Intent(
						HuiYuanTuiJianActivity.this, TryOnInfoActivity.class);
				tryOnInfoActivity2.putExtra("path", url);
				startActivity(tryOnInfoActivity2);
				break;
			// 下载试衣形象照失败
			case NetworkAsyncCommonDefines.DOWNLOAD_SY_SERVER_F:
				downloadFlag = true;
				tryOn();
				break;
			// 获取广告信息成功
			case NetworkAsyncCommonDefines.GET_GG_INFO_S:
				Bundle ggData = msg.getData();
				if (ggData != null) {
					List<GuangGao> ggList = ggData
							.getParcelableArrayList("list");

					if (ggList != null && ggList.size() > 0) {

						mYBHYAdapter.setGGData(ggList.get(0));
					}
				}
				mYBHYAdapter.notifyItemInserted(0);
				break;
			// 获取广告信失败
			case NetworkAsyncCommonDefines.GET_GG_INFO_F:

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
				neckMaskFlag = false;
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
				headMaskFlag = false;
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
				pngFlag = false;
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
				xmlFlag = false;
				break;
			// 合成成功
			case NetworkAsyncCommonDefines.COMPOUND_S:
				pbDialog.dismiss();
				String imageUrl = PathCommonDefines.WARDROBE_TRYON + "/"
						+ mAttireScheme.getAttireId() + ".a";
				// 上传试衣形象
				Intent service = new Intent(HuiYuanTuiJianActivity.this,
						DownLoadingService.class);
				service.putExtra("id",
						NetworkAsyncCommonDefines.UPDATELOAD_SY_XX);
				service.putExtra("path", imageUrl);
				startService(service);
				Intent tryOnInfoActivity = new Intent(
						HuiYuanTuiJianActivity.this, TryOnInfoActivity.class);
				tryOnInfoActivity.putExtra("path", imageUrl);
				startActivity(tryOnInfoActivity);
				break;
			// 合成失败
			case NetworkAsyncCommonDefines.COMPOUND_F:
				pbDialog.dismiss();
				break;
			// 点击试衣
			case NetworkAsyncCommonDefines.TRYON:
				Bundle tryon = msg.getData();
				if (tryon != null) {
					int position = tryon.getInt("position");
					mAttireScheme = mList.get(position);
					pbDialog.show();
					tryOn();
				}

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
				Toast.makeText(getApplicationContext(), "未知错误,请重新设置头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				break;
			default:
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
		// 本地存在
		File xxFile = new File(imageUrl);
		if (xxFile.exists()) {
			pbDialog.dismiss();
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
			File head = new File(PathCommonDefines.WARDROBE_HEAD + "/head.0");
			if (!head.exists()) {
				String photo = mSharedPreferenceUtils
						.getWardrobePhoto(mContext);
				// 开始下载头像
				DownloadExec.getInstance().downloadFile(mHandler,
						photo + "/head.0",
						PathCommonDefines.WARDROBE_HEAD + "/head.0",
						NetworkAsyncCommonDefines.DOWNLOAD_HEAD_S,
						NetworkAsyncCommonDefines.DOWNLOAD_HEAD_F);
				// 开始下载headmask
				DownloadExec.getInstance()
						.downloadFile(
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
				return;
			}
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
					pngFlag = false;
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
					xmlFlag = false;
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

	private void compoundImage() {
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						long time1 = System.currentTimeMillis();
						LogUtil.e("开始穿衣");
						int result = (int) DetectionBasedTracker
								.nativeMergeBody(
										"wardrobe/head/head.0",
										"wardrobe/mote/"
												+ mAttireScheme.getAttireId()
												+ ".png", "wardrobe/tryOn/"
												+ mAttireScheme.getAttireId()
												+ ".jpeg");
						long time2 = System.currentTimeMillis();
						LogUtil.e("穿衣结束-result:" + result + "  耗时:"
								+ (time2 - time1) / 1000);
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
							String pngPath = PathCommonDefines.WARDROBE_MOTE
									+ "/" + mAttireScheme.getAttireId()
									+ ".png";

							File pngFile = new File(pngPath);
							pngFile.delete();
							String xmlPath = PathCommonDefines.WARDROBE_MOTE
									+ "/" + mAttireScheme.getAttireId()
									+ ".png.xml";

							File xmlFile = new File(pngPath);
							xmlFile.delete();
							// 合成成功
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.COMPOUND_S);
						} else {
							switch (result) {
							// 头像处理成功
							case 0:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_S);
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
							default:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
								break;
							}

						}

					}
				});
	}

	/**
	 * @description:扣头
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
							default:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
								break;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		CallbackContext.onActivityResult(requestCode, resultCode, data);
	}
}
