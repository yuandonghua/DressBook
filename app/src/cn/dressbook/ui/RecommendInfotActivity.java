package cn.dressbook.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.RecommendInfoAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.BuyerResponse;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.utils.BitmapTool;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.UriUtils;
import cn.dressbook.ui.view.SendPopupWindow;

/**
 * @description: 推荐详情
 * @author:袁东华
 * @time:2015-10-19上午11:50:44
 */
@ContentView(R.layout.recommendinfo)
public class RecommendInfotActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = RecommendInfotActivity.this;
	private RecommendInfoAdapter mRecommendInfoAdapter;
	private boolean flag;
	private InputMethodManager manager;
	private String ids, name, time, head, yjh, ch, pl, jw, buyer_id,
			buyer_name, buyer_time, buyer_head, buyer_yjh, buyer_attires,
			buyer_comment, buyer_zw, requirementUserId, buyerUserId;
	private ArrayList<AttireScheme> list;
	private ArrayList<BuyerResponse> list2;
	private AttireScheme mAttireScheme;
	private boolean pngFlag, xmlFlag, headFlag, headMaskFlag, neckMaskFlag;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 购物车
	 */
	@ViewInject(R.id.operate_iv)
	private ImageView operate_iv;
	/**
	 * 推荐语
	 */
	@ViewInject(R.id.tj_et)
	private EditText tj_et;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	@ViewInject(R.id.rl)
	private RelativeLayout rl;
	/**
	 * 喜欢按钮
	 */
	@ViewInject(R.id.xh_tv)
	private TextView xh_tv;
	/**
	 * 评论按钮
	 */
	@ViewInject(R.id.pl_tv)
	private TextView pl_tv;
	private SendPopupWindow mSendPopupWindow;
	private String isPraise;
	private Requirement rq;
	private BuyerResponse br = null;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("推荐详情");
		operate_iv.setImageResource(R.drawable.shoppingcart_src_2);
		operate_iv.setVisibility(View.VISIBLE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			requirementUserId = intent.getStringExtra("requirementUserId");
			buyerUserId = intent.getStringExtra("buyerUserId");
			name = intent.getStringExtra("name");
			time = intent.getStringExtra("time");
			head = intent.getStringExtra("head");
			yjh = intent.getStringExtra("yjh");
			ch = intent.getStringExtra("ch");
			pl = intent.getStringExtra("pl");
			jw = intent.getStringExtra("jw");
			buyer_id = intent.getStringExtra("buyer_id");
			buyer_name = intent.getStringExtra("buyer_name");
			buyer_time = intent.getStringExtra("buyer_time");
			buyer_head = intent.getStringExtra("buyer_head");
			buyer_yjh = intent.getStringExtra("buyer_yjh");
			buyer_zw = intent.getStringExtra("buyer_zw");
			buyer_attires = intent.getStringExtra("buyer_attires");
			buyer_comment = intent.getStringExtra("buyer_comment");
			ids = intent.getStringExtra("ids");
			isPraise = intent.getStringExtra("isPraise");
			// 获取推荐详情
			RequirementExec.getInstance().getRecommendInfoFromServer(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), buyer_id,
					NetworkAsyncCommonDefines.GET_RECOMMEND_S,
					NetworkAsyncCommonDefines.GET_RECOMMEND_F);
		}
		if ("0".equals(isPraise)) {
			Drawable rightDrawable = mContext.getResources().getDrawable(
					R.drawable.xh_src_1);
			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
					rightDrawable.getMinimumHeight());
			xh_tv.setCompoundDrawables(rightDrawable, null, null, null);

		} else if ("1".equals(isPraise)) {
			Drawable rightDrawable = mContext.getResources().getDrawable(
					R.drawable.xh_src_2);
			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
					rightDrawable.getMinimumHeight());
			xh_tv.setCompoundDrawables(rightDrawable, null, null, null);

		} else {
			Drawable rightDrawable = mContext.getResources().getDrawable(
					R.drawable.xh_src_1);
			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
					rightDrawable.getMinimumHeight());
			xh_tv.setCompoundDrawables(rightDrawable, null, null, null);
		}

		mRecommendInfoAdapter = new RecommendInfoAdapter(RecommendInfotActivity.this,mContext, mHandler);
		mRecommendInfoAdapter.setData(requirementUserId, buyerUserId, buyer_zw,
				buyer_attires, buyer_comment, buyer_name, buyer_time,
				buyer_head, buyer_yjh, name, time, head, yjh, ch, pl, jw);
		recyclerview.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerview.setLayoutManager(llm);
		recyclerview.setAdapter(mRecommendInfoAdapter);
	}

	@Event({ R.id.back_rl, R.id.tj_et, R.id.ok_tv, R.id.operate_iv, R.id.pl_tv,
			R.id.xh_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击评论
		case R.id.pl_tv:
			if (mSendPopupWindow == null) {
				mSendPopupWindow = new SendPopupWindow(this, null, mHandler);

			}
			// 显示窗口
			mSendPopupWindow.showAtLocation(rl, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		// 点击喜欢
		case R.id.xh_tv:
			if (isFinish()) {
				pbDialog.show();
				RequirementExec.getInstance().xhRecommend(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						buyer_id, NetworkAsyncCommonDefines.XH_S,
						NetworkAsyncCommonDefines.XH_F);
			} else {
				Toast.makeText(mContext, "正在处理,请稍后", Toast.LENGTH_SHORT).show();
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击购物车
		case R.id.operate_iv:
			Intent intent = new Intent(mContext, ShoppingCartActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private int mVisibleHeight;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 举报评论成功
			case NetworkAsyncCommonDefines.JB_COMMENT_S:
				Bundle jbcomData = msg.getData();
				if (jbcomData != null) {
					String recode = jbcomData.getString("recode");
					String redesc = jbcomData.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				LogUtil.e("buyer_id:" + buyer_id);
				// 获取推荐详情
				RequirementExec.getInstance().getRecommendInfoFromServer(
						mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						buyer_id, NetworkAsyncCommonDefines.GET_RECOMMEND_S,
						NetworkAsyncCommonDefines.GET_RECOMMEND_F);
				pbDialog.dismiss();
				break;
			// 举报评论失败
			case NetworkAsyncCommonDefines.JB_COMMENT_F:
				LogUtil.e("buyer_id:" + buyer_id);
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			// 喜欢评论成功
			case NetworkAsyncCommonDefines.XH_COMMENT_S:
				Bundle xhcomData = msg.getData();
				if (xhcomData != null) {
					String recode = xhcomData.getString("recode");
					String redesc = xhcomData.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				LogUtil.e("buyer_id:" + buyer_id);
				// 获取推荐详情
				RequirementExec.getInstance().getRecommendInfoFromServer(
						mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						buyer_id, NetworkAsyncCommonDefines.GET_RECOMMEND_S,
						NetworkAsyncCommonDefines.GET_RECOMMEND_F);
				pbDialog.dismiss();
				break;
			// 喜欢评论失败
			case NetworkAsyncCommonDefines.XH_COMMENT_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			// 发送成功
			case NetworkAsyncCommonDefines.SEND_COMMENT_S:
				FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.PAIZHAO));
				Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				mSendPopupWindow.dismiss();
				// 获取推荐详情
				RequirementExec.getInstance().getRecommendInfoFromServer(
						mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						buyer_id, NetworkAsyncCommonDefines.GET_RECOMMEND_S,
						NetworkAsyncCommonDefines.GET_RECOMMEND_F);
				break;
			// 发送成功
			case NetworkAsyncCommonDefines.SEND_COMMENT_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT).show();
				break;
			// 点击发送
			case NetworkAsyncCommonDefines.SEND_COMMENT:
				pbDialog.show();
				RequirementExec.getInstance().sendComment(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						buyer_id, mSendPopupWindow.words,
						mSendPopupWindow.list,
						NetworkAsyncCommonDefines.SEND_COMMENT_S,
						NetworkAsyncCommonDefines.SEND_COMMENT_F);
				break;
			// 发送提示
			case NetworkAsyncCommonDefines.PL_HINT:
				Bundle sendData = msg.getData();
				if (sendData != null) {
					String hint = sendData.getString("hint");
					Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
				}
				break;
			// 添加图片失败
			case NetworkAsyncCommonDefines.ADD_IMAGE_F:
				Toast.makeText(mContext, "最多添加9张图片", Toast.LENGTH_SHORT).show();
				break;
			// 点击试衣
			case NetworkAsyncCommonDefines.TRYON:
				Bundle tryon = msg.getData();
				if (tryon != null) {
					int position = tryon.getInt("position");
					mAttireScheme = list.get(position);
					pbDialog.show();
					tryOn();
				}

				break;
			// 喜欢成功
			case NetworkAsyncCommonDefines.XH_S:
				Bundle xhData = msg.getData();
				if (xhData != null) {
					String recode = xhData.getString("recode");
					String redesc = xhData.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				pbDialog.dismiss();
				Drawable rightDrawable = mContext.getResources().getDrawable(
						R.drawable.xh_src_2);
				rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
						rightDrawable.getMinimumHeight());
				xh_tv.setCompoundDrawables(rightDrawable, null, null, null);
				break;
			// 喜欢失败
			case NetworkAsyncCommonDefines.XH_F:
				pbDialog.dismiss();
				break;
			// 点击推荐
			case NetworkAsyncCommonDefines.CLICK_TJ:
				mRecommendInfoAdapter.notifyDataSetChanged();
				break;
			// 点击评论
			case NetworkAsyncCommonDefines.CLICK_PL:
				mRecommendInfoAdapter.notifyDataSetChanged();
				break;
			// 获取评论列表成功
			case NetworkAsyncCommonDefines.GET_COMMENT_S:
				Bundle commentlistData = msg.getData();
				if (commentlistData != null) {
					list2 = commentlistData.getParcelableArrayList("list");
					if (list2 != null && list2.size() > 0) {
					} else {
						list2 = new ArrayList<BuyerResponse>();

					}
					list2.add(0, null);
					mRecommendInfoAdapter.setList2(list2);
					mRecommendInfoAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				break;
			// 获取评论列表失败
			case NetworkAsyncCommonDefines.GET_COMMENT_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.GET_RECOMMEND_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					br = listData.getParcelable("br");
					rq = listData.getParcelable("rq");
					list = listData.getParcelableArrayList("list");
					list2 = listData.getParcelableArrayList("list2");
					if (list2 != null && list2.size() > 0) {
					} else {
						list2 = new ArrayList<BuyerResponse>();

					}
					if (list != null && list.size() > 0) {
					} else {
						list = new ArrayList<AttireScheme>();

					}
					list.add(0, null);
					list2.add(0, null);
					mRecommendInfoAdapter.setList(list);
					mRecommendInfoAdapter.setList2(list2);
					mRecommendInfoAdapter.setRQ(rq);
					mRecommendInfoAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				break;
			// 推荐失败
			case NetworkAsyncCommonDefines.GET_RECOMMEND_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
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
			default:
				break;
			}
		};
	};

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
		if (xxFile.exists()) {
			pbDialog.dismiss();
			mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.COMPOUND_S);
			return;
		}
		File head = new File(PathCommonDefines.WARDROBE_HEAD + "/head.0");
		if (!head.exists()) {
			String photo = mSharedPreferenceUtils.getWardrobePhoto(mContext);
			// 开始下载头像
			DownloadExec.getInstance().downloadFile(mHandler,
					photo + "/head.0",
					PathCommonDefines.WARDROBE_HEAD + "/head.0",
					NetworkAsyncCommonDefines.DOWNLOAD_HEAD_S,
					NetworkAsyncCommonDefines.DOWNLOAD_HEAD_F);
			// 开始下载headmask
			DownloadExec.getInstance().downloadFile(mHandler,
					photo + "/head.0maskhead.png",
					PathCommonDefines.WARDROBE_HEAD + "/head.0maskhead.png",
					NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_S,
					NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_F);
			// 开始下载neckmask
			DownloadExec.getInstance()
					.downloadFile(
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

	/**
	 * @description:开始合成图片
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
							case 9:
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
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 拍照的照片存储位置
	 */
	private File mPhotoDir;
	/**
	 * 照相机拍照得到的图片
	 */
	private File mCameraHead;
	/**
	 * 照片的名字
	 */
	private String mFileName = "camerahead1.jpg";
	/**
	 * 用来标识请求照相功能
	 */
	private static final int CAMERA_WITH_DATA = 2015;
	/**
	 * 用来标识裁剪功能
	 */
	private static final int CROP_BIG_PICTURE = 0x12;
	/**
	 * 临时图片名字
	 */
	private String mTemporaryName = "camerahead1.jpg";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 拍照返回
		case CAMERA_WITH_DATA:
			dealImage(mSendPopupWindow.fileName);

			break;
		// 裁剪返回
		case CROP_BIG_PICTURE:
			File f1 = new File(PathCommonDefines.PAIZHAO, mTemporaryName);
			if (f1.exists()) {
				if (FileSDCacher.fuZhiFile(f1, mCameraHead)) {
					// 裁剪后的图片复制成/paizhao/camerahead.0
					File t = new File(PathCommonDefines.PAIZHAO,
							"camerahead.jpg");
					if (FileSDCacher.fuZhiFile(mCameraHead, t)) {
						Toast.makeText(mContext, "正在检测图片是否合格,请耐心等待",
								Toast.LENGTH_LONG).show();
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								long result = DetectionBasedTracker
										.nativeHeadDetect(
												"paizhao/camerahead.jpg",
												"paizhao/camerahead1.jpg");
								if (result == 0) {
									mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CHECK_HEAD_EXIST);

								} else {
								}
							}
						}).start();
					}
				}
			} else {

			}
			break;
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
					dealImage(path);
				}
			} else {
				Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT).show();

			}

			break;
		}
	}

	private void dealImage(String fileName) {
		// TODO Auto-generated method stub
		try {
			Bitmap bitmap = BitmapTool.getInstance().createImage(fileName);
			if (bitmap != null) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				if (width > 800) {
					float scale = 800 / (float) width;
					Matrix matrix = new Matrix();
					matrix.postScale(scale, scale); // 长和宽放大缩小的比例
					Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width,
							height, matrix, true);
					File file = new File(mSendPopupWindow.fileName);
					if (file.exists()) {
						file.delete();
					}
					File file2 = new File(mSendPopupWindow.fileName);
					FileOutputStream out = new FileOutputStream(file2);
					if (bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
						if (bitmap != null) {
							bitmap.recycle();
						}
						if (bitmap2 != null) {
							bitmap2.recycle();
						}
						out.flush();
						out.close();
						mSendPopupWindow.refreshData();
					}

				} else {
					File file = new File(mSendPopupWindow.fileName);
					if (file.exists()) {
						file.delete();
					}
					boolean boo = FileSDCacher.fuZhiFile(new File(fileName),
							new File(mSendPopupWindow.fileName));
					if (boo) {
						mSendPopupWindow.refreshData();
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
