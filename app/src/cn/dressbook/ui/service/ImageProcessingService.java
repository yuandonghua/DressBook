package cn.dressbook.ui.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.samples.facedetect.DetectionBasedTracker;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.MainActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.Wardrobe;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.net.UploadExec;
import cn.dressbook.ui.net.WardrobeExec;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.HeadAndImageUtils;
import cn.dressbook.ui.utils.HelperUtils;

public class ImageProcessingService extends IntentService {
	private int xx_id;
	private Context mContext = ImageProcessingService.this;

	public ImageProcessingService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ImageProcessingService() {
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

	@SuppressWarnings("null")
	@Override
	protected void onHandleIntent(final Intent intent) {
		// TODO Auto-generated method stub
		xx_id = 0;
		int result = intent.getIntExtra("id", 1000);

		xx_id = intent.getIntExtra("xx_id", 0);
		File file = new File(PathCommonDefines.MOTE);
		if (!file.exists()) {
			file.mkdirs();
		}
		switch (result) {
		// 检查衣柜head文件夹
		case NetworkAsyncCommonDefines.CHECH_WARDROBE_HEAD:
			ArrayList<Wardrobe> wardrobeList = intent
					.getParcelableArrayListExtra("wardrobeList");
			if (wardrobeList != null && wardrobeList.size() > 1) {
				for (int i = 1; i < wardrobeList.size(); i++) {
					Wardrobe wardrobe = wardrobeList.get(i);
					final int wardrobeId = wardrobe.getWardrobeId();
					String xingxiangUrl = wardrobe.getPhoto();
					String cameraheadUrl = wardrobe.getHeadImage();
					String maskfaceneckUrl = PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ "/"
							+ wardrobeList.get(i).getPath()
							+ "/cfg/camerahead.0maskfaceneck.png";
					String maskheadUrl = PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ "/" + wardrobeList.get(i).getPath()
							+ "/cfg/camerahead.0maskhead.png";
					// 检查形象文件夹是否存在
					File wardrobeXingXiangFolder = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/形象");
					if (!wardrobeXingXiangFolder.exists()) {
						wardrobeXingXiangFolder.mkdirs();
					}
					// 检查head文件夹是否存在
					File wardrobeHeadFolder = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/head");

					if (!wardrobeHeadFolder.exists()) {
						wardrobeHeadFolder.mkdirs();
					}
					// 检查模特文件夹是否存在
					File wardrobeModelFolder = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/模特");

					if (!wardrobeModelFolder.exists()) {
						wardrobeModelFolder.mkdirs();
					}
					// 检查xingxiang.0文件是否存在
					File xingxiang = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/head/" + "xingxiang.0");
					if (!xingxiang.exists()) {
						// 下载xingxiang.0文件
					}
					// 检查camerahead.0文件是否存在
					File camerahead = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/head/" + "camerahead.0");
					if (!camerahead.exists()) {
						// 下载camerahead.0文件
					}
					// 检查camerahead.0maskfaceneck.png文件是否存在
					File maskfaceneck = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/head/" + "camerahead.0maskfaceneck.png");
					if (!maskfaceneck.exists()) {
						// 下载camerahead.0maskfaceneck.png文件
					}
					// 检查camerahead.0maskhead.png文件是否存在
					File maskhead = new File(
							PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + wardrobeId
									+ "/head/" + "camerahead.0maskhead.png");
					if (!maskhead.exists()) {
						// 下载camerahead.0maskhead.png文件
					}
				}
			}
			break;
		// 开始上传
		case NetworkAsyncCommonDefines.UPLOAD:
			String xxz_xxid = intent.getStringExtra("wardrobeId");
			File f1 = new File(PathCommonDefines.WARDROBE_HEAD, "");
			// 上传头像,xml文件,形象照,两个mask文件
			UploadExec.getInstance().uploadWardrobeFiles(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), xxz_xxid,
					NetworkAsyncCommonDefines.UPLOAD_IMAGE_S,
					NetworkAsyncCommonDefines.UPLOAD_IMAGE_F);
			break;
		// 给我穿方案列表中的方案穿衣
		case NetworkAsyncCommonDefines.DRESS_ATTIRESCHEME_LIST_UNDRESSED:
			ArrayList<AttireScheme> unDressedAttireList = intent
					.getParcelableArrayListExtra("fanganlist");
			if (unDressedAttireList != null && unDressedAttireList.size() > 0) {
				final int xingxiangid = unDressedAttireList.get(0)
						.getWardrobeId();

				for (final AttireScheme as : unDressedAttireList) {

					String imageUrl = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + xingxiangid + "/形象/" + as.getAttireId()
							+ "s.0";
					File s_pngFile = new File(imageUrl);
					if (!s_pngFile.exists()) {
						File head = new File(
								PathCommonDefines.PHOTOCACHE_FOLDER
										+ "/"
										+ ManagerUtils.getInstance()
												.getUser_id(mContext) + "/"
										+ xingxiangid + "/head/"
										+ "camerahead.0");

						if (head.exists()) {

							String path = PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/" + xingxiangid
									+ "/模特";
							final String pngSuffix = as.getAttireId() + ".png";
							File pngFile = new File(path, pngSuffix);
							if (!pngFile.exists()) {
								if (as.getModPic() != null
										&& !as.getModPic().equals("")) {
									// LogUtils.e("getModPic不为空-------------");
									// 开始下载png图片
								} else if (as.getModpic_jjh() != null
										&& !as.getModpic_jjh().equals("")) {
									// LogUtils.e("getModpic_jjh不为空-------------");
									// 开始下载png图片
								}
							}
							String xmlSuffix = as.getAttireId() + ".png.xml";
							File xmlFile = new File(path, xmlSuffix);
							if (!xmlFile.exists()) {
								if (as.getModPic() != null
										&& !as.getModPic().equals("")) {

									// 开始下载png图片的xml文件
								} else if (as.getModpic_jjh() != null
										&& !as.getModpic_jjh().equals("")) {
									// 开始下载png图片的xml文件
								}

							} else {
								// 合成图片
								ManagerUtils.getInstance()
										.getExecutorService2()
										.execute(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated
												// method stub
												// 合成的形象不存在
												long result = HeadAndImageUtils
														.getInstance()
														.heChengImage1(
																mContext,
																xingxiangid,
																"photo_cache/"
																		+ ManagerUtils
																				.getInstance()
																				.getUser_id(
																						mContext)
																		+ "/"
																		+ xingxiangid
																		+ "/模特/"
																		+ pngSuffix,
																"photo_cache/"
																		+ ManagerUtils
																				.getInstance()
																				.getUser_id(
																						mContext)
																		+ "/"
																		+ xingxiangid
																		+ "/形象/"
																		+ as.getAttireId()
																		+ ".jpeg");

											}
										});
							}
						} else {
						}
					}
				}
			}
			break;
		// 开启后台下载
		case -1:
			// 获取用户的衣柜列表
			break;

		// 检查本地是否存在头像
		case 1:
			int xingxiangId = intent.getIntExtra("xingxiang_id", 0);
			String xingxiangZhao = intent.getStringExtra("headimage");
			File touxiang = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ xingxiangId + "/head/camerahead.0");
			if (!touxiang.exists()) {
				File headFolder = new File(PathCommonDefines.PHOTOCACHE_FOLDER
						+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
						+ "/" + xingxiangId + "/head");
				if (!headFolder.exists()) {
					if (headFolder.mkdirs()) {
					}
				}
				File headxml = new File(PathCommonDefines.PHOTOCACHE_FOLDER
						+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
						+ "/" + xingxiangId + "/head/camerahead.0.xml");
				if (headxml.exists()) {
					headxml.delete();
				}
				// 下载头像
				if (xingxiangZhao != null && !"".equals(xingxiangZhao)) {
				} else {
					try {
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			File touxiangXML = new File(PathCommonDefines.PHOTOCACHE_FOLDER
					+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
					+ "/" + xingxiangId + "/head/camerahead.0.xml");
			// if (touxiangXML.exists()) {
			// touxiangXML.delete();
			// }
			if (!touxiangXML.exists()) {
			} else {
				File touxiangXMLTXT = new File(
						PathCommonDefines.PHOTOCACHE_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/" + xingxiangId
								+ "/head/camerahead.0.xml.txt");
				if (touxiangXMLTXT.exists()) {

					String headxmlleagth = FileSDCacher
							.ReadData(touxiangXMLTXT);
					if (headxmlleagth != null && !"".equals(headxmlleagth)) {
						if (!headxmlleagth.equals(touxiangXML.length() + "")) {
							if (touxiangXML.delete()) {
							}
						}
					}
				}
			}

			break;
		// 上传形象照
		case 2:
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					String xxz_xxid = intent.getStringExtra("xxz_xxid");
					int shap = intent.getIntExtra("shap", 0);
					boolean flag = intent.getBooleanExtra("flag", false);
					if (flag && xxz_xxid != null && shap != 0) {
					} else if (!flag && xxz_xxid != null && shap != 0) {
					}
				}
			}, 60 * 1000);
			break;
		// 检查衣柜中的方案是否合成
		case 3:
			ArrayList<AttireScheme> attireList1 = intent
					.getParcelableArrayListExtra("fanganlist");
			if (attireList1 != null && attireList1.size() > 0) {
				final int xingxiangid = attireList1.get(0).getWardrobeId();
				File wardrobe_xx_file = new File(
						PathCommonDefines.PHOTOCACHE_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/" + xingxiangid + "/形象");

				if (!wardrobe_xx_file.exists()) {
					wardrobe_xx_file.mkdirs();
				}
				for (final AttireScheme as : attireList1) {
					String imageUrl = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + xingxiangid + "/形象/" + as.getAttireId()
							+ "s.0";
					File s_pngFile = new File(imageUrl);
					if (!s_pngFile.exists()) {
						ManagerUtils.getInstance().getExecutorService()
								.execute(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										// heChengDanGe(as.getModPic(),
										// as.getAttireId(), xingxiangid);
									}
								});
					} else {

						final File xmlFile = new File(imageUrl.replace("形象",
								"模特").replace("s.0", ".png.xml"));
						if (xmlFile != null && xmlFile.exists()) {
							xmlFile.delete();
						}
						final File xmlTxtFile = new File(imageUrl.replace("形象",
								"模特").replace("s.0", ".png.xml.txt"));
						if (xmlTxtFile != null && xmlTxtFile.exists()) {
							xmlTxtFile.delete();
						}
						final File moteFile = new File(imageUrl.replace("形象",
								"模特").replace("s.0", ".png"));
						if (moteFile != null && moteFile.exists()) {
							moteFile.delete();
						}
						final File moteTxtFile = new File(imageUrl.replace(
								"形象", "模特").replace("s.0", ".png.txt"));
						if (moteTxtFile != null && moteTxtFile.exists()) {
							moteTxtFile.delete();
						}
					}
				}
			} else {
			}
			break;
		// 合成形象照
		case NetworkAsyncCommonDefines.COMPOUND_IMAGE:
			int shap4 = intent.getIntExtra("shap", 0);
			if (shap4 != 0) {

				File cxpzhcxx = new File(PathCommonDefines.PAIZHAO,
						"camerahead.0");
				if (cxpzhcxx != null && cxpzhcxx.exists()) {
					File cxpzhcxx_mt = new File(PathCommonDefines.MOTE + "/"
							+ shap4 + ".png");
					File cxpzhcxx_mt_xml = new File(PathCommonDefines.MOTE
							+ "/" + shap4 + ".png.xml");
					if (cxpzhcxx_mt.exists() && cxpzhcxx_mt_xml.exists()) {
						File imageFile = new File(PathCommonDefines.XINGXIANG);
						if (!imageFile.exists()) {
							imageFile.mkdirs();
						}
						long time1 = System.currentTimeMillis();
						long cxpzhcxx_result = DetectionBasedTracker
								.nativeMergeBody("paizhao/camerahead.0",
										"mote/" + shap4 + ".png",
										"xingxiang/xingxiang.jpeg");
						long time2 = System.currentTimeMillis();
						if (cxpzhcxx_result != 0) {
						} else {
							File xingxiang1 = new File(
									PathCommonDefines.XINGXIANG
											+ "/xingxiang.jpeg");
							if (xingxiang1 != null && xingxiang1.exists()) {
								File xingxiang2 = new File(
										PathCommonDefines.XINGXIANG
												+ "/xingxiang.0");
								xingxiang1.renameTo(xingxiang2);
							}

						}
					} else {

						downLoadMoTe(shap4);

					}
				}
			}
			break;
		
		// 创建形象--合成形象
		case 6:
		// 下载标准形象图片
		case 7:
			ArrayList<AttireScheme> attireList7 = intent
					.getParcelableArrayListExtra("fanganlist");
			if (attireList7 != null) {
				int xingxiangid = attireList7.get(0).getWardrobeId();
				for (AttireScheme as : attireList7) {

					String imageUrls = PathCommonDefines.PHOTOCACHE_FOLDER
							+ "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + xingxiangid + "/形象/" + as.getAttireId()
							+ "s.0";
					File s_pngFile = new File(imageUrls);

					String imageUrl = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + xingxiangid + "/形象/" + as.getAttireId()
							+ ".0";
					File jpegFile = new File(imageUrl);

					if (s_pngFile == null || !s_pngFile.exists()) {

					}
					if (jpegFile == null || !jpegFile.exists()) {

					}
				}
			} else {
				// LogUtils.e("方案为空------------------");
			}
			break;
		// 下载图片
		case 8:
			String url_8 = intent.getStringExtra("url");
			String suffix_8 = intent.getStringExtra("suffix");
			String path_8 = intent.getStringExtra("path");
			break;
		// 创建形象——给相似头穿衣
		case 12:
			final ArrayList<AttireScheme> attireSchemeList = intent
					.getParcelableArrayListExtra("list");

			if (attireSchemeList != null && attireSchemeList.size() > 0) {
				File deleteFiles = new File(PathCommonDefines.PHOTOCACHE_FOLDER
						+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
						+ "/" + attireSchemeList.get(0).getWardrobeId() + "/"
						+ "形象");
				if (deleteXingXiang(deleteFiles)) {

					String path = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + attireSchemeList.get(0).getWardrobeId()
							+ "/" + "模特";
					File mote = new File(path);
					if (!mote.exists()) {
						mote.mkdirs();
					}
					String path2 = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + attireSchemeList.get(0).getWardrobeId()
							+ "/" + "形象";
					File xingxiang = new File(path2);
					if (!xingxiang.exists()) {
						xingxiang.mkdirs();
					}
					File f0 = new File(PathCommonDefines.PHOTOCACHE_FOLDER
							+ "/"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "/" + attireSchemeList.get(0).getWardrobeId()
							+ "/head");
					if (!f0.exists()) {
						f0.mkdirs();
					}
					// 创建形象，先合成2张
					for (int i = 0; i < 4; i++) {
						String moteUrl = attireSchemeList.get(i).getModPic();
						String suffix = attireSchemeList.get(i).getAttireId()
								+ ".png";
						// 1=代表相似头2=代表用户头
						// 下载模特图片
					}
				}
			}
			break;
		// 扣头
		case NetworkAsyncCommonDefines.DEAL_HEAD:
			try {
				ManagerUtils.getInstance().setKouTouResult(0);
				final long time1 = System.currentTimeMillis();
				final int msg = (int) DetectionBasedTracker
						.nativeMattingHead("paizhao/camerahead.0");
				ManagerUtils.getInstance().setKouTouResult(msg);
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						long time2 = System.currentTimeMillis();
						switch (msg) {
						// 头像处理成功
						case 0:
							// Toast.makeText(getApplicationContext(),
							// "头像初步处理成功",
							// Toast.LENGTH_LONG).show();
							break;
						// 没有找到面部
						case 1:
							Toast.makeText(getApplicationContext(),
									"面部太暗,没有找到面部,请重新拍照", Toast.LENGTH_LONG)
									.show();
							File file1 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file1);
							break;
						// 没有找到身体
						case 2:
							Toast.makeText(getApplicationContext(),
									"没有找到符合的身体,请重新提交数据", Toast.LENGTH_LONG)
									.show();
							File file2 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file2);
							break;
						// 头部太小
						case 3:
							Toast.makeText(getApplicationContext(),
									"头部太小,请重新拍照", Toast.LENGTH_LONG).show();
							File file3 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file3);
							break;
						// 头部距离上边框太近
						case 4:
							Toast.makeText(getApplicationContext(),
									"头部距离上边框太近,请重新拍照", Toast.LENGTH_LONG)
									.show();
							File file4 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file4);
							break;
						// 头部距离左边框太近
						case 5:
							Toast.makeText(getApplicationContext(),
									"头部距离左边框太近,请重新拍照", Toast.LENGTH_LONG)
									.show();
							File file5 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file5);
							break;
						// 头部距离右边框太近
						case 6:
							Toast.makeText(getApplicationContext(),
									"头部距离右边框太近,请重新拍照", Toast.LENGTH_LONG)
									.show();
							File file6 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file6);
							break;
						// 头部距离下边框太近
						case 7:
							Toast.makeText(getApplicationContext(),
									"头部距离下边框太近,请重新拍照", Toast.LENGTH_LONG)
									.show();
							File file7 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file7);
							break;
						// 头部模糊
						case 8:
							Toast.makeText(getApplicationContext(),
									"头部太模糊,请重新拍照", Toast.LENGTH_LONG).show();
							File file8 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file8);
							break;
						// 其他错误
						case 9:
							Toast.makeText(getApplicationContext(),
									"头部处理错误,请重新拍照", Toast.LENGTH_LONG).show();
							File file9 = new File(
									PathCommonDefines.APP_FOLDER_ON_SD + "/"
											+ "paizhao");
							deleteFiles(file9);
							break;
						}
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "头像处理失败,请重新拍照",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			break;
		// 合成形象
		case 14:

			// LogUtils.e("从适配器过来，合成形象开始----------------------");
			// 接收模特地址
			String modpic = intent.getStringExtra("modpic");
			// LogUtils.e("modpic:" + modpic);
			// 接收方案id
			String attire_id = intent.getStringExtra("attire_id");
			// 接收形象ID
			int xingxiang_id = intent.getIntExtra("xingxiang_id", 0);
			// heChengDanGe(modpic, attire_id, xingxiang_id);

			break;

		// 扣头穿衣，更新缓存文件和模特数据
		case 15:
			// LogUtils.e("扣头穿衣，更新缓存文件和模特数据----------------");
			String xmlpath = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ xx_id + "/head/camerahead.0.xml";
			File xmlfile = new File(xmlpath);
			if (xmlfile.exists()) {
				// LogUtils.e("删除以前的头像的xml文件-----------------------");
				xmlfile.delete();
			}
			String path = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
					+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
					+ xx_id + "/head/camerahead.0";
			File headFile = new File(path);
			// 头像存在
			if (headFile.exists()) {
				// LogUtils.e("头像存在---------------------------------");
				int koutouFlag = kouTou("photo_cache/"
						+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
						+ xx_id + "/head/camerahead.0");
				if (koutouFlag == 0) {
					// SchemeExec
					// .getInstance()
					// .getAttireSchemeListFromSD(
					// mHandler,
					// ManagerUtils.getInstance().getUser_id(mContext),
					// xx_id,
					// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
					// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
				}

			} else {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "头像处理失败请重新修改",
								Toast.LENGTH_LONG).show();
					}
				});
			}
			break;
		// 只扣头穿衣，不更新缓存文件
		case 16:
			// LogUtils.e("只扣头穿衣，不更新缓存文件------------------------------");
			// SchemeExec
			// .getInstance()
			// .getAttireSchemeListFromSD(
			// mHandler,
			// ManagerUtils.getInstance().getUser_id(mContext),
			// ManagerUtils.getInstance().getWardrobe2()
			// .getWardrobeId(),
			// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
			// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
			break;
		// 不更新缓存文件，但扣头穿衣
		case 17:
			final int bianji_xxid = intent.getIntExtra("bianji_xxid", 0);

			xx_id = bianji_xxid;
			// LogUtils.e("不更新缓存文件，但扣头穿衣------------------------------");
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					fuZhi();

				}
			});
			File deleteFiles = new File(PathCommonDefines.PHOTOCACHE_FOLDER
					+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
					+ "/" + bianji_xxid + "/" + "形象");
			if (deleteXingXiang(deleteFiles)) {
				// SchemeExec
				// .getInstance()
				// .getAttireSchemeListFromSD(
				// mHandler,
				// ManagerUtils.getInstance().getUser_id(mContext),
				// bianji_xxid,
				// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
				// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
			}
			break;
		// 缓存文件更新，不扣头但穿衣
		case 18:
			// LogUtils.e("缓存文件更新，不扣头但穿衣------------------------");
			ArrayList<AttireScheme> attireSchemeList2 = intent
					.getParcelableArrayListExtra("list");
			if (attireSchemeList2 != null && attireSchemeList2.size() > 0) {

				String motePath = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
						+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
						+ attireSchemeList2.get(0).getWardrobeId() + "/" + "模特";
				File moteFile = new File(motePath);
				if (!moteFile.exists()) {
					moteFile.mkdirs();
				}
				String xingXiangPath = PathCommonDefines.PHOTOCACHE_FOLDER
						+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
						+ "/" + attireSchemeList2.get(0).getWardrobeId() + "/"
						+ "形象";
				File xingXiangFile = new File(xingXiangPath);
				if (!xingXiangFile.exists()) {
					xingXiangFile.mkdirs();
				}
				File headfile = new File(PathCommonDefines.PHOTOCACHE_FOLDER
						+ "/" + ManagerUtils.getInstance().getUser_id(mContext)
						+ "/" + attireSchemeList2.get(0).getWardrobeId()
						+ "/head");
				if (!headfile.exists()) {
					headfile.mkdirs();
				}
				for (int i = 0; i < attireSchemeList2.size(); i++) {
					String moteUrl = attireSchemeList2.get(i).getModPic();
					String suffix = attireSchemeList2.get(i).getAttireId()
							+ ".png";

					// 下载模特图片

				}

			}
			break;
		// 更新缓存文件扣头穿衣（创建形象）
		case 19:
			// LogUtils.e("更新缓存文件扣头穿衣（创建形象）-------------------------------");
			ArrayList<AttireScheme> attireList = intent
					.getParcelableArrayListExtra("list");
			if (attireList != null && attireList.size() > 0) {

				String tiaoGuoMT = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
						+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
						+ ManagerUtils.getInstance().getCreateXXID() + "/"
						+ "模特";
				File tiaoGuoMTFile = new File(tiaoGuoMT);
				if (!tiaoGuoMTFile.exists()) {
					tiaoGuoMTFile.mkdirs();
				}
				String tiaoGuoXX = PathCommonDefines.PHOTOCACHE_FOLDER + "/"
						+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
						+ ManagerUtils.getInstance().getCreateXXID() + "/"
						+ "形象";
				File tiaoGuoXXFile = new File(tiaoGuoXX);
				if (!tiaoGuoXXFile.exists()) {
					tiaoGuoXXFile.mkdirs();
				}
				File tiaoGuoHeadFile = new File(
						PathCommonDefines.PHOTOCACHE_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/"
								+ ManagerUtils.getInstance().getCreateXXID()
								+ "/head");
				if (!tiaoGuoHeadFile.exists()) {
					tiaoGuoHeadFile.mkdirs();
				}
				File fuZhiXingXiang1 = new File(
						PathCommonDefines.APP_FOLDER_ON_SD + "/" + "xingxiang"
								+ "/" + "xingxiang.jpeg");
				File fuZhiXingXiang2 = new File(
						PathCommonDefines.PHOTOCACHE_FOLDER
								+ "/"
								+ ManagerUtils.getInstance().getUser_id(
										mContext) + "/"
								+ ManagerUtils.getInstance().getCreateXXID()
								+ "/head/xingxiang.jpeg");
				FileSDCacher.fuZhiFile(fuZhiXingXiang1, fuZhiXingXiang2);
				// for (int i = 0; i < attireList.size(); i++) {
				// String moteUrl = attireList.get(i).getModPic();
				// String suffix = attireList.get(i).getAttireId() + ".png";
				//
				// // 下载模特图片
				// ImageExec.getInstance().downloadMoTe(mHandler, moteUrl,
				// tiaoGuoXX, suffix, "png", 13);
				//
				// }

			}

			break;
		}

	}

	/**
	 * @description 下载某个体型的模特
	 * @parameters
	 */
	private void downLoadMoTe(int shap) {
		// TODO Auto-generated method stub
		// 去下载模特
		File pngFile = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/mote/"
				+ shap + ".png");
		if (!pngFile.exists()) {
			DownloadExec.getInstance().downloadFile(null,
					PathCommonDefines.SERVER_MOTE + shap + ".png",
					PathCommonDefines.MOTE + "/" + shap + ".png", 0, 0);
			// 模特的PNG不存在

		}
		File XMLFile = new File(PathCommonDefines.APP_FOLDER_ON_SD + "/mote/"
				+ shap + ".png.xml");
		if (!XMLFile.exists()) {
			// 模特的XML不存在
			DownloadExec.getInstance().downloadFile(null,
					PathCommonDefines.SERVER_MOTE + shap + ".png.xml",
					PathCommonDefines.MOTE + "/" + shap + ".png.xml", 0, 0);
		}
	}

	/**
	 * @description 回到形象管理界面
	 * @parameters
	 */
	private void goToXXGL() {
		// TODO Auto-generated method stub
		try {

			Intent xxgl = new Intent(mContext, MainActivity.class);
			xxgl.putExtra("fragmentPosition", 3);
			startActivity(xxgl);
			// ((Activity)
			// mContext).overridePendingTransition(R.anim.anim_enter,
			// R.anim.back_exit);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void deleteMoTeData(int xingxiang_id, String attire_id) {
		// TODO Auto-generated method stub
		final File xmlgzFile = new File(PathCommonDefines.PHOTOCACHE_FOLDER
				+ "/" + ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png.xml.gz");
		if (xmlgzFile != null && xmlgzFile.exists()) {
			xmlgzFile.delete();
			// LogUtils.e("模特的gz文件删除成功--------------------");
		}
		final File xmlFile = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png.xml");
		if (xmlFile != null && xmlFile.exists()) {
			xmlFile.delete();
			// LogUtils.e("模特的XML文件删除成功--------------------");
		}
		final File xmlTxtFile = new File(PathCommonDefines.PHOTOCACHE_FOLDER
				+ "/" + ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png.xml.txt");
		if (xmlTxtFile != null && xmlTxtFile.exists()) {
			xmlTxtFile.delete();
			// LogUtils.e("模特的XML的大小文件删除成功--------------------");
		}
		final File moteFile = new File(PathCommonDefines.PHOTOCACHE_FOLDER
				+ "/" + ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png");
		if (moteFile != null && moteFile.exists()) {
			moteFile.delete();
			// LogUtils.e("模特的图片文件删除成功--------------------");
		}
		final File moteTxtFile = new File(PathCommonDefines.PHOTOCACHE_FOLDER
				+ "/" + ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ xingxiang_id + "/模特/" + attire_id + ".png.txt");
		if (moteTxtFile != null && moteTxtFile.exists()) {
			moteTxtFile.delete();
			// LogUtils.e("模特的图片大小文件删除成功--------------------");
		}
	}

	/**
	 * @description 删除合成的形象
	 * @parameters
	 */
	private boolean deleteXingXiang(File file) {
		// TODO Auto-generated method stub
		return deleteDirectory(file);
	}

	/**
	 * @description 下载模特的集合
	 * @parameters
	 */
	private void dowloadMoTeList() {
		// TODO Auto-generated method stub
		if (HelperUtils.isConnectWIFI(mContext)) {
			ArrayList<Integer> motelist = ManagerUtils.getInstance()
					.getMoteList();
			if (motelist != null && motelist.size() > 0) {
				for (int shap : motelist) {
					File pngFile = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/mote/" + shap + ".png");
					if (!pngFile.exists()) {
						// 模特的PNG不存在
						// OldWardrobeExec.getInstance().getMoTeData(mHandler,
						// shap,
						// "png", 100);

					}
					File XMLFile = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/mote/" + shap + ".png.xml");
					if (!XMLFile.exists()) {
						// 模特的XML不存在
						// OldWardrobeExec.getInstance().getMoTeData(mHandler,
						// shap,
						// "xml", 100);
					}
					File biaoZhunFile = new File(
							PathCommonDefines.APP_FOLDER_ON_SD + "/mote/"
									+ shap + "_ubody.jpg");
					if (!biaoZhunFile.exists()) {
						// OldWardrobeExec.getInstance().getMoRenMoTeData(mHandler,
						// shap, 100);

					}
				}
			}

		} else {

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
		File f1 = new File(PathCommonDefines.PAIZHAO + "/camerahead.0");
		File f11 = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ ManagerUtils.getInstance().getCreateXXID()
				+ "/head/camerahead.0");
		FileSDCacher.fuZhiFile(f1, f11);
		File f2 = new File(PathCommonDefines.PAIZHAO + "/camerahead.0.xml");
		File f22 = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ ManagerUtils.getInstance().getCreateXXID()
				+ "/head/camerahead.0.xml");
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
					// Toast.makeText(getApplicationContext(), "头像处理成功",
					// Toast.LENGTH_LONG).show();
					break;
				// 没有找到面部
				case 1:
					Toast.makeText(getApplicationContext(),
							"面部太暗,没有找到面部,请重新拍照", Toast.LENGTH_LONG).show();
					File file1 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file1);
					break;
				// 没有找到身体
				case 2:
					Toast.makeText(getApplicationContext(),
							"没有找到符合的身体,请重新提交数据", Toast.LENGTH_LONG).show();
					File file2 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file2);
					break;
				// 头部太小
				case 3:
					Toast.makeText(getApplicationContext(), "头部太小,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file3 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file3);
					break;
				// 头部距离上边框太近
				case 4:
					Toast.makeText(getApplicationContext(), "头部距离上边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file4 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file4);
					break;
				// 头部距离左边框太近
				case 5:
					Toast.makeText(getApplicationContext(), "头部距离左边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file5 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file5);
					break;
				// 头部距离右边框太近
				case 6:
					Toast.makeText(getApplicationContext(), "头部距离右边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file6 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file6);
					break;
				// 头部距离下边框太近
				case 7:
					Toast.makeText(getApplicationContext(), "头部距离下边框太近,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file7 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file7);
					break;
				// 头部模糊
				case 8:
					Toast.makeText(getApplicationContext(), "头部太模糊,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file8 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file8);
					break;
				// 其他错误
				case 9:
					Toast.makeText(getApplicationContext(), "头部处理错误,请重新拍照",
							Toast.LENGTH_LONG).show();
					File file9 = new File(PathCommonDefines.APP_FOLDER_ON_SD
							+ "/" + "paizhao");
					deleteFiles(file9);
					break;
				}
			}
		});
		return msg;
	}

	private ArrayList<Wardrobe> mWardrobeList;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 形象照下载成功
			case NetworkAsyncCommonDefines.DOWNLOAD_XINGXIANG_S:
				Bundle xingXiangBun = msg.getData();
				if (xingXiangBun != null) {
					Wardrobe wardrobe = xingXiangBun.getParcelable("wardrobe");
					if (wardrobe != null) {

						checkWardrobeHead(wardrobe);
					}

				}

				break;
			// 形象照下载失败
			case NetworkAsyncCommonDefines.DOWNLOAD_XINGXIANG_F:

				break;
			// camerahead.0下载成功
			case NetworkAsyncCommonDefines.DOWNLOAD_CAMERAHEAD_S:
				Bundle cameraheadBun = msg.getData();
				if (cameraheadBun != null) {
					Wardrobe wardrobe = cameraheadBun.getParcelable("wardrobe");
					if (wardrobe != null) {

						checkWardrobeHead(wardrobe);
					}

				}

				break;
			// camerahead.0下载失败
			case NetworkAsyncCommonDefines.DOWNLOAD_CAMERAHEAD_F:

				break;
			// camerahead.0maskfaceneck.png下载成功
			case NetworkAsyncCommonDefines.DOWNLOAD_MASKFACENECK_S:
				Bundle faceneckBun = msg.getData();
				if (faceneckBun != null) {
					Wardrobe wardrobe = faceneckBun.getParcelable("wardrobe");
					if (wardrobe != null) {

						checkWardrobeHead(wardrobe);
					}

				}

				break;
			// camerahead.0maskfaceneck.png下载失败
			case NetworkAsyncCommonDefines.DOWNLOAD_MASKFACENECK_F:

				break;
			// camerahead.0maskhead.png下载成功
			case NetworkAsyncCommonDefines.DOWNLOAD_MASKHEAD_S:
				Bundle headBun = msg.getData();
				if (headBun != null) {
					Wardrobe wardrobe = headBun.getParcelable("wardrobe");
					if (wardrobe != null) {

						checkWardrobeHead(wardrobe);
					}

				}

				break;
			// camerahead.0maskhead.png下载失败
			case NetworkAsyncCommonDefines.DOWNLOAD_MASKHEAD_F:

				break;

			// 下载完衣柜列表
			case NetworkAsyncCommonDefines.DOWNLOAD_WARDROBE_LIST_S:
				// 获取用户的衣柜列表
				break;
			// 删除衣柜失败
			case NetworkAsyncCommonDefines.DELETE_WARDROBE_F:
				Bundle deleteWardrobeBundle1 = msg.getData();
				int xingxiangid2 = 0;
				xingxiangid2 = deleteWardrobeBundle1.getInt("xingxiangid");
				// 删除该衣柜缓存文件
				Intent serviceIntent2 = new Intent(mContext,
						DownLoadingService.class);
				serviceIntent2.putExtra("id", 9);
				serviceIntent2.putExtra("xx_id", xingxiangid2);
				mContext.startService(serviceIntent2);
				break;
			// 删除衣柜成功
			case NetworkAsyncCommonDefines.DELETE_WARDROBE_S:
				Bundle deleteWardrobeBundle = msg.getData();
				if (deleteWardrobeBundle != null) {
					File file = new File(PathCommonDefines.JSON_FOLDER, "u_"
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "_wardrobe_list.txt");
					if (file.exists()) {
						file.delete();
					}
					File wardrobelist = new File(PathCommonDefines.JSON_FOLDER
							+ "/"
							+ ManagerUtils.getInstance().getUser_id(mContext),
							"wardrobelist.txt");
					if (wardrobelist.exists()) {
						wardrobelist.delete();
					}
					int xingxiangid1 = 0;
					xingxiangid1 = deleteWardrobeBundle.getInt("xingxiangid");
					// 删除该衣柜缓存文件
					Intent serviceIntent = new Intent(mContext,
							DownLoadingService.class);
					serviceIntent.putExtra("id", 9);
					serviceIntent.putExtra("xx_id", xingxiangid1);
					mContext.startService(serviceIntent);
				}
				break;
			// 检查模特数据，合成图片
			case 600:
				Bundle bundle600 = msg.getData();
				if (bundle600 != null) {
					int shap = bundle600.getInt("shap");
					if (shap != 0) {

						File cxpzhcxx = new File(
								PathCommonDefines.APP_FOLDER_ON_SD
										+ "/paizhao/camerahead.0");
						if (cxpzhcxx != null && cxpzhcxx.exists()) {
							File cxpzhcxx_mt = new File(PathCommonDefines.MOTE
									+ "/" + shap + ".png");
							File cxpzhcxx_mt_txt = new File(
									PathCommonDefines.MOTE + "/" + shap
											+ ".png.txt");
							String mt_length1 = FileSDCacher
									.ReadData(cxpzhcxx_mt_txt);
							File cxpzhcxx_mt_xml = new File(
									PathCommonDefines.MOTE + "/" + shap
											+ ".png.xml");
							File cxpzhcxx_mt_xml_txt = new File(
									PathCommonDefines.MOTE + "/" + shap
											+ ".png.xml.txt");
							String mt_length2 = FileSDCacher
									.ReadData(cxpzhcxx_mt_xml_txt);

							if (cxpzhcxx_mt.exists()
									&& cxpzhcxx_mt_xml.exists()
									&& mt_length2 != null
									&& mt_length2.equals(cxpzhcxx_mt_xml
											.length() + "")
									&& mt_length1 != null
									&& mt_length1.equals(cxpzhcxx_mt.length()
											+ "")) {
								long time1 = System.currentTimeMillis();
								long cxpzhcxx_result = DetectionBasedTracker
										.nativeMergeBody(
												"paizhao/camerahead.0", "mote/"
														+ shap + ".png",
												"xingxiang/xingxiang.jpeg");
								long time2 = System.currentTimeMillis();

								if (cxpzhcxx_result != 0) {
								} else {
									File xingxiang1 = new File(
											PathCommonDefines.XINGXIANG
													+ "/xingxiang.jpeg");
									if (xingxiang1 != null
											&& xingxiang1.exists()) {
										File xingxiang2 = new File(
												PathCommonDefines.XINGXIANG
														+ "/xingxiang.jpeg");
										xingxiang1.renameTo(xingxiang2);
									}
								}
							} else {
								downLoadMoTe(shap);

							}
						} else {
						}
					}

				}

				break;
			// 开始检查要穿衣的衣柜列表
			case NetworkAsyncCommonDefines.GET_WARDROBE_LIST_FROM_SD_S:
				Bundle wardrobeListBundle = msg.getData();
				if (wardrobeListBundle != null) {
					mWardrobeList = wardrobeListBundle
							.getParcelableArrayList(Wardrobe.WARDROBE_LIST);
					if (mWardrobeList != null && mWardrobeList.size() > 0) {
						// 检查是否上传头像
						Intent service = new Intent(mContext,
								DownLoadingService.class);
						service.putExtra("id", 14);
						service.putExtra(Wardrobe.WARDROBE_LIST, mWardrobeList);
						mContext.startService(service);
						for (int i = 0; i < mWardrobeList.size(); i++) {
							int wardrobeId = mWardrobeList.get(i)
									.getWardrobeId();

							SchemeExec.getInstance().getAttireSchemeList(
									mHandler,
									ManagerUtils.getInstance().getUser_id(
											mContext), wardrobeId, 220, 223);

						}

					} else {
					}
				}
				break;
			// 获取到某个衣柜的方案列表
			case 220:
				Bundle bundle220 = msg.getData();
				if (bundle220 != null) {
					ArrayList<AttireScheme> mAttireSchemeList220;
					mAttireSchemeList220 = bundle220
							.getParcelableArrayList("list");
					// 方案列表不为空
					if (mAttireSchemeList220 != null
							&& mAttireSchemeList220.size() > 0) {
						final int xingxiangid = mAttireSchemeList220.get(0)
								.getWardrobeId();
						for (int i = 0; i < mAttireSchemeList220.size(); i++) {
							final AttireScheme as = mAttireSchemeList220.get(i);
							// 检查形象是否存在
							String imageUrl = PathCommonDefines.PHOTOCACHE_FOLDER
									+ "/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext)
									+ "/"
									+ xingxiangid
									+ "/形象/" + as.getAttireId() + "s.0";
							File s_pngFile = new File(imageUrl);
							// 形象不存在，去合成
							if (!s_pngFile.exists()) {
								ManagerUtils.getInstance().getExecutorService()
										.execute(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												// heChengDanGe(as.getModPic(),
												// as.getAttireId(),
												// xingxiangid);
											}
										});
							} else {
								// 形象存在，删除无效数据
								final File xmlFile = new File(imageUrl.replace(
										"形象", "模特").replace("s.0", ".png.xml"));
								if (xmlFile != null && xmlFile.exists()) {
									xmlFile.delete();
								}
								final File xmlTxtFile = new File(imageUrl
										.replace("形象", "模特").replace("s.0",
												".png.xml.txt"));
								if (xmlTxtFile != null && xmlTxtFile.exists()) {
									xmlTxtFile.delete();
								}
								final File moteFile = new File(imageUrl
										.replace("形象", "模特").replace("s.0",
												".png"));
								if (moteFile != null && moteFile.exists()) {
									moteFile.delete();
								}
								final File moteTxtFile = new File(imageUrl
										.replace("形象", "模特").replace("s.0",
												".png.txt"));
								if (moteTxtFile != null && moteTxtFile.exists()) {
									moteTxtFile.delete();
								}
							}
						}

					}
				}
				break;
			// 扣头
			case 1:
				Bundle bun = msg.getData();
				if (bun != null) {

					String xingxiangZhao = bun.getString("camerahead");
					if (xingxiangZhao == null) {
						break;
					}

					int xx_id = bun.getInt("xx_id");
					File xingxiangZhaoFile = new File(xingxiangZhao);
					if (xingxiangZhaoFile != null
							&& !xingxiangZhaoFile.exists()) {
						try {

							ManagerUtils.getInstance().setKouTouResult(0);
							final long time1 = System.currentTimeMillis();
							final int koutou = (int) DetectionBasedTracker
									.nativeMattingHead("photo_cache/"
											+ ManagerUtils.getInstance()
													.getUser_id(mContext) + "/"
											+ xx_id + "/head/" + "camerahead.0");
							// LogUtils.e("扣头结果：" + koutou);
							ManagerUtils.getInstance().setKouTouResult(koutou);
							long time2 = System.currentTimeMillis();
							// LogUtils.e("用时：" + (time2 - time1) / 1000 + "秒");
							if (koutou == 0) {
								// LogUtils.e("头像处理成功------------------------");
							} else {
								// LogUtils.e("头像处理失败------------------------");
								File file = new File(xingxiangZhao);
								if (file.exists()) {
									file.delete();
									// LogUtils.e("头像已删除------------------------");
								}
							}

						} catch (Exception e) {
							// TODO: handle exception
							new Handler(Looper.getMainLooper())
									.post(new Runnable() {
										@Override
										public void run() {
											// TODO Auto-generated method stub
											Toast.makeText(
													getApplicationContext(),
													"头像处理失败,请重新拍照",
													Toast.LENGTH_LONG).show();
										}
									});
						}
					} else {
						// LogUtils.e("压缩文件存在-------------");
					}
				}
				break;
			// 上传形象照成功
			case NetworkAsyncCommonDefines.UPLOAD_IMAGE_S:

				break;
			// 上传形象照失败
			case NetworkAsyncCommonDefines.UPLOAD_IMAGE_F:
				break;
			// 上传头像成功
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_S:
				Bundle maskHead = msg.getData();
				if (maskHead != null) {
					int xxz_xxid = maskHead.getInt("xxz_xxid", 0);
					// 上传头像mask
				}
				break;
			// 上传头像失败
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_F:

				break;
			// 上传头像mask成功
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_MASK_S:
				Bundle maskFace = msg.getData();
				if (maskFace != null) {
					int xxz_xxid = maskFace.getInt("xxz_xxid", 0);
					// 上传脸脖mask
				}
				break;
			// 上传头像mask失败
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_MASK_F:

				break;
			// 上传脸脖mask成功
			case NetworkAsyncCommonDefines.UPLOAD_FACENECK_MASK_S:
				// Bundle xmlZip = msg.getData();
				// if (xmlZip != null) {
				// int xxz_xxid = xmlZip.getInt("xxz_xxid", 0);
				// // 上传头像xml压缩文件
				// OldWardrobeExec.getInstance().uploadWardrobeFile(
				// mHandler,
				// ManagerUtils.getInstance().getUser_id(mContext),
				// xxz_xxid,
				// PathCommonDefines.PHOTOCACHE_FOLDER
				// + "/"
				// + ManagerUtils.getInstance()
				// .getUser_id(mContext) + "/" + xxz_xxid
				// + "/head/camerahead.0.xml.zip",
				// NetworkAsyncCommonDefines.UPLOAD_HEAD_XML_ZIP_S,
				// NetworkAsyncCommonDefines.UPLOAD_HEAD_XML_ZIP_F);
				// }
				break;
			// 上传脸脖mask失败
			case NetworkAsyncCommonDefines.UPLOAD_FACENECK_MASK_F:

				break;
			// 上传头像xml的压缩文件成功
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_XML_ZIP_S:
				break;
			// 上传头像xml的压缩文件失败
			case NetworkAsyncCommonDefines.UPLOAD_HEAD_XML_ZIP_F:

				break;
			// 模特的png图片下载成功，开始下载模特的xml文件
			case 13:
				Bundle motePNGBun = msg.getData();
				if (motePNGBun != null) {
					String xmlUrl = motePNGBun.getString("url");
					String xmlPath = motePNGBun.getString("path");
					String xmlSuffix = motePNGBun.getString("suffix");
					int xmlXingXiangId = motePNGBun.getInt("xingxiangid");
				}
				break;
			// 创建形象时，模特的xml文件下载成功，开始合成图片
			case 14:
				Bundle xingxiangBun = msg.getData();
				if (xingxiangBun != null) {
					final int xingxiangId = xingxiangBun.getInt("xingxiangid");
					// LogUtils.e("合成形象--形象ID：" + xingxiangId);
					final String motePath = xingxiangBun.getString("motepath");
					// LogUtils.e("合成形象--模特路径：" + motePath);
					final String xingxiangPath = xingxiangBun
							.getString("xingxiangpath");
					// LogUtils.e("合成形象--生成形象路径：" + xingxiangPath);
					final File xmlFile = new File(
							PathCommonDefines.APP_FOLDER_ON_SD + "/" + motePath
									+ ".xml");
					final File xmlTxtFile = new File(
							PathCommonDefines.APP_FOLDER_ON_SD + "/" + motePath
									+ ".xml.txt");
					String xmlLength = FileSDCacher.ReadData(xmlTxtFile);
					final File moteFile = new File(
							PathCommonDefines.APP_FOLDER_ON_SD + "/" + motePath);
					final File moteTxtFile = new File(
							PathCommonDefines.APP_FOLDER_ON_SD + "/" + motePath
									+ ".txt");
					String moteLength = FileSDCacher.ReadData(moteTxtFile);
					if (moteFile.exists() && xmlFile.exists()
							&& moteLength != null && !"".equals(moteLength)
							&& moteLength.equals(moteFile.length() + "")
							&& xmlLength != null && !"".equals(xmlLength)
							&& xmlLength.equals(xmlFile.length() + "")) {

						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									// LogUtils.e("开始合成------------------");
									long time1 = System.currentTimeMillis();
									long xingxiangResult = DetectionBasedTracker
											.nativeMergeBody(
													"photo_cache/"
															+ ManagerUtils
																	.getInstance()
																	.getUser_id(
																			mContext)
															+ "/" + xingxiangId
															+ "/head/"
															+ "camerahead.0",
													motePath, xingxiangPath);
									if (xingxiangResult == 0) {
										long time2 = System.currentTimeMillis();
										// LogUtils.e("合成形象--成功--耗时："
										// + (time2 - time1) / 1000);
									} else {
										long time2 = System.currentTimeMillis();
										// LogUtils.e("合成形象--失败--耗时："
										// + (time2 - time1) / 1000);
										if (moteFile.delete()) {
											if (xmlFile.delete()) {
												// LogUtils.e("文件已删除-------------");
											}
										}
									}
								} catch (Exception e) {
									// LogUtils.e("合成形象异常-------------------");
									String motePNGFilePath = PathCommonDefines.APP_FOLDER_ON_SD
											+ "/" + motePath;
									File moteFile = new File(motePNGFilePath);
									if (moteFile.exists()) {
										if (moteFile.delete()) {
											// LogUtils.e("合成形象异常，模特png图片，删除-----------------------");

										}
									}
									String moteXMLPath = PathCommonDefines.APP_FOLDER_ON_SD
											+ "/" + motePath + ".xml";
									File moteXmlFile = new File(moteXMLPath);
									if (moteXmlFile.exists()) {
										if (moteXmlFile.delete()) {
											// LogUtils.e("合成形象异常，模特XML文件，删除-----------------------");
										}
									}
									String xingxiangFilePath = PathCommonDefines.APP_FOLDER_ON_SD
											+ "/" + xingxiangPath;
									File xingxiangFile = new File(
											xingxiangFilePath);
									if (xingxiangFile.exists()) {
										if (xingxiangFile.delete()) {
											// LogUtils.e("合成形象异常，合成的形象图片，删除-----------------------");
										}
									}

								}
							}
						});
						thread.setPriority(10);
						// thread.start();

						ManagerUtils.getInstance().getExecutorService()
								.execute(thread);
					} else {
						Timer timer = new Timer();

						TimerTask tt = new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (moteFile.exists() && xmlFile.exists()) {
									Thread thread = new Thread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											try {
												// LogUtils.e("开始合成------------------");
												long time1 = System
														.currentTimeMillis();
												long xingxiangResult = DetectionBasedTracker
														.nativeMergeBody(
																"photo_cache/"
																		+ ManagerUtils
																				.getInstance()
																				.getUser_id(
																						mContext)
																		+ "/"
																		+ xingxiangId
																		+ "/head/"
																		+ "camerahead.0",
																motePath,
																xingxiangPath);
												if (xingxiangResult == 0) {
													long time2 = System
															.currentTimeMillis();
													// LogUtils.e("合成形象--成功--耗时："
													// + (time2 - time1)
													// / 1000);
												} else {
													long time2 = System
															.currentTimeMillis();
													// LogUtils.e("合成形象--失败--耗时："
													// + (time2 - time1)
													// / 1000);
													if (moteFile.delete()) {
														if (xmlFile.delete()) {
															// LogUtils.e("文件已删除-------------");
														}
													}
												}
											} catch (Exception e) {
												// LogUtils.e("合成形象异常-------------------");
												String motePNGFilePath = PathCommonDefines.APP_FOLDER_ON_SD
														+ "/" + motePath;
												File moteFile = new File(
														motePNGFilePath);
												if (moteFile.exists()) {
													if (moteFile.delete()) {
														// LogUtils.e("合成形象异常，模特png图片，删除-----------------------");

													}
												}
												String moteXMLPath = PathCommonDefines.APP_FOLDER_ON_SD
														+ "/"
														+ motePath
														+ ".xml";
												File moteXmlFile = new File(
														moteXMLPath);
												if (moteXmlFile.exists()) {
													if (moteXmlFile.delete()) {
														// LogUtils.e("合成形象异常，模特XML文件，删除-----------------------");
													}
												}
												String xingxiangFilePath = PathCommonDefines.APP_FOLDER_ON_SD
														+ "/" + xingxiangPath;
												File xingxiangFile = new File(
														xingxiangFilePath);
												if (xingxiangFile.exists()) {
													if (xingxiangFile.delete()) {
														// LogUtils.e("合成形象异常，合成的形象图片，删除-----------------------");
													}
												}

											}
										}
									});
									// thread.start();
									ManagerUtils.getInstance()
											.getExecutorService2()
											.execute(thread);
								} else {
									motePath.substring(1, motePath.length() - 4);
									String[] moteArr = motePath.split("/");
									String suffix = moteArr[moteArr.length - 1];
									// LogUtils.e("后缀名：" + suffix);
									Intent service = new Intent(mContext,
											DownLoadingService.class);
									service.putExtra("id", 12);
									service.putExtra("attire_id", suffix);
									service.putExtra("xx_id", xingxiangId);
									mContext.startService(service);
								}
							}
						};
						timer.schedule(tt, 5000);
						// LogUtils.e("xml文件不存在：" + xmlFile.getAbsolutePath());
					}
				}

				break;
			// 获取方案列表
			case 15:

				// SchemeExec
				// .getInstance()
				// .getAttireSchemeListFromSD(
				// mHandler,
				// ManagerUtils.getInstance().getUser_id(mContext),
				// xx_id,
				// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
				// NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
				break;
			// 获取方案列表的返回结果
			case NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S:

				final Bundle attireSchemeBundle = msg.getData();
				if (attireSchemeBundle != null) {
					Thread thread1 = new Thread(new Runnable() {

						@Override
						public void run() {
							ArrayList<AttireScheme> mAttireSchemeList;
							mAttireSchemeList = attireSchemeBundle
									.getParcelableArrayList("list");
							if (mAttireSchemeList != null
									&& mAttireSchemeList.size() > 0) {

								String path = PathCommonDefines.PHOTOCACHE_FOLDER
										+ "/"
										+ ManagerUtils.getInstance()
												.getUser_id(mContext)
										+ "/"
										+ mAttireSchemeList.get(0)
												.getWardrobeId() + "/模特";
								File mote = new File(path);
								if (!mote.exists()) {
									mote.mkdirs();
								}
								String path2 = PathCommonDefines.PHOTOCACHE_FOLDER
										+ "/"
										+ ManagerUtils.getInstance()
												.getUser_id(mContext)
										+ "/"
										+ mAttireSchemeList.get(0)
												.getWardrobeId() + "/形象";
								File xingxiang = new File(path2);
								if (!xingxiang.exists()) {
									xingxiang.mkdirs();
								}
								File f0 = new File(
										PathCommonDefines.PHOTOCACHE_FOLDER
												+ "/"
												+ ManagerUtils.getInstance()
														.getUser_id(mContext)
												+ "/"
												+ mAttireSchemeList.get(0)
														.getWardrobeId()
												+ "/head");
								if (!f0.exists()) {
									f0.mkdirs();
								}

								for (int i = 0; i < mAttireSchemeList.size(); i++) {
									String moteUrl = mAttireSchemeList.get(i)
											.getModPic();
									String suffix = mAttireSchemeList.get(i)
											.getAttireId() + ".png";
									File motefile = new File(path + "/"
											+ suffix);
									if (motefile.exists()) {
										motefile.delete();

									}
									File moteXMLfile = new File(path + "/"
											+ suffix + ".xml");
									if (moteXMLfile.exists()) {
										moteXMLfile.delete();

									}
									// 下载模特图片
									// 合成形象结束后，上传衣柜的头像
									// if (i == mAttireSchemeList.size() - 1) {
									// OldWardrobeExec
									// .getInstance()
									// .uploadWardrobeHeadImage(
									// mHandler,
									// ManagerUtils
									// .getInstance()
									// .getUser_id(mContext),
									// xx_id,
									// PathCommonDefines.PHOTOCACHE_FOLDER
									// + "/"
									// + ManagerUtils
									// .getInstance()
									// .getUser_id(mContext)
									// + "/"
									// + ManagerUtils
									// .getInstance()
									// .getCreateXXID()
									// + "/head/camerahead.0");
									// }
								}
							}
						}
					});
					thread1.start();
				}
				break;
			case 404:
				// LogUtils.e("没有找到数据----------------------");
				break;
			case 48:
				// LogUtils.e("没有获取到更新时间----------------------");
				break;
			case 100:
				break;
			// 头像上传失败
			// case 405:
			// Bundle shibaiBun=msg.getData();
			// if(shibaiBun!=null){
			// int xx_id=shibaiBun.getInt("xx_id",0);
			//
			//
			//
			//
			// }
			// break;

			}

		}
	};

	/**
	 * @description
	 * @parameters
	 */
	private void setConfigXML() {
		// TODO Auto-generated method stub

		CopyAssets("model", PathCommonDefines.APP_FOLDER_ON_SD + "/model");
		CopyAssets("config.txt", PathCommonDefines.APP_FOLDER_ON_SD);
		// try {
		// File file = new File(PathCommonDefines.APP_FOLDER_ON_SD);
		// if (!file.exists()) {
		// file.mkdirs();
		// }
		// File config = new File(PathCommonDefines.APP_FOLDER_ON_SD
		// + "/" + "config.txt");
		// if (!config.exists()) {
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * @description:检查衣柜的head文件是否齐全
	 * @exception
	 */
	protected void checkWardrobeHead(final Wardrobe wardrobe) {
		// TODO Auto-generated method stub
		// 检查camerahead.0文件是否存在
		File camerahead = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ wardrobe.getWardrobeId() + "/head/" + "camerahead.0");
		if (!camerahead.exists()) {
			return;
		}
		// 检查camerahead.0maskfaceneck.png文件是否存在
		File maskfaceneck = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ wardrobe.getWardrobeId() + "/head/"
				+ "camerahead.0maskfaceneck.png");
		if (!maskfaceneck.exists()) {
			return;
		}
		// 检查camerahead.0maskhead.png文件是否存在
		File maskhead = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ wardrobe.getWardrobeId() + "/head/"
				+ "camerahead.0maskhead.png");
		if (!maskhead.exists()) {
			return;
		}
		File cameraheadxml = new File(PathCommonDefines.PHOTOCACHE_FOLDER + "/"
				+ ManagerUtils.getInstance().getUser_id(mContext) + "/"
				+ wardrobe.getWardrobeId() + "/head/" + "camerahead.0.xml");
		if (!cameraheadxml.exists()) {
			ManagerUtils.getInstance().getExecutorService2()
					.execute(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							String srcPath = "photo_cache/"
									+ ManagerUtils.getInstance().getUser_id(
											mContext) + "/"
									+ wardrobe.getWardrobeId()
									+ "/head/camerahead.0";
							long positionResult = DetectionBasedTracker
									.nativeChangePosition(srcPath,
											wardrobe.getHeadPosition());
							long scaleResult = DetectionBasedTracker
									.nativeScaleHead(srcPath,
											wardrobe.getHeadScale());
							long bodyResult = DetectionBasedTracker
									.nativeSetWarpParam(srcPath,
											wardrobe.getBodyWeight(),
											wardrobe.getBodyHeight());

						}
					});
		}
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
				File outFile = new File(mWorkingPath, assetDir);
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
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public boolean deleteFiles(File file) {
		if (file == null || !file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(file);
			} else {
				return deleteDirectory(file);

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
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(File file) {
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
				flag = deleteDirectory(files[i]);
				if (!flag)
					break;
			}
		}
		return flag;
		// // 删除当前目录
		// if (file.delete()) {
		// return true;
		// } else {
		// return false;
		// }
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
