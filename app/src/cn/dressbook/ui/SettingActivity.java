package cn.dressbook.ui;

import java.io.File;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.dialog.AboutUsDialog;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.tb.TaoBaoUI;
import cn.dressbook.ui.utils.FileSDCacher;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * @description: 设置
 * @author:袁东华
 * @time:2015-12-1下午2:39:15
 */
@ContentView(R.layout.moreoperation)
public class SettingActivity extends BaseActivity {
	private AboutUsDialog mAboutUsDialog;
	private Context mContext = SettingActivity.this;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("设置");
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Event({  R.id.back_rl, R.id.clearcache_rl, R.id.jcgx_rl,
			R.id.gywm_rl, R.id.yhfk_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击清理缓存
		case R.id.clearcache_rl:
			clearCache();
			break;
		// 点击检查更新
		case R.id.jcgx_rl:

			pbDialog.show();
			// banben_iv.setEnabled(false);
			updateVersion();
			break;
		// 点击关于我们
		case R.id.gywm_rl:
			if (mAboutUsDialog == null) {
				mAboutUsDialog = new AboutUsDialog(mContext);
			}
			mAboutUsDialog.show();
			break;
		// 点击用户反馈
		case R.id.yhfk_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent yhfk = new Intent(this, UserFeedbackActivity.class);
				startActivity(yhfk);
			} else {
				pbDialog.show();
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);

				pbDialog.dismiss();
			}

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
			// 清理缓存成功
			case NetworkAsyncCommonDefines.CLEAR_CACHE_S:
				pbDialog.dismiss();
				Intent service = new Intent(mContext, DownLoadingService.class);
				service.putExtra("id",
						NetworkAsyncCommonDefines.GET_USER_ALL_DATA);
				startService(service);
				Toast.makeText(mContext, "清理完毕", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * @description: 清除缓存
	 * @exception
	 */
	protected void clearCache() {
		// TODO Auto-generated method stub
		pbDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File JSON_FOLDER = new File(PathCommonDefines.JSON_FOLDER);
				FileSDCacher.deleteDirectory2(JSON_FOLDER);
				File HEAD = new File(PathCommonDefines.HEAD);
				FileSDCacher.deleteDirectory2(HEAD);
				File PAIZHAO = new File(PathCommonDefines.PAIZHAO);
				FileSDCacher.deleteDirectory2(PAIZHAO);
				File XINGXIANG = new File(PathCommonDefines.XINGXIANG);
				FileSDCacher.deleteDirectory2(XINGXIANG);
				File photo_cache = new File(PathCommonDefines.PHOTOCACHE_FOLDER);
				FileSDCacher.deleteDirectory2(photo_cache);
				FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.WARDROBE_HEAD));
				FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.WARDROBE_TRYON));
				FileSDCacher.deleteDirectory2(new File(
						PathCommonDefines.WARDROBE_MOTE));
				File life = new File(PathCommonDefines.LIFEPHOTOS);
				FileSDCacher.deleteDirectory2(life);
				File lstj = new File(PathCommonDefines.LSTJ);
				FileSDCacher.deleteDirectory2(lstj);
				FileSDCacher.deleteDirectory2(life);
				File mxz = new File(PathCommonDefines.MINGXINGZHAO);
				FileSDCacher.deleteDirectory2(mxz);
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLEAR_CACHE_S);
			}
		}).start();

	}

	/**
	 * 
	 * @description 版本更新
	 * @version
	 * @author
	 * @update 2013-12-31 上午11:28:49
	 */
	private void updateVersion() {
		// TODO Auto-generated method stub
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				switch (updateStatus) {
				// 版本更新
				case 0:
					pbDialog.dismiss();
					// Log.i(TAG, "返回结果码：" + updateStatus);
					UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
					// Log.i(TAG, "返回更新信息：version=" + updateInfo.version
					// + "updateLog=" + updateInfo.updateLog);
					// Log.i(TAG, "返回更新信息：hasUpdate=" + updateInfo.hasUpdate);
					// Log.i(TAG, "返回更新信息：path=" + updateInfo.path);
					// banben_iv.setEnabled(true);
					break;
				// 不更新
				case 1:
					pbDialog.dismiss();
					// Log.i(TAG, "返回结果码：" + updateStatus);
					Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT)
							.show();
					// Log.i(TAG, "返回更新信息：version=" + updateInfo.version
					// + "updateLog=" + updateInfo.updateLog);
					// Log.i(TAG, "返回更新信息：hasUpdate=" + updateInfo.hasUpdate);

					// banben_iv.setEnabled(true);
					break;
				// wifi情况下更新
				case 2:
					pbDialog.dismiss();
					// Log.i(TAG, "返回结果码：" + updateStatus);
					Toast.makeText(mContext, "没有wifi连接", Toast.LENGTH_SHORT)
							.show();
					// Log.i(TAG, "返回更新信息：version=" + updateInfo.version
					// + "updateLog=" + updateInfo.updateLog);
					// banben_iv.setEnabled(true);
					break;
				// 更新超时
				case 3:
					pbDialog.dismiss();
					// Log.i(TAG, "返回结果码：" + updateStatus);
					Toast.makeText(mContext, "更新超时", Toast.LENGTH_SHORT).show();
					// Log.i(TAG, "返回更新信息：version=" + updateInfo.version
					// + "updateLog=" + updateInfo.updateLog);
					// banben_iv.setEnabled(true);
					break;
				// 正在更新
				case 4:
					pbDialog.dismiss();
					Toast.makeText(mContext, "正在下载更新...", Toast.LENGTH_SHORT)
							.show();
					// Log.i(TAG, "返回更新信息：version=" + updateInfo.version
					// + "updateLog=" + updateInfo.updateLog);
					// banben_iv.setEnabled(true);
					break;
				}
			}
		});
		UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

			@Override
			public void onClick(int status) {
				switch (status) {
				case UpdateStatus.Update:
					// Log.i(TAG,
					// "选择了更新————————————————————————————————————UpdateStatus.Update="
					// + UpdateStatus.Update);
					break;
				case UpdateStatus.Ignore:
					// Log.i(TAG,
					// "选择了取消————————————————————————————————————UpdateStatus.Ignore="
					// + UpdateStatus.Ignore);
					// banben_iv.setEnabled(true);
					break;
				case UpdateStatus.NotNow:
					// Log.i(TAG,
					// "选择了忽略————————————————————————————————————UpdateStatus.NotNow="
					// + UpdateStatus.NotNow);
					// banben_iv.setEnabled(true);
					break;
				}
			}
		});
		UmengUpdateAgent.forceUpdate(mContext);

	}
}
