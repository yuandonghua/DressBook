package cn.dressbook.ui.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Wardrobe;
import cn.dressbook.ui.net.DeleteExec;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.net.UploadExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.WardrobeExec;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

public class DownLoadingService extends IntentService {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private int xx_id;
	private Context mContext = DownLoadingService.this;
	private Wardrobe mWardrobe;
	private int breakFlag;
	private static boolean isDealHead;

	public DownLoadingService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public DownLoadingService() {
		super("DownLoadingService");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// LogUtils.e("服务创建--------------------------");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		// LogUtils.e("onHandleIntent-------------------------");
		xx_id = 0;

		int result = intent.getIntExtra("id", -3);

		xx_id = intent.getIntExtra("xx_id", 0);
		mWardrobe = intent.getParcelableExtra("xx");
		File file = new File(PathCommonDefines.MOTE);
		if (!file.exists()) {
			file.mkdirs();
		} else {
		}
		switch (result) {
		// 删除服务端试衣形象
		case NetworkAsyncCommonDefines.DELETE_SY_SERVER:
			DeleteExec.getInstance().deleteServerSY(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), 0, 0);
			break;
		// 上传试衣形象保存在服务端
		case NetworkAsyncCommonDefines.UPDATELOAD_SY_XX:
			String path = intent.getStringExtra("path");
			String tryResult = mSharedPreferenceUtils
					.getTryResultSave(mContext);
			LogUtil.e(path);
			LogUtil.e(tryResult);
			if (!"no".equals(tryResult)) {

				UploadExec.getInstance().uploadFile(null,
						PathCommonDefines.UPDATELOAD_SY_FILE,
						ManagerUtils.getInstance().getUser_id(mContext), path,
						NetworkAsyncCommonDefines.UPDATELOAD_SY_XX_S,
						NetworkAsyncCommonDefines.UPDATELOAD_SY_XX_F);
			}
			break;
		// 上传试衣图片
		case NetworkAsyncCommonDefines.UPLOAD_TRYON_IMAGE:
			String uploadPath = intent.getStringExtra("uploadPath");
			String uploadFile = intent.getStringExtra("uploadFile");
			if (uploadPath != null) {
				UploadExec.getInstance().uploadTryOnImage(null, uploadPath,
						uploadFile,
						NetworkAsyncCommonDefines.UPLOAD_TRYON_IMAGE_S,
						NetworkAsyncCommonDefines.UPLOAD_TRYON_IMAGE_F);
			}
			break;
		// 获取用户信息
		case NetworkAsyncCommonDefines.GET_USER_ALL_DATA:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				WardrobeExec.getInstance().getWardrobe(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_WARDROBE_S,
						NetworkAsyncCommonDefines.GET_WARDROBE_F);
			}
			break;
		// 上传用户头像
		case NetworkAsyncCommonDefines.SERVICE_UPLOAD_HEADIMAGE:
			// 退出应用
		case -1:

			break;
		// 启动应用
		case NetworkAsyncCommonDefines.START_APP:
			ManagerUtils.getInstance().downloadMTFile();
			// 复制配置文件
			setConfigXML();
			yanZhengSheBeiHao();
			break;

		// 删除相册
		case 6:
			File f = new File(PathCommonDefines.XIANGCE + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ "xc");
			FileSDCacher.deleteDirectory(f);
			FileSDCacher.deleteDirectory(f);
			break;
		// 保存浏览的方案ID
		case 7:
			String[] ids = intent.getStringArrayExtra("ids");
			if (ids != null && ids.length > 0) {
				StringBuilder json = new StringBuilder();
				for (int i = 0; i < ids.length; i++) {
					json.append(ids[i]);
					if (i != (ids.length - 1)) {
						json.append(",");

					}
				}
				String suffix = "id.txt";
				String str = json.toString();
				FileSDCacher.createFile(mHandler, str, suffix, ManagerUtils
						.getInstance().getUser_id(mContext), 100);

			}
			break;
		// 返回桌面
		case 8:
			// 删除拍照目录下的文件
			FileSDCacher.deleteDirectory2(new File(PathCommonDefines.PAIZHAO));
			// 删除相册目录下的文件
			FileSDCacher.deleteDirectory2(new File(PathCommonDefines.XIANGCE));
			// 删除形象目录下的文件
			FileSDCacher
					.deleteDirectory2(new File(PathCommonDefines.XINGXIANG));
			break;
		// 删除形象的缓存文件
		case 9:
			File deletePic = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ xx_id);
			FileSDCacher.deleteDirectory(deletePic);
			File deleteJson = new File(PathCommonDefines.JSON_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ xx_id);
			FileSDCacher.deleteDirectory(deleteJson);
			break;
		// 删除方案
		case 12:
			// LogUtils.e("case 12--------------------");
			String attire_id = intent.getStringExtra("attire_id");
			int xx_id = intent.getIntExtra("xx_id", 0);
			String fileName = PathCommonDefines.JSON_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ "delete_" + xx_id + ".txt";
			File deletefile = new File(fileName);
			if (deletefile.exists()) {
				FileSDCacher.appendContent(fileName, "," + attire_id);
			} else {
				FileSDCacher.createFile2(
						null,
						attire_id,
						PathCommonDefines.JSON_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext), "delete_" + xx_id + ".txt",
						100);
			}

			break;
		// 删除用户
		case 13:
			ArrayList<Wardrobe> xx_list = intent
					.getParcelableArrayListExtra("xx_list");

			File photocache = new File(PathCommonDefines.PHOTOCACHE_FOLDER);
			if (photocache.exists()) {

				File[] files = photocache.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						// LogUtils.e("用户的文件名字：" + files[i].getName());
						if (!files[i].getName().equals("3")
								&& !files[i].getName().equals(
										ManagerUtils.getInstance().getUser_id(
												mContext)
												+ "")
								&& !files[i].getName().equals("sjslb")) {
							// LogUtils.e("删除的文件名：" + files[i].getName());
							FileSDCacher.deleteDirectory(files[i]);
						}
					}
				}
			}
			if (xx_list != null) {
				File yigui = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
						+ ManagerUtils.getInstance().getUser_id(mContext));
				if (yigui.exists()) {
					File[] files = yigui.listFiles();
					if (files != null) {
						for (int j = 0; j < files.length; j++) {
							// LogUtils.e("衣柜的文件名：" + files[j].getName());
							boolean flag = false;
							for (int i = 0; i < xx_list.size(); i++) {
								if (files[j].getName().equals(
										xx_list.get(i).getWardrobeId() + "")) {
									flag = true;
								}

							}
							if (!flag) {
								// LogUtils.e("删除的文件名：" + files[j].getName());
								FileSDCacher.deleteDirectory(files[j]);
							}

						}

					}

				}
			}
			break;

		// 删除文件夹中的图片
		case 15:
			File rootfile = new File(PathCommonDefines.APP_FOLDER_ON_SD);
			if (rootfile == null || !rootfile.exists()) {
				rootfile.mkdirs();
			}
			break;
		}
	}

	/**
	 * @description 删除无用的文件夹
	 * @parameters
	 */
	private void deleteUselessDirectory() {
		// TODO Auto-generated method stub
		// deleteJson();

	}

	/**
	 * @description 删除非用户的json文件
	 * @parameters
	 */
	private void deleteJson() {
		// TODO Auto-generated method stub
		File file1 = new File(PathCommonDefines.JSON_FOLDER);
		if (file1.exists()) {
			File[] files = file1.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						String name = file.getName();
						if (!name.equals(ManagerUtils.getInstance().getUser_id(
								mContext)
								+ "")) {
							FileSDCacher.deleteDirectory(file);
						}

					}
				}
			}
		}

	}

	/**
	 * @description 复制头像和XML,形象照
	 * @parameters
	 */
	private void fuZhi() {
		// TODO Auto-generated method stub
		// LogUtils.e("复制头像和形象照成功------------------");
		// 复制创建形象时的拍照图片和xml文件到改形象目录下面
		File f1 = new File(PathCommonDefines.PAIZHAO + "/camerahead.jpg");
		File f11 = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ ManagerUtils.getInstance().getCreateXXID()
				+ "/head/camerahead.jpg");
		FileSDCacher.fuZhiFile(f1, f11);
		File f2 = new File(PathCommonDefines.PAIZHAO + "/camerahead.jpg.xml");
		File f22 = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ ManagerUtils.getInstance().getCreateXXID()
				+ "/head/camerahead.jpg.xml");
		FileSDCacher.fuZhiFile(f2, f22);
		File f3 = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/"
				+ "xingxiang" + "/" + "xingxiang.jpeg");
		File f33 = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ ManagerUtils.getInstance().getCreateXXID()
				+ "/head/xingxiang.jpeg");
		FileSDCacher.fuZhiFile(f3, f33);
	}

	private int kouTou(String path) {
		final long time1 = System.currentTimeMillis();
		// LogUtils.e("正在扣头----------------------------");
		final int msg = (int) DetectionBasedTracker.nativeMattingHead(path);
		// LogUtils.e("扣头结果：" + msg);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long time2 = System.currentTimeMillis();
				// LogUtils.e("用时：" + (time2 - time1));
				switch (msg) {
				// 头像处理成功
				case 0:
					Toast.makeText(getApplicationContext(), "头像处理成功",
							Toast.LENGTH_LONG).show();
					break;
				// 没有找到面部
				case 1:
					Toast.makeText(getApplicationContext(),
							"面部太暗,没有找到面部,请重新拍照", Toast.LENGTH_LONG).show();
					File file1 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file1);
					break;
				// 没有找到身体
				case 2:
					Toast.makeText(getApplicationContext(),
							"没有找到符合的身体,请重新提交数据", Toast.LENGTH_LONG).show();
					File file2 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file2);
					break;
				// 头部太小
				case 3:
					Toast.makeText(getApplicationContext(), "头部太小,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file3 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file3);
					break;
				// 头部距离上边框太近
				case 4:
					Toast.makeText(getApplicationContext(), "头部距离上边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file4 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file4);
					break;
				// 头部距离左边框太近
				case 5:
					Toast.makeText(getApplicationContext(), "头部距离左边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file5 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file5);
					break;
				// 头部距离右边框太近
				case 6:
					Toast.makeText(getApplicationContext(), "头部距离右边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file6 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file6);
					break;
				// 头部距离下边框太近
				case 7:
					Toast.makeText(getApplicationContext(), "头部距离下边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file7 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file7);
					break;
				// 头部模糊
				case 8:
					Toast.makeText(getApplicationContext(), "头部太模糊,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file8 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file8);
					break;
				// 其他错误
				case 9:
					Toast.makeText(getApplicationContext(), "头部处理错误,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file9 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					FileSDCacher.deleteDirectory(file9);
					break;
				}
			}
		});
		return msg;
	}

	private boolean headFlag, headMaskFlag, neckMaskFlag;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 检查更新文件成功
			case NetworkAsyncCommonDefines.CHECK_FILE_UPDATE_S:
				Bundle updateData = msg.getData();
				if (updateData != null) {
					String model_update_date = updateData
							.getString("model_update_date");
					String lastModelUpdateDate = mSharedPreferenceUtils
							.getUpdataTimeMT(mContext);
					mSharedPreferenceUtils.setUpdataTimeMT(mContext,
							model_update_date);
					String wardrobe_time = updateData
							.getString("wardrobe_time");
					String lastwardrobe_time = mSharedPreferenceUtils
							.getUpdataTimeWardrobe(mContext);
					mSharedPreferenceUtils.setUpdataTimeWardrobe(mContext,
							wardrobe_time);
					// 更新模特文件
					if (!model_update_date.equals(lastModelUpdateDate)) {
						File file = new File(PathCommonDefines.MOTE);
						if (FileSDCacher.deleteDirectory2(file)) {
							ManagerUtils.getInstance().downloadMTFile();
						}
					} else {
					}
					// 更新衣柜文件
					if (!wardrobe_time.equals(lastwardrobe_time)) {
						File file = new File(PathCommonDefines.JSON_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext)
								+ "/"
								+ "w_"
								+ ManagerUtils.getInstance().getWardrobeId(
										mContext) + ".txt");
						if (FileSDCacher.deleteDirectory2(file)) {
						}
						SchemeExec
								.getInstance()
								.getLSTJFromServer(
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										Integer.parseInt(ManagerUtils
												.getInstance().getWardrobeId(
														mContext)),
										NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_S,
										NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_F);
					} else {
					}
				}
				break;
			// 检查更新文件失败
			case NetworkAsyncCommonDefines.CHECK_FILE_UPDATE_F:
				break;
			// 下载neckmask成功
			case NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_S:
				neckMaskFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag && !isDealHead) {
					dealHead();
				}
				break;
			// 下载neckmask失败
			case NetworkAsyncCommonDefines.DOWNLOAD_NECKMASK_F:
				isDealHead = false;
				break;
			// 下载headmask成功
			case NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_S:
				headMaskFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag && !isDealHead) {
					dealHead();
				}
				break;
			// 下载headmask失败
			case NetworkAsyncCommonDefines.DOWNLOAD_HEADMASK_F:
				isDealHead = false;
				break;
			// 下载头像成功
			case NetworkAsyncCommonDefines.DOWNLOAD_HEAD_S:
				headFlag = true;
				if (neckMaskFlag && headMaskFlag && headFlag && !isDealHead) {
					dealHead();
				}
				break;
			// 下载头像失败
			case NetworkAsyncCommonDefines.DOWNLOAD_HEAD_F:
				isDealHead = false;
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
						String photo = mSharedPreferenceUtils
								.getWardrobePhoto(mContext);
						isDealHead = true;
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
						// 开始下载形象照
						DownloadExec
								.getInstance()
								.downloadFile(
										mHandler,
										photo + "/xingxiang.0",
										PathCommonDefines.WARDROBE_HEAD
												+ "/xingxiang.0",
										NetworkAsyncCommonDefines.DOWNLOAD_XINGXIANGZ_S,
										NetworkAsyncCommonDefines.DOWNLOAD_XINGXIANGZ_F);
					}
				}
				break;
			// 获取衣柜信息失败
			case NetworkAsyncCommonDefines.GET_WARDROBE_F:

				break;

			// 检查更新文件失败
			case NetworkAsyncCommonDefines.UPDATE_FILE_F:

				break;
			// 检查更新文件成功
			case NetworkAsyncCommonDefines.UPDATE_FILE_S:
				Bundle updateBun = msg.getData();
				if (updateBun != null) {
					// 最新的
					String json1 = updateBun.getString("json1");
					// 原来的
					String json2 = updateBun.getString("json2");
					if (json1 != null && !"".equals(json1) && json2 != null
							&& !"".equals(json2)) {
						JSONObject info1;
						try {
							info1 = new JSONObject(json1).getJSONObject("info");
							JSONObject info2 = new JSONObject(json2)
									.getJSONObject("info");

							Iterator<String> keys1 = info1.keys();
							// //LogUtils.e( "是否还有key：" + keys1.hasNext());
							// 遍历最新的
							while (keys1.hasNext()) {
								// 得到key
								String key = (String) keys1.next();
								// 得到value
								String value = info1.getString(key);
								// //LogUtils.e( "是否包含：" +
								// !info2.isNull(key));
								// 旧数据包含新数据
								if (!info2.isNull(key)) {
									// 数据相等
									if (value.equals(info2.getString(key))) {
										if (key.contains("starhead_info_new")) {
											File file = new File(
													PathCommonDefines.MINGXINGZHAO);
											if (!file.exists()) {
												file.mkdirs();
											}
											String[] mingxing = value
													.split("_");
										} else if (key
												.contains("wardrobe_list")) {
										}
									} else {
										if (key.contains("demo_3")) {
											File file = new File(
													PathCommonDefines.MINGXINGZHAO);
											if (!file.exists()) {
												file.mkdirs();
											} else {
												FileSDCacher
														.deleteDirectory2(file);
											}

											String[] mingxing = value
													.split("_");
										}
										if (key.contains("starhead_info_new")) {
											File file = new File(
													PathCommonDefines.MINGXINGZHAO);
											if (!file.exists()) {
												file.mkdirs();
											} else {
												FileSDCacher
														.deleteDirectory2(file);
											}

											String[] mingxing = value
													.split("_");
										} else if (key
												.contains("wardrobe_list")) {
										} else if (key
												.contains("modelUpdateDate")) {
											// LogUtils.e("需要更新模特文件---------------------");

										} else {
											if (!"demo_3".equals(key)) {

											}
										}

									}
								} else {
									// 旧数据不包含新数据
									if (key.contains("demo_3")) {
									} else if (key.contains("wardrobe_list")) {
										// LogUtils.e("用户的形象列表需要更新-------------------------");
									} else if (key.contains("modelUpdateDate")) {
										// LogUtils.e("需要更新模特文件---------------------");
										File file = new File(
												PathCommonDefines.MOTE);
										if (FileSDCacher.deleteDirectory2(file)) {
										}
										;
									} else {
										// 本地没有此形象需要更新
										// 下载模特数据
									}
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						if (json1 != null && !"".equals(json1)) {
							JSONObject info1;
							try {
								info1 = new JSONObject(json1)
										.getJSONObject("info");
								Iterator<String> keys1 = info1.keys();
								// 遍历最新的
								while (keys1.hasNext()) {
									// 得到key
									String key = (String) keys1.next();
									// 得到value
									String value = info1.getString(key);
									if (key.contains("starhead_info_new")) {
										File file = new File(
												PathCommonDefines.MINGXINGZHAO);
										if (!file.exists()) {
											file.mkdirs();
										} else {
											FileSDCacher.deleteDirectory2(file);
										}

										String[] mingxing = value.split("_");
									} else if (key.contains("wardrobe_list")) {
										// LogUtils.e("用户的形象列表需要更新-------------------------");
										// 获取用户的衣柜列表
										try {
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else if (key.contains("demo_3")) {
										// LogUtils.e("精品馆需要更新-------------------------");
									} else if (key.contains("modelUpdateDate")) {
										// LogUtils.e("需要更新模特文件---------------------");
										File file = new File(
												PathCommonDefines.MOTE);
										if (!file.exists()) {
											file.mkdirs();
										}
										if (FileSDCacher.deleteDirectory2(file)) {
										}
									}
								}
								;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							FileSDCacher
									.deleteDirectory2(new File(
											PathCommonDefines.JSON_FOLDER,
											ManagerUtils.getInstance()
													.getUser_id(mContext)));
						}
					}
				} else {
					FileSDCacher.deleteDirectory2(new File(
							PathCommonDefines.JSON_FOLDER, ManagerUtils
									.getInstance().getUser_id(mContext)));
				}
				break;
			// 对比是否更新方案列表
			case 7:
				Bundle buntime = msg.getData();
				if (buntime != null) {
					int time = buntime.getInt("time");
					int time2 = mWardrobe.getWardrobe_Time();
					// LogUtils.e("形象的更新时间：" + time);
					// LogUtils.e("形象列表中形象的更新时间：" + time2);
					if (time < time2) {
						breakFlag++;
						if (breakFlag <= 2) {

						} else {

							mHandler.sendEmptyMessage(9);
						}
					} else {

						mHandler.sendEmptyMessage(9);

					}
				}
				break;
			// 验证手机号
			case 12:
				yanZhengSheBeiHao();
				break;

			case 13:

				break;
			// 上传头像
			case 14:
				Bundle touxiang = msg.getData();
				if (touxiang != null) {
					int xxz_xxid = touxiang.getInt("xxz_xxid", 0);
					if (xxz_xxid != 0) {

						File tx = new File(PathCommonDefines.JSON_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/sctxsb.txt");
						if (tx.exists()) {
							String sbStr = FileSDCacher.ReadData(tx);
							if (sbStr != null && sbStr.contains(xxz_xxid + "")) {

								String[] strs = sbStr.split(",");
								if (strs != null) {
									for (int i = 0; i < strs.length; i++) {
										if (strs[i].equals(xxz_xxid + "")) {
											File headfile = new File(
													PathCommonDefines.PHOTOCACHE_FOLDER
															+ "/"
															+ ManagerUtils
																	.getInstance()
																	.getUser_id(
																			mContext)
															+ "/"
															+ xxz_xxid
															+ "/head/camerahead.jpg");
											if (headfile.exists()) {

											}
										}
									}
								}
							}

						}

					}
				}
				break;

			// 下载背景图片
			case 102:
				Bundle imageBundle = msg.getData();
				if (imageBundle != null) {
					String imageUrl = imageBundle.getString("image_url");

				}
				break;

			}

		}
	};

	/**
	 * @description 复制头像处理的配置文件到SD卡
	 * @parameters
	 */
	private void setConfigXML() {
		// TODO Auto-generated method stub
		File model = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/model");
		if (model != null && !model.exists()) {
			model.mkdirs();
		}
		File data = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/data");
		if (data != null && !data.exists()) {
			data.mkdirs();
		}
		CopyAssets("data", PathCommonDefines.APP_FOLDER_ON_SD + "/data");
		CopyAssets("model", PathCommonDefines.APP_FOLDER_ON_SD + "/model");
		CopyAssets("config7.txt", PathCommonDefines.APP_FOLDER_ON_SD);
		CopyAssets("33.jpg.tracker.xml", PathCommonDefines.APP_FOLDER_ON_SD);
		CopyAssets("44.jpg.tracker.xml", PathCommonDefines.APP_FOLDER_ON_SD);

	}

	private void dealHead() {
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isDealHead = true;
						try {
							final long time1 = System.currentTimeMillis();
							final int msg = (int) DetectionBasedTracker
									.nativeMattingHead("wardrobe/head/head.0");
							isDealHead = false;
							new Handler(Looper.getMainLooper())
									.post(new Runnable() {
										@Override
										public void run() {
											// TODO Auto-generated method stub
											long time2 = System
													.currentTimeMillis();

											switch (msg) {
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
									});
						} catch (Exception e) {
							// TODO: handle exception
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
						}
					}
				});
	}

	private void CopyAssets(String assetDir, String dir) {
		String[] files;
		try {
			files = this.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		if (!mWorkingPath.exists()) {
			mWorkingPath.mkdirs();
		}
		if (files != null && files.length > 0) {

			for (int i = 0; i < files.length; i++) {
				try {
					String fileName = files[i];
					// LogUtils.e("assets目录下的文件：" + fileName);
					if (!fileName.contains(".")) {
						if (0 == assetDir.length()) {
							CopyAssets(fileName, dir + fileName + "/");
						} else {
							CopyAssets(assetDir + "/" + fileName, dir
									+ fileName + "/");
						}
						continue;
					}
					File outFile = new File(mWorkingPath, fileName);
					if (!outFile.exists()) {

						InputStream in = null;
						if (0 != assetDir.length())
							in = getAssets().open(assetDir + "/" + fileName);
						else
							in = getAssets().open(fileName);
						OutputStream out = new FileOutputStream(outFile);
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					} else {
						// LogUtils.e(fileName + "已存在----------------------");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				File outFile = null;
				if (assetDir.equals("config7.txt")) {
					outFile = new File(mWorkingPath, "config.txt");
				} else {
					outFile = new File(mWorkingPath, assetDir);
				}
				if (!outFile.exists()) {

					InputStream in = null;
					in = getAssets().open(assetDir);
					OutputStream out = new FileOutputStream(outFile);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				} else {
					// LogUtils.e(assetDir + "已存在----------------------");
					if (outFile.delete()) {

						CopyAssets(assetDir, dir);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @description:验证设备号
	 * @exception
	 */
	private void yanZhengSheBeiHao() {
		String sheBeiHao = DressBookApp.getInstance().getSheBeiHao();
		if (ManagerUtils.getInstance().isLogin(mContext)) {
			String model = "手机型号：" + android.os.Build.MODEL + "系统版本："
					+ android.os.Build.VERSION.RELEASE;
			String password = "";
			if ("000000".equals(ManagerUtils.getInstance()
					.getPassword(mContext))) {
				password = "000000";
			} else {
				password = MD5Utils.getMD5String(ManagerUtils.getInstance()
						.getPassword(mContext));
			}
			try {

				String appver = getPackageManager().getPackageInfo(
						mContext.getPackageName(), 0).versionName;
				UserExec.getInstance().initUser(mContext, mHandler,
						ManagerUtils.getInstance().getPhoneNum(mContext),
						password, DressBookApp.getInstance().getSheBeiHao(),
						model.replace(" ", "-"), appver,"yes",
						NetworkAsyncCommonDefines.CHECK_FILE_UPDATE_S,
						NetworkAsyncCommonDefines.CHECK_FILE_UPDATE_F);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			if (sheBeiHao == null || sheBeiHao.length() < 10) {

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						yanZhengSheBeiHao();
					}
				}, 5000);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(File file) {
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 删除目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory2(File file) {
		boolean flag = true;
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!file.exists() || !file.isDirectory()) {
			flag = false;
			return flag;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i]);
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = FileSDCacher.deleteDirectory(files[i]);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (file.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.IntentService#onDestroy()
	 * 
	 * @Description TODO
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// super.onDestroy();
		// LogUtils.e("服务销毁-----------------------");
		// Intent intent = new Intent(mContext, DownLoadingService.class);
		// startService(intent);
	}

}
