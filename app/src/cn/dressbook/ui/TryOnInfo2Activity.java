package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.TryOnInfo2Adapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.ShareExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.view.VerticalSeekBar;
import cn.dressbook.ui.view.VerticalSeekBar.OnSeekBarChangeListener;

import com.umeng.analytics.social.UMSocialService;

/**
 * @description: 试衣收藏大图展示
 * @author:袁东华
 * @time:2015-11-10下午2:49:14
 */
@ContentView(R.layout.tryoninfo2)
public class TryOnInfo2Activity extends BaseActivity {
	private Context mContext = TryOnInfo2Activity.this;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.viewpager)
	private ViewPager viewpager;
	private ArrayList<String> mList = new ArrayList<String>();
	private int position = 0;
	private TryOnInfo2Adapter mTryOnInfo2Adapter;
	/**
	 * 高矮
	 */
	private VerticalSeekBar left_sb;
	/**
	 * 胖瘦
	 */
	private VerticalSeekBar right_sb;
	private String url = null;
	private int height, weight;
	private String inputFilePath, outputFilePath, curFilePath, fileName;
	private String title, content, pic, uploadPath, sharePath;
	private UMSocialService mController;
	private boolean isShow = true;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("试衣收藏");
		left_sb=null;
		left_sb=(VerticalSeekBar) findViewById(R.id.left_sb);
		right_sb=null;
		right_sb=(VerticalSeekBar) findViewById(R.id.right_sb);
		// 滑动切换
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				pbDialog.show();
				position = arg0;
				left_sb=null;
				left_sb=(VerticalSeekBar) findViewById(R.id.left_sb);
				right_sb=null;
				right_sb=(VerticalSeekBar) findViewById(R.id.right_sb);
				mHandler.sendEmptyMessage(1);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 调节高矮
		left_sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(int progress) {
				// TODO Auto-generated method stub
				if (progress > 5) {
					progress = 5;
				}
				if (progress < 0) {
					progress = 0;
				}
				pbDialog.show();
				left_sb.setEnabled(false);
				right_sb.setEnabled(false);
				height = progress;
				startBS();
				left_sb.setEnabled(true);
				right_sb.setEnabled(true);
			}
		});
		// // // 调节胖瘦
		right_sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(int progress) {
				// TODO Auto-generated method stub
				if (progress > 5) {
					progress = 5;
				}
				if (progress < 0) {
					progress = 0;
				}
				pbDialog.show();
				left_sb.setEnabled(false);
				right_sb.setEnabled(false);
				weight = progress;
				startBS();
				left_sb.setEnabled(true);
				right_sb.setEnabled(true);
			}

		});
	}

	/**
	 * @description:开始变身
	 */
	protected void startBS() {
		// TODO Auto-generated method stub
		// 没有变身
		if (height == 0 && weight == 0) {
			if (curFilePath != null) {

				File file = new File(curFilePath);
				if (file.exists()) {
					file.delete();
				}
			}
			mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.NO_BS);
		} else {
			if (curFilePath != null) {
				File file = new File(curFilePath);
				if (file.exists()) {
					file.delete();
				}
			}
			// 变身了
			// 原文件路径
			inputFilePath = "wardrobe/tryOn"
					+ url.substring(url.lastIndexOf("/"));
			// 目标文件路径
			outputFilePath = inputFilePath.replace(".a", "_" + height + "_"
					+ weight + ".jpeg");
			long result = DetectionBasedTracker.nativeHumanWarps(inputFilePath,
					outputFilePath, height, weight);
			if (result == 0) {
				curFilePath = url.replace(".a", "_" + height + "_" + weight
						+ ".a");
				File file = new File(url.replace(".a", "_" + height + "_"
						+ weight + ".jpeg"));
				if (file.renameTo(new File(curFilePath))) {
					mList.remove(position);
					mList.add(position, curFilePath);
					mTryOnInfo2Adapter.setData(mList);
					mTryOnInfo2Adapter.notifyDataSetChanged();
					viewpager.setAdapter(mTryOnInfo2Adapter);
					viewpager.setCurrentItem(position);
				}
				pbDialog.dismiss();
			} else {
				Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
			}

		}
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			position = intent.getIntExtra("position", 0);
			mList = intent.getStringArrayListExtra("list");
		}

		mTryOnInfo2Adapter = new TryOnInfo2Adapter(mHandler);
		mTryOnInfo2Adapter.setData(mList);
		viewpager.setAdapter(mTryOnInfo2Adapter);
		viewpager.setCurrentItem(position);
		setPosition();
		String path = mList.get(position);
		if (path.contains("_")) {
			String str[] = path.split("_");
			url = str[0] + ".a";
		} else {
			url = path;
		}
	}

	/**
	 * @description:设置高矮胖瘦的位置
	 */
	private void setPosition() {
		// TODO Auto-generated method stub

		String path = mList.get(position);
		if (path.contains("_")) {
			LogUtil.e("变身图片");
			path = path.replace(".a", "");
			String hw[] = path.split("_");
			height = Integer.parseInt(hw[1]);
			weight = Integer.parseInt(hw[2]);
			LogUtil.e("height:" + height);
			LogUtil.e("weight:" + weight);
			left_sb.setProgress(height);
			right_sb.setProgress(weight);
			LogUtil.e("progress1:" + left_sb.getProgress());
			LogUtil.e("progress2:" + right_sb.getProgress());
			curFilePath = mList.get(position);
			url = hw[0] + ".a";

		} else {
			LogUtil.e("原图");
			height = 0;
			weight = 0;
			left_sb.setProgress(height);
			right_sb.setProgress(weight);
			curFilePath = null;
			url = path;
		}
		pbDialog.dismiss();
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 刷新进度条
			case 1:
				setPosition();
				break;
			case NetworkAsyncCommonDefines.ONCLICK_TRYON_IMAGE:
				if (isShow) {
					isShow = false;
					left_sb.setVisibility(View.GONE);
					right_sb.setVisibility(View.GONE);
				} else {
					isShow = true;
					left_sb.setVisibility(View.VISIBLE);
					right_sb.setVisibility(View.VISIBLE);
				}
				break;
			// 获取分享内容成功
			case NetworkAsyncCommonDefines.GET_SHARE_S:
				Bundle data = msg.getData();
				if (data != null) {
					title = data.getString("title");
					content = data.getString("content");
					pic = data.getString("pic");
					uploadPath = data.getString("uploadPath");
					sharePath = data.getString("sharePath");
					Intent intent = new Intent(mContext,
							DownLoadingService.class);
					if (curFilePath != null) {
						intent.putExtra("uploadFile", curFilePath);
					} else {
						intent.putExtra("uploadFile", url);
					}
					intent.putExtra("uploadPath", uploadPath);
					intent.putExtra("id",
							NetworkAsyncCommonDefines.UPLOAD_TRYON_IMAGE);
					startService(intent);
					pbDialog.dismiss();
					shareAll();
				}
				break;
			// 获取分享内容失败
			case NetworkAsyncCommonDefines.GET_SHARE_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
				break;
			// 分享
			case NetworkAsyncCommonDefines.START_FENXIANG_S:
				pbDialog.show();
				String filePath = mList.get(position);
				fileName = filePath.substring(filePath.lastIndexOf("/") + 1)
						.replace(".a", "");
				// 获取分享内容
				ShareExec.getInstance().getShareTryOnContent(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						fileName, NetworkAsyncCommonDefines.GET_SHARE_S,
						NetworkAsyncCommonDefines.GET_SHARE_F);

				break;
			// 展示原图
			case NetworkAsyncCommonDefines.NO_BS:
				String path = mList.get(position);
				String path1;
				if (path.contains("_")) {
					String str[] = path.split("_");
					path1 = str[0] + ".a";
					mList.remove(position);
					mList.add(position, path1);
				} else {
					path1 = path;
				}
				mTryOnInfo2Adapter.setData(mList);
				mTryOnInfo2Adapter.notifyDataSetChanged();
				viewpager.setAdapter(mTryOnInfo2Adapter);
				viewpager.setCurrentItem(position);
				pbDialog.dismiss();
				break;
			// 删除成功
			case NetworkAsyncCommonDefines.DELETE_TRYON_S:
				if (mList != null && mList.size() > position) {
					Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
					mList.remove(position);
					if (mList.size() > position) {
						mTryOnInfo2Adapter.setData(mList);
						mTryOnInfo2Adapter.notifyDataSetChanged();
						viewpager.setAdapter(mTryOnInfo2Adapter);
						viewpager.setCurrentItem(position);
					} else if (mList.size() > 0) {
						mTryOnInfo2Adapter.setData(mList);
						mTryOnInfo2Adapter.notifyDataSetChanged();
						viewpager.setAdapter(mTryOnInfo2Adapter);
						viewpager.setCurrentItem(mList.size() - 1);
						position = mList.size() - 1;
					} else {
						mTryOnInfo2Adapter.setData(mList);
						mTryOnInfo2Adapter.notifyDataSetChanged();
					}

				} else {
					mTryOnInfo2Adapter.setData(mList);
					mTryOnInfo2Adapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @description:分享到所有平台
	 */
	protected void shareAll() {
		Intent intent = new Intent(mContext, MyShareActivity.class);
		intent.putExtra("targeturl", sharePath);
		intent.putExtra("content", content);
		intent.putExtra("title", title);
		intent.putExtra("pic", pic);
		startActivity(intent);

	}

	/**
	 * @description:分享到微信朋友圈
	 */
	/*
	 * protected void shareWXCircle() { // TODO Auto-generated method stub if
	 * (mController == null) { mController = UMServiceFactory
	 * .getUMSocialService("com.umeng.share"); } // 支持微信朋友圈 UMWXHandler
	 * wxCircleHandler = new UMWXHandler(mContext, Constants.APP_ID,
	 * Constants.AppSecret); if (wxCircleHandler.isClientInstalled()) {
	 * wxCircleHandler.setToCircle(true); wxCircleHandler.addToSocialSDK();
	 * CircleShareContent circleMedia = new CircleShareContent(); UMImage image
	 * = new UMImage(mContext, R.drawable.tryon_share_1);
	 * circleMedia.setShareImage(image); circleMedia.setTargetUrl(sharePath);
	 * circleMedia.setShareContent(content); circleMedia.setTitle(title);
	 * mController.setShareMedia(circleMedia); // 参数1为Context类型对象，
	 * 参数2为要分享到的目标平台， 参数3为分享操作的回调接口 mController.postShare(mContext,
	 * SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
	 * 
	 * @Override public void onStart() { // Toast.makeText(mContext, "开始分享.", //
	 * Toast.LENGTH_SHORT) // .show(); }
	 * 
	 * @Override public void onComplete(SHARE_MEDIA platform, int eCode,
	 * SocializeEntity entity) { if (eCode == 200) { } else { String eMsg = "";
	 * if (eCode == -101) { eMsg = "没有授权"; } } pbDialog.dismiss(); } }); } else
	 * { pbDialog.dismiss(); Toast.makeText(mContext, "请安装微信",
	 * Toast.LENGTH_LONG).show(); } }
	 */
}
